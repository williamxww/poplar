package com.bow.zookeeper;

import org.junit.Before;
import org.junit.Test;


/**
 * @author wwxiang
 * @since 2017/12/27.
 */
public class ZookeeperClientTest {



    private ZookeeperClient client;


    @Before
    public void before() {
        client = new CuratorZookeeperClient("127.0.0.1:2181");
    }


    @Test
    public void forceCreate() throws Exception {
        client.forceCreate("/a/b", false);
    }

    @Test
    public void create() throws Exception {
        String path = "/a/b/c";
        client.create(path, false);
        client.setData(path, "hello".getBytes());
    }

    @Test
    public void create1() throws Exception {
        String path = "/d/b/c";
        client.create(path, false);
//        client.setData(path, "hello".getBytes());
    }

}