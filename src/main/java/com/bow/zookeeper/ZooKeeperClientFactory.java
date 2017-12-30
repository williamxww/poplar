package com.bow.zookeeper;

/**
 * @author wwxiang
 * @since 2017/12/28.
 */
public class ZooKeeperClientFactory {

	private static ZookeeperClient client = null;


	public static ZookeeperClient getClient() {
		if (client == null) {
			client = new CuratorZookeeperClient("127.0.0.1:2181");
		}
		return client;
	}
}
