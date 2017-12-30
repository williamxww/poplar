package com.bow.service.core;

import com.bow.entity.App;
import com.bow.entity.Item;
import com.bow.entity.Namespace;

import java.util.Set;

/**
 * @author wwxiang
 * @since 2017/12/27.
 */
public interface IPublishService {

    boolean publish(Set<App> apps, Set<Namespace> namespaces, Set<Item> items);
}
