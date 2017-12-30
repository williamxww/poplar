package com.bow.entity;

/**
 * @author wwxiang
 * @since 2017/12/27.
 */
public class AppVo extends App {

    public AppVo(App app){
        super();
        this.setAppId(app.getAppId());
        this.setAppName(app.getAppName());
        this.setComment(app.getComment());
        this.setCreateTime(app.getCreateTime());
        this.setUpdateTime(app.getUpdateTime());
    }
    /**
     * -1：删除
     * 0:未修改
     * 1：新增
     * 2：修改
     */
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
