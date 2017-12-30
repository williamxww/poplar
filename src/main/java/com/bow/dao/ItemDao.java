package com.bow.dao;

import com.bow.entity.Item;

import java.util.List;

/**
 * @author wwxiang
 * @since 2017/12/27.
 */
public interface ItemDao {

	Item getItem(int id);

	List<Item> getItems(int namespaceId);

	boolean addItem(Item item);

	boolean deleteItem(int id);

	boolean updateItem(Item item);
}
