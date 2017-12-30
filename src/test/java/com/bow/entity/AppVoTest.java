package com.bow.entity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author wwxiang
 * @since 2017/12/28.
 */
public class AppVoTest {
    @Test
    public void getStatus() throws Exception {
        App app = new App();
        app.setAppId("user");
        AppVo vo = new AppVo(app);
        System.out.println(vo.getAppId());
    }

}