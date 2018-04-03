package com.xiaoyu.lingdian.entity.weixin;

import com.xiaoyu.lingdian.entity.BaseEntity;


public class ShareConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * url路径
	 */
	private String url;
	
	/**
	 * appid
	 */
	private String appid;
	
	/**
	 * 微信加密签名
	 */
	private String signature;
	
	/**
	 * 时间戳
	 */
	private String timestamp;
	
	/**
	 * 随机数
	 */
	private String nonceStr;
	
	/**
	 * jsapi_ticket
	 */
	private String jsapi_ticket;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getJsapi_ticket() {
		return jsapi_ticket;
	}

	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}

}