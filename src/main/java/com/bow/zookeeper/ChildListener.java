package com.bow.zookeeper;

import java.util.List;

/**
 * 子节点监听器
 */
public interface ChildListener {

	/**
	 * 子节点变更（增删）时触发
	 * 
	 * @param path 监听节点
	 * @param children 变化后的所有子节点
	 */
	void childChanged(String path, List<String> children);

}
