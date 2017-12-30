package com.bow.service.core.impl;

import com.bow.entity.App;
import com.bow.entity.Item;
import com.bow.entity.Namespace;
import com.bow.service.core.IDbService;

import java.util.List;

/**
 * @author vv
 * @since 2017/12/30.
 */
public class DbService implements IDbService {
    @Override
    public List<App> getApps() {
        return null;
    }

    @Override
    public List<Namespace> getNamespaces(String appId) {
        return null;
    }

    @Override
    public List<Item> getItems(String appId, String namespace) {
        return null;
    }
}
