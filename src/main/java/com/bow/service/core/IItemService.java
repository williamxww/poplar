package com.bow.service.core;

import com.bow.entity.Item;

import java.util.List;

/**
 * @author wwxiang
 * @since 2017/12/27.
 */
public interface IItemService {

    List<Item> getItems(int namespaceId);

    Item updateItem(int itemId, String value);
}
