package com.bow.zookeeper;

/**
 * 监听节点内容变化
 * 
 * @author wwxiang
 * @since 2017/12/28.
 */
public interface NodeListener {

	void nodeChanged(String path, String content);
}
