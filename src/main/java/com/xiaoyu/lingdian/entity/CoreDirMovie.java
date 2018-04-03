package com.xiaoyu.lingdian.entity;

import com.xiaoyu.lingdian.entity.BaseEntity;

/**
* 视频目录表
*/
public class CoreDirMovie extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	*标识UNID
	*/
	private Integer crdirUnid;

	/**
	*标识UUID
	*/
	private String crdirUuid;

	/**
	*目录名称
	*/
	private String crdirName;

	public void setCrdirUnid(Integer crdirUnid) {
		this.crdirUnid = crdirUnid;
	}

	public Integer getCrdirUnid( ) {
		return crdirUnid;
	}

	public void setCrdirUuid(String crdirUuid) {
		this.crdirUuid = crdirUuid;
	}

	public String getCrdirUuid( ) {
		return crdirUuid;
	}

	public void setCrdirName(String crdirName) {
		this.crdirName = crdirName;
	}

	public String getCrdirName( ) {
		return crdirName;
	}

	public CoreDirMovie( ) { 
	}

//<=================定制内容开始==============
//==================定制内容结束==============>

}