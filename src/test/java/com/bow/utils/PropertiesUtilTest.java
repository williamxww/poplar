package com.bow.utils;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author wwxiang
 * @since 2017/12/28.
 */
public class PropertiesUtilTest {

    @Test
    public void parse() throws Exception {

        String s = "ignite.tcp.discovery.port=54306\n" +
                "ignite.tcp.discovery.port.range=5\n" +
                "ignite.tcp.discovery.addresses=127.0.0.1:54306..54310\n" +
                "ignite.tcp.communication.port=54311";
        Properties properties = PropertiesUtil.parse(s);
        System.out.println(properties.get("ignite.tcp.communication.port"));
    }

}