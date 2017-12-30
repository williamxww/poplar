package com.bow.service.core.impl;

import com.bow.zookeeper.ZooKeeperClientFactory;
import com.bow.zookeeper.ZookeeperClient;

/**
 * @author wwxiang
 * @since 2017/12/29.
 */
public class ZkService {

	private static final String ROOT = "/config";

	private static final String SEPARATOR = "/";

	private ZookeeperClient client;

	public ZkService() {
		this.client = ZooKeeperClientFactory.getClient();
		if (!client.exists(ROOT)) {
			client.create(ROOT, false);
		}
	}

	public boolean notifyZk(String appId, String namespace, String content) {
		String path = ROOT + SEPARATOR + appId + SEPARATOR + namespace;
		client.setData(path, content.getBytes());
		return true;
	}

	public boolean createApp(String appId) {
		String path = ROOT + SEPARATOR + appId;
		client.create(path, false);
		return true;
	}

	public boolean createNamespace(String appId, String namespace, String content) {
		// 调用此方法前,请确保config/appId节点一定存在
		String path = ROOT + SEPARATOR + appId + SEPARATOR + namespace;
		client.create(path, false, content.getBytes());
		return true;
	}
}
