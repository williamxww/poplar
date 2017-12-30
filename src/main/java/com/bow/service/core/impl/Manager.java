package com.bow.service.core.impl;

import com.bow.entity.App;
import com.bow.entity.Item;
import com.bow.entity.Namespace;
import com.bow.service.core.IDbService;
import com.bow.service.core.IZkService;
import com.bow.utils.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vv
 * @since 2017/12/30.
 */
@Component
public class Manager {

    @Autowired
    private IZkService zkService;

    @Autowired
    private IDbService dbService;

    /**
     * 配置管理器初始化
     */
    public void init(){
        initApp();
    }

    /**
     * 同步DB和ZK的APP节点数据
     */
    private void initApp(){

        // 找出数据库相对于ZK的新增项和删除项
        List<App> apps = dbService.getApps();
        List<String> appsDb = new ArrayList<>();
        for(App a: apps){
            appsDb.add(a.getAppId());
        }
        List<String> appsZk = zkService.getApps();
        List<String> adds = ListUtil.subtract(appsDb, appsZk);
        List<String> deletes = ListUtil.subtract(appsZk, appsDb);

        // 向ZK上添加新增项
        for(String add: adds){
            zkService.createApp(add);
        }

        // 删除未在数据库中存在的
        for(String delete: deletes){
            zkService.deleteApp(delete);
        }

        // 对于每个应用，初始化其下namespace节点
        for(String a: appsDb){
            initNamespace(a);
        }

    }

    private void initNamespace(String appId){
        List<Namespace> nsObj = dbService.getNamespaces(appId);
        List<String> nsDb = new ArrayList<>();
        for(Namespace obj: nsObj){
            nsDb.add(obj.getNamespace());
        }

        List<String> nsZk = zkService.getNamespace(appId);
        List<String> adds = ListUtil.subtract(nsDb, nsZk);
        List<String> deletes = ListUtil.subtract(nsZk, nsDb);
        List<String> sames = ListUtil.intersection(nsDb, nsZk);

        // 向ZK上添加新增项
        for(String add: adds){
            List<Item> items = dbService.getItems(appId,add);
            String content = itemsToString(items);
            zkService.createNamespace(appId, add,content);
        }

        // 删除未在数据库中存在的
        for(String delete: deletes){
            zkService.deleteNamespace(appId, delete);
        }

        for(String same: sames){
            List<Item> items = dbService.getItems(appId,same);
            String contentDb = itemsToString(items);
            String contentZk = zkService.getItems(appId, same);
            //内容不同，则通知ZK更新
            if(!contentDb.equals(contentZk)){
                zkService.notifyZk(appId, same, contentDb);
            }
        }
    }


    private String itemsToString(List<Item> items){
        if(items == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(Item item: items){
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }
}
