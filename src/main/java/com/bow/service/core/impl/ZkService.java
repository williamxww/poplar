package com.bow.service.core.impl;

import com.bow.service.core.IZkService;
import com.bow.zookeeper.ZooKeeperClientFactory;
import com.bow.zookeeper.ZookeeperClient;

import java.util.List;

/**
 * @author wwxiang
 * @since 2017/12/29.
 */
public class ZkService implements IZkService{

	private static final String ROOT = "/config";

	private static final String SEPARATOR = "/";

	private ZookeeperClient client;

	public ZkService() {
		this.client = ZooKeeperClientFactory.getClient();
		if (!client.exists(ROOT)) {
			client.create(ROOT, false);
		}
	}

	@Override
	public boolean createApp(String appId) {
		String path = ROOT + SEPARATOR + appId;
		client.create(path, false);
		return true;
	}

	@Override
	public boolean createNamespace(String appId, String namespace, String content) {
		// 调用此方法前,请确保config/appId节点一定存在
		String path = ROOT + SEPARATOR + appId + SEPARATOR + namespace;
		client.create(path, false, content.getBytes());
		return true;
	}

	@Override
	public boolean notifyZk(String appId, String namespace, String content) {
		String path = ROOT + SEPARATOR + appId + SEPARATOR + namespace;
		client.setData(path, content.getBytes());
		return true;
	}

    @Override
    public boolean deleteApp(String appId) {
        return false;
    }

    @Override
    public boolean deleteNamespace(String appId, String namespace) {
        return false;
    }

    @Override
    public List<String> getApps() {
        return null;
    }

    @Override
    public List<String> getNamespace(String appId) {
        return null;
    }

    @Override
    public String getItems(String appId, String namespace) {
        return null;
    }
}
