package com.bow.service.client;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author wwxiang
 * @since 2017/12/28.
 */
public class RepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryTest.class);


    @Test
    public void subscribe() throws Exception {
        //订阅远程
        Repository commonRepo = new Repository("demoApp");
        commonRepo.subscribe();

        //加载本地
        Properties properties = new Properties();
        properties.setProperty("name","vv");
        commonRepo.loadFile("demo.prop", properties);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            String cmd = reader.readLine();
            String result = commonRepo.getProperty("demo.prop","name");
            System.out.println(result);
        }

    }

    public static void main(String[] args) throws Exception{
        RepositoryTest demo = new RepositoryTest();
        demo.subscribe();
    }

}