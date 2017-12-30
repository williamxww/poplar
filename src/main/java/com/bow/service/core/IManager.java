package com.bow.service.core;

import com.bow.entity.App;
import com.bow.entity.Item;
import com.bow.entity.Namespace;

import java.util.List;

/**
 * @author vv
 * @since 2017/12/30.
 */
public interface IManager {

    void init();

    void createApp(App app);

    void deleteApp(String appId);

    void createNamespace(Namespace namespace);

    void deleteNamespace(String appId, String namespaceId);

    void updateItems(String appId, String namespaceId, List<Item> items);
}
