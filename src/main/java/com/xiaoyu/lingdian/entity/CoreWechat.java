package com.xiaoyu.lingdian.entity;

import com.xiaoyu.lingdian.entity.BaseEntity;
import java.util.Date;

/**
* 公众号表
*/
public class CoreWechat extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	*标识UNID
	*/
	private Integer crwctUnid;

	/**
	*标识UUID
	*/
	private String crwctUuid;

	/**
	*公众号名称
	*/
	private String crwctName;

	/**
	*appid
	*/
	private String crwctAppid;

	/**
	*appsecret
	*/
	private String crwctAppsecret;

	/**
	*accessToken(全局)
	*/
	private String crwctAccessToken;

	/**
	*accessTokenLastTime
	*/
	private Date crwctAccessTime;

	/**
	*jsapiTicket(js-sdk)
	*/
	private String crwctJsapiTicket;

	/**
	*jsapiTicketLastTime
	*/
	private Date crwctJsapiTime;

	/**
	*商户号partner/mch_id
	*/
	private String crwctPartner;

	/**
	*商户密钥partnerkey
	*/
	private String crwctPartnerkey;

	/**
	*回调URLnotifyurl
	*/
	private String crwctNotifyurl;

	/**
	*状态:1启用,0禁用
	*/
	private Integer crwctStatus;

	/**
	*创建日期
	*/
	private Date crwctCdate;

	/**
	*修改日期
	*/
	private Date crwctUdate;

	/**
	*备注
	*/
	private String crwctRemarks;

	public void setCrwctUnid(Integer crwctUnid) {
		this.crwctUnid = crwctUnid;
	}

	public Integer getCrwctUnid( ) {
		return crwctUnid;
	}

	public void setCrwctUuid(String crwctUuid) {
		this.crwctUuid = crwctUuid;
	}

	public String getCrwctUuid( ) {
		return crwctUuid;
	}

	public void setCrwctName(String crwctName) {
		this.crwctName = crwctName;
	}

	public String getCrwctName( ) {
		return crwctName;
	}

	public void setCrwctAppid(String crwctAppid) {
		this.crwctAppid = crwctAppid;
	}

	public String getCrwctAppid( ) {
		return crwctAppid;
	}

	public void setCrwctAppsecret(String crwctAppsecret) {
		this.crwctAppsecret = crwctAppsecret;
	}

	public String getCrwctAppsecret( ) {
		return crwctAppsecret;
	}

	public void setCrwctAccessToken(String crwctAccessToken) {
		this.crwctAccessToken = crwctAccessToken;
	}

	public String getCrwctAccessToken( ) {
		return crwctAccessToken;
	}

	public void setCrwctAccessTime(Date crwctAccessTime) {
		this.crwctAccessTime = crwctAccessTime;
	}

	public Date getCrwctAccessTime( ) {
		return crwctAccessTime;
	}

	public void setCrwctJsapiTicket(String crwctJsapiTicket) {
		this.crwctJsapiTicket = crwctJsapiTicket;
	}

	public String getCrwctJsapiTicket( ) {
		return crwctJsapiTicket;
	}

	public void setCrwctJsapiTime(Date crwctJsapiTime) {
		this.crwctJsapiTime = crwctJsapiTime;
	}

	public Date getCrwctJsapiTime( ) {
		return crwctJsapiTime;
	}

	public void setCrwctPartner(String crwctPartner) {
		this.crwctPartner = crwctPartner;
	}

	public String getCrwctPartner( ) {
		return crwctPartner;
	}

	public void setCrwctPartnerkey(String crwctPartnerkey) {
		this.crwctPartnerkey = crwctPartnerkey;
	}

	public String getCrwctPartnerkey( ) {
		return crwctPartnerkey;
	}

	public void setCrwctNotifyurl(String crwctNotifyurl) {
		this.crwctNotifyurl = crwctNotifyurl;
	}

	public String getCrwctNotifyurl( ) {
		return crwctNotifyurl;
	}

	public void setCrwctStatus(Integer crwctStatus) {
		this.crwctStatus = crwctStatus;
	}

	public Integer getCrwctStatus( ) {
		return crwctStatus;
	}

	public void setCrwctCdate(Date crwctCdate) {
		this.crwctCdate = crwctCdate;
	}

	public Date getCrwctCdate( ) {
		return crwctCdate;
	}

	public void setCrwctUdate(Date crwctUdate) {
		this.crwctUdate = crwctUdate;
	}

	public Date getCrwctUdate( ) {
		return crwctUdate;
	}

	public void setCrwctRemarks(String crwctRemarks) {
		this.crwctRemarks = crwctRemarks;
	}

	public String getCrwctRemarks( ) {
		return crwctRemarks;
	}

	public CoreWechat( ) { 
	}

//<=================定制内容开始==============
//==================定制内容结束==============>

}