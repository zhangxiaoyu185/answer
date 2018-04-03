package com.xiaoyu.lingdian.vo;

import com.xiaoyu.lingdian.entity.CoreWechat;
import com.xiaoyu.lingdian.vo.BaseVO;
import com.xiaoyu.lingdian.tool.DateUtil;

/**
* 公众号表
*/
public class CoreWechatVO implements BaseVO {

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
	private String crwctAccessTime;

	/**
	*jsapiTicket(js-sdk)
	*/
	private String crwctJsapiTicket;

	/**
	*jsapiTicketLastTime
	*/
	private String crwctJsapiTime;

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
	private String crwctCdate;

	/**
	*修改日期
	*/
	private String crwctUdate;

	/**
	*备注
	*/
	private String crwctRemarks;

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

	public void setCrwctAccessTime(String crwctAccessTime) {
		this.crwctAccessTime = crwctAccessTime;
	}

	public String getCrwctAccessTime( ) {
		return crwctAccessTime;
	}

	public void setCrwctJsapiTicket(String crwctJsapiTicket) {
		this.crwctJsapiTicket = crwctJsapiTicket;
	}

	public String getCrwctJsapiTicket( ) {
		return crwctJsapiTicket;
	}

	public void setCrwctJsapiTime(String crwctJsapiTime) {
		this.crwctJsapiTime = crwctJsapiTime;
	}

	public String getCrwctJsapiTime( ) {
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

	public void setCrwctCdate(String crwctCdate) {
		this.crwctCdate = crwctCdate;
	}

	public String getCrwctCdate( ) {
		return crwctCdate;
	}

	public void setCrwctUdate(String crwctUdate) {
		this.crwctUdate = crwctUdate;
	}

	public String getCrwctUdate( ) {
		return crwctUdate;
	}

	public void setCrwctRemarks(String crwctRemarks) {
		this.crwctRemarks = crwctRemarks;
	}

	public String getCrwctRemarks( ) {
		return crwctRemarks;
	}

	public CoreWechatVO( ) { 
	}

	@Override
	public void convertPOToVO(Object poObj) {
		if (null == poObj) {
			return;
		}

		CoreWechat po = (CoreWechat) poObj;
		this.crwctUuid = po.getCrwctUuid();
		this.crwctName = po.getCrwctName();
		this.crwctAppid = po.getCrwctAppid();
		this.crwctAppsecret = po.getCrwctAppsecret();
		this.crwctAccessToken = po.getCrwctAccessToken();
		this.crwctAccessTime = po.getCrwctAccessTime()!=null?DateUtil.formatDefaultDate(po.getCrwctAccessTime()):"";
		this.crwctJsapiTicket = po.getCrwctJsapiTicket();
		this.crwctJsapiTime = po.getCrwctJsapiTime()!=null?DateUtil.formatDefaultDate(po.getCrwctJsapiTime()):"";
		this.crwctPartner = po.getCrwctPartner();
		this.crwctPartnerkey = po.getCrwctPartnerkey();
		this.crwctNotifyurl = po.getCrwctNotifyurl();
		this.crwctStatus = po.getCrwctStatus();
		this.crwctCdate = po.getCrwctCdate()!=null?DateUtil.formatDefaultDate(po.getCrwctCdate()):"";
		this.crwctUdate = po.getCrwctUdate()!=null?DateUtil.formatDefaultDate(po.getCrwctUdate()):"";
		this.crwctRemarks = po.getCrwctRemarks();
	}
//<=================定制内容开始==============
//==================定制内容结束==============>

}