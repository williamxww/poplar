package com.bow.service.client;

/**
 * @author wwxiang
 * @since 2017/12/28.
 */
public interface ConfigClient {

    String getProperty(String namespace, String key);
}
