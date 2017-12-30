package com.bow.zookeeper;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.Watcher;

public class CuratorZookeeperClient extends AbstractZookeeperClient {

    private static final Charset UTF8 = Charset.forName("UTF-8");
	private final CuratorFramework client;

	/**
	 * 创建客户端
	 */
	public CuratorZookeeperClient(String address) {
		Builder builder = CuratorFrameworkFactory.builder().connectString(address)
				.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(5000)
				.sessionTimeoutMs(15000);
		client = builder.build();
		// 监控当前连接的状态
		client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
			public void stateChanged(CuratorFramework client, ConnectionState state) {
				if (state == ConnectionState.LOST) {
					CuratorZookeeperClient.this.stateChanged(StateListener.DISCONNECTED);
				} else if (state == ConnectionState.CONNECTED) {
					CuratorZookeeperClient.this.stateChanged(StateListener.CONNECTED);
				} else if (state == ConnectionState.RECONNECTED) {
					CuratorZookeeperClient.this.stateChanged(StateListener.RECONNECTED);
				}
			}
		});
		client.start();
	}

	public void createPersistent(String path) {
		try {
			client.create().forPath(path);
		} catch (NodeExistsException e) {
			logger.warn(path + " node already exists");
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * 短暂的节点
	 * 
	 * @param path
	 */
	public void createEphemeral(String path) {
		try {
			client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
		} catch (NodeExistsException e) {
			logger.warn(path + " node already exists");
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public boolean exists(String path) {
		try {
			return client.checkExists().forPath(path) == null ? false : true;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public void create(String path, boolean ephemeral, byte[] data) {
		try {
			if (ephemeral) {
				client.create().withMode(CreateMode.EPHEMERAL).forPath(path, data);
			} else {
				client.create().withMode(CreateMode.PERSISTENT).forPath(path, data);
			}

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public byte[] getData(String path) {
		try {
			return client.getData().forPath(path);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void setData(String path, byte[] content) {
		try {
			client.setData().forPath(path, content);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void delete(String path) {
		try {
			client.delete().forPath(path);
		} catch (NoNodeException e) {
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public List<String> getChildren(String path) {
		try {
			return client.getChildren().forPath(path);
		} catch (NoNodeException e) {
			return null;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * 监控path的子节点变化(子节点的增删)
	 * 
	 * @param path 监控节点
	 * @param listener 子节点监听器
	 * @return path的子节点
	 */
	@Override
	public List<String> addChildListener(String path, ChildListener listener) {
		CuratorWatcher watcher = new ChildWatcherWrapper(listener);
		try {
			// 监控path的子节点变化
			return client.getChildren().usingWatcher(watcher).forPath(path);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

    @Override
	public String addNodeListener(String path, NodeListener listener) {
		CuratorWatcher watcher = new NodeWatcherWrapper(listener);
		try {
			// 监控path的子节点变化
			byte[] content =  client.getData().usingWatcher(watcher).forPath(path);
			return new String(content, UTF8);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public void removeChildListener(String path, ChildListener listener) {

	}

	public boolean isConnected() {
		return client.getZookeeperClient().isConnected();
	}

	public void doClose() {
		client.close();
	}

	/**
	 * 将上层的ChildListener包装为CuratorWatcher
	 */
	private class ChildWatcherWrapper implements CuratorWatcher {

		private ChildListener listener;

		public ChildWatcherWrapper(ChildListener listener) {
			this.listener = listener;
		}

        @Override
		public void process(WatchedEvent event) throws Exception {
			if (listener != null) {
				// 1.watcher是一次性的，所以触发后需要重新绑定
				// 2.client.getChildren().usingWatcher表明监控的是path的子节点是否发生变化
				List<String> children = client.getChildren().usingWatcher(this).forPath(event.getPath());
				listener.childChanged(event.getPath(), children);
			}
		}
	}


	private class NodeWatcherWrapper implements CuratorWatcher {

		private NodeListener listener;

		public NodeWatcherWrapper(NodeListener listener){
			this.listener = listener;
		}

		@Override
		public void process(WatchedEvent event) throws Exception {
			if (listener != null) {
                if(event.getType()==Watcher.Event.EventType.NodeDeleted){
                    // 节点被删除，就不做任何处理了
                    return;
                }
				// 重新添加监听
				byte[] content = client.getData().usingWatcher(this).forPath(event.getPath());
				// 触发业务监听器
				listener.nodeChanged(event.getPath(), new String(content, UTF8));
			}
		}
	}

}
