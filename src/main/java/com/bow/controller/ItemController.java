package com.bow.controller;

import com.bow.entity.Item;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wwxiang
 * @since 2017/12/27.
 */
public class ItemController {

	@RequestMapping(value = "/listItems/{namespaceId}", method = RequestMethod.POST)
	public void hello(@PathVariable long namespaceId, HttpSession session) {
		Item user = (Item) session.getAttribute("modified");
		//
	}

    @RequestMapping(value = "/updateItem", method = RequestMethod.POST)
    public void update(HttpSession session, @RequestBody Item item) {
        Set<Item> items = (Set<Item>) session.getAttribute("items");
        if(items == null){
            items = new HashSet<>();
            session.setAttribute("items", items);
        }
        items.add(item);
    }

    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    public void deploy(HttpSession session, @RequestBody Item item) {

        Set<Item> items = (Set<Item>) session.getAttribute("items");
    }
}
