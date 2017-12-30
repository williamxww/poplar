package com.bow.utils;

import org.apache.commons.dbcp.BasicDataSource;

public class EncryptDataSource extends BasicDataSource {

	@Override
	public void setPassword(String password) {
		// 解密后重置
		String newPassword = EncryptUtils.decodeBase64String(password);
		super.setPassword(newPassword);
	}
}
