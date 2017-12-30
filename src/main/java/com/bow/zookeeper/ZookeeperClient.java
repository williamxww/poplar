package com.bow.zookeeper;

import java.util.List;

public interface ZookeeperClient {

	/**
	 * create a node /a/b/c 只有当/a/b存在时，才可以创建c
	 * 
	 * @param path path
	 * @param ephemeral ephemeral
	 */
	void create(String path, boolean ephemeral);

	/**
	 *
	 * @param path node
	 * @param data data
	 */
	void create(String path, boolean ephemeral, byte[] data);

	/**
	 * /a/b/c 当/a/b不存在时也能够创建
	 * 
	 * @param path /a/b/c
	 * @param ephemeral 临时节点，连接断开后session失效节点即删除
	 */
	@Deprecated
	void forceCreate(String path, boolean ephemeral);

	byte[] getData(String path);

	void setData(String path, byte[] content);

	boolean exists(String path);

	/**
	 * delete a node
	 * 
	 * @param path path
	 */
	void delete(String path);

	List<String> getChildren(String path);

    /**
     * 监控path的子节点变化(子节点的增删)
     *
     * @param path 监控节点
     * @param listener 子节点监听器
     * @return path的子节点
     */
	List<String> addChildListener(String path, ChildListener listener);

	String addNodeListener(String path, NodeListener listener);

	void removeChildListener(String path, ChildListener listener);

	void addStateListener(StateListener listener);

	void removeStateListener(StateListener listener);

	boolean isConnected();

	void close();

}
