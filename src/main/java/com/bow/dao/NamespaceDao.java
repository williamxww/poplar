package com.bow.dao;

import com.bow.entity.Namespace;

/**
 * @author wwxiang
 * @since 2017/12/27.
 */
public interface NamespaceDao {

	Namespace getNamespace(int id);

	boolean addNamespace(Namespace namespace);

	boolean deleteNamespace(int id);

	boolean updateNamespace(Namespace namespace);
}
