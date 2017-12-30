package com.bow.service.core.impl;

import com.bow.entity.App;
import com.bow.entity.Item;
import com.bow.entity.Namespace;
import com.bow.service.core.IPublishService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wwxiang
 * @since 2017/12/27.
 */
@Service
public class PublishService implements IPublishService {

    private static final Lock lock = new ReentrantLock();
    @Override
    public boolean publish(Set<App> apps, Set<Namespace> namespaces, Set<Item> items) {

        boolean lr;
        try {
            lr = lock.tryLock(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
        if(!lr){
            return false;
        }

        try{
            return true;
        }finally {
            lock.unlock();
        }

    }
}
