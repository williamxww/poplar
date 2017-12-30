package com.bow.dao;

import com.bow.entity.App;

/**
 * @author wwxiang
 * @since 2017/12/27.
 */
public interface AppDao {

    App getApp(int id);
    boolean addApp(App app);
    boolean deleteApp(int id);
    boolean updateApp(App app);
}
