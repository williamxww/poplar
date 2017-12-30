package com.bow.service.core.impl;

import com.bow.entity.App;
import com.bow.entity.AppVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author wwxiang
 * @since 2017/12/27.
 */
public class MergeUtil {

    public static List<AppVo> merge(List<App> apps, Set<AppVo> modified){
        List<AppVo> result = new ArrayList<>();
        for(App app: apps){
            result.add(new AppVo(app));
        }

        for(AppVo vo: modified){
            if(vo.getStatus()==-1){
                //从apps中移除
            }
        }
        return  result;
    }
}
