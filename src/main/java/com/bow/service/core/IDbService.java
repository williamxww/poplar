package com.bow.service.core;

import com.bow.entity.App;
import com.bow.entity.Item;
import com.bow.entity.Namespace;

import java.util.List;

/**
 * @author vv
 * @since 2017/12/30.
 */
public interface IDbService {

    List<App> getApps();

    List<Namespace> getNamespaces(String appId);

    List<Item> getItems(String appId, String namespace);
}
