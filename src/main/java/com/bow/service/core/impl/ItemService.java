package com.bow.service.core.impl;

import com.bow.dao.ItemDao;
import com.bow.entity.Item;
import com.bow.service.core.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wwxiang
 * @since 2017/12/27.
 */
@Service
public class ItemService implements IItemService {

    @Autowired
    private ItemDao itemDao;

    @Override
    public List<Item> getItems(int namespaceId) {
        return itemDao.getItems(namespaceId);
    }

    @Override
    public Item updateItem(int itemId, String value) {

        return null;
    }
}
