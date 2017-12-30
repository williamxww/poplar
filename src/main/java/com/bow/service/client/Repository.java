package com.bow.service.client;

import com.bow.utils.ListUtil;
import com.bow.utils.PropertiesUtil;
import com.bow.zookeeper.ChildListener;
import com.bow.zookeeper.NodeListener;
import com.bow.zookeeper.ZooKeeperClientFactory;
import com.bow.zookeeper.ZookeeperClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wwxiang
 * @since 2017/12/28.
 */
public class Repository {

	private static final Logger LOGGER = LoggerFactory.getLogger(Repository.class);

	private static final String ROOT = "/config";
	private static final String SEPARATOR = "/";

	private String appId;

	private ZookeeperClient client;

	/**
	 * Map<namespace, Map<key, value>>
	 */
	private Map<String, Map<String, String>> localRepo = new ConcurrentHashMap<>();
	private Map<String, Map<String, String>> zkRepo = new ConcurrentHashMap<>();

	public Repository(String appId) {
		this.appId = appId;
		this.client = ZooKeeperClientFactory.getClient();
	}

	/**
	 * 1.关注命名空间节点的个数
	 * 2.关注各个空间的内容
     */
	public void subscribe() {

		// 确保APP节点存在
		String appPath = ROOT + SEPARATOR + appId;
		if(!client.exists(appPath)){
			client.create(appPath, false);
		}

		// 监听APP节点
		List<String> namespaces = client.addChildListener(appPath, new ChildListener() {
			@Override
			public void childChanged(String path, List<String> children) {

				List<String> current = new ArrayList(zkRepo.keySet());
				List<String> add = ListUtil.subtract(children, current);
				List<String> delete = ListUtil.subtract(current, children);

				for (String ns : add) {
					LOGGER.info("Add node "+path+ SEPARATOR + ns);
					// 添加节点后,新增监听;
					String initContent = client.addNodeListener(appPath + SEPARATOR + ns, new NodeListenerImpl());
					//初始化对应空间的zkRepo
					handleContentChange(ns, initContent);
				}

				for (String ns : delete) {
					LOGGER.info("Delete node "+path+ SEPARATOR + ns);
					// 移除zkRepo中的配置
					zkRepo.remove(ns);
				}
			}
		});

		// 监听namespace节点内容
		for (String namespace : namespaces) {
			// 新增监听;
			String initContent = client.addNodeListener(appPath + SEPARATOR + namespace, new NodeListenerImpl());
			// 初始化对应空间的zkRepo
			handleContentChange(namespace, initContent);
		}
	}



	private class NodeListenerImpl implements NodeListener{

		@Override
		public void nodeChanged(String path, String content) {
			int idx = path.lastIndexOf(SEPARATOR);
			String namespace = path.substring(idx+1);
			handleContentChange(namespace, content);
		}
	}

	private void handleContentChange(String namespace, String content) {
		LOGGER.info(namespace + " content changed.");
		try {
			Properties props = PropertiesUtil.parse(content);
			Map<String, String> contentMap = PropertiesUtil.format(props);
			zkRepo.put(namespace, contentMap);
		} catch (IOException e) {
			LOGGER.error("Content change exception", e);
		}
	}

	/**
	 * 将本地文件加载到仓库中
	 * 
	 * @param namespace
	 * @param properties
	 */
	public void loadFile(String namespace, Properties properties) {
		Map<String, String> content = PropertiesUtil.format(properties);
		localRepo.put(namespace, content);
	}

	public String getProperty(String namespace, String key) {
		String result = null;

		// 从ZK获取
		Map<String, String> zkProps = zkRepo.get(namespace);
		if(zkProps != null){
			result = zkProps.get(key);
		}
		// 获取到值后直接返回
		if(result != null){
			return result;
		}

		// 从本地获取
		Map<String, String> localProps = localRepo.get(namespace);
		if(localProps != null){
			result = localProps.get(key);
			LOGGER.warn("Config from local "+key+"="+result);
		}
		return result;
	}
}
