package com.bow.service.core;

import java.util.List;

/**
 * @author vv
 * @since 2017/12/30.
 */
public interface IZkService {

    /**
     * 在ZK上创建APP节点，无论节点是否已存在<br/>
     * 服务端和客户端都会调用此方法
     * 
     * @param appId 应用ID
     * @return 是否创建成功
     */
    boolean createApp(String appId);

    /**
     * 创建一个命名空间，并设置其内容
     * @param appId 命名空间所属应用
     * @param namespace 命名空间
     * @param content 内容
     * @return 是否创建成功
     */
    boolean createNamespace(String appId, String namespace, String content);

    /**
     * 配置变化后，通过此函数通知ZK
     *
     * @param appId 配置所属应用
     * @param namespace 配置所属空间
     * @param content 配置内容
     * @return 是否成功通知
     */
    boolean notifyZk(String appId, String namespace, String content);

    /**
     * 删除APP，服务端启动时同步DB和ZK数据会调用
     * @param appId
     * @return
     */
    boolean deleteApp(String appId);

    /**
     * 删除命名空间，服务端启动时同步DB和ZK数据会调用
     * @param appId
     * @param namespace
     * @return
     */
    boolean deleteNamespace(String appId, String namespace);

    List<String> getApps();

    List<String> getNamespace(String appId);

    String getItems(String appId, String namespace);
}
