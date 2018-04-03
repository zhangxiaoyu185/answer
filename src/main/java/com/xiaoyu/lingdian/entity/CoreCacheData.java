package com.xiaoyu.lingdian.entity;

import com.xiaoyu.lingdian.entity.BaseEntity;

/**
* 缓存数据表
*/
public class CoreCacheData extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	*标识UNID
	*/
	private Integer crcdaUnid;

	/**
	*标识UUID
	*/
	private String crcdaUuid;

	/**
	*缓存数据名称
	*/
	private String crcdaName;

	/**
	*缓存数据值
	*/
	private String crcdaValue;

	public void setCrcdaUnid(Integer crcdaUnid) {
		this.crcdaUnid = crcdaUnid;
	}

	public Integer getCrcdaUnid( ) {
		return crcdaUnid;
	}

	public void setCrcdaUuid(String crcdaUuid) {
		this.crcdaUuid = crcdaUuid;
	}

	public String getCrcdaUuid( ) {
		return crcdaUuid;
	}

	public void setCrcdaName(String crcdaName) {
		this.crcdaName = crcdaName;
	}

	public String getCrcdaName( ) {
		return crcdaName;
	}

	public void setCrcdaValue(String crcdaValue) {
		this.crcdaValue = crcdaValue;
	}

	public String getCrcdaValue( ) {
		return crcdaValue;
	}

	public CoreCacheData( ) { 
	}

//<=================定制内容开始==============
//==================定制内容结束==============>

}