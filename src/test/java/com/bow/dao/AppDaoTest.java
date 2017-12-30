package com.bow.dao;

import com.bow.entity.App;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * @author wwxiang
 * @since 2017/12/27.
 */
public class AppDaoTest {

    private AppDao dao;

    @Before
    public void before() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext-mybatis.xml");
        dao = context.getBean(AppDao.class);
    }

    @Test
    public void getApp() throws Exception {

    }

    @Test
    public void addApp() throws Exception {
        App app = new App();
        app.setAppName("UserManager");
        app.setComment("用户管理");
        dao.addApp(app);
    }

    @Test
    public void deleteApp() throws Exception {

    }

    @Test
    public void updateApp() throws Exception {

    }

}