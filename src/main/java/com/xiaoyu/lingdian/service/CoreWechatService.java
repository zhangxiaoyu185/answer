package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.entity.CoreWechat;
import com.xiaoyu.lingdian.entity.weixin.ShareConfig;

import java.util.List;

import com.xiaoyu.lingdian.core.mybatis.page.Page;

/**
* 公众号表
*/
public interface CoreWechatService {

	/**
	* 添加
	* @param coreWechat
	* @return
	*/
	public boolean insertCoreWechat(CoreWechat coreWechat);

	/**
	* 修改
	* @param coreWechat
	* @return
	*/
	public boolean updateCoreWechat(CoreWechat coreWechat);

	/**
	 * 修改
	 * @param coreWechat
	 * @return
	 */
	public boolean updateCoreWechatByAppid(CoreWechat coreWechat);

	/**
	* 查询
	* @param coreWechat
	* @return
	*/
	public CoreWechat getCoreWechat(CoreWechat coreWechat);

	/**
	* 根据微信名称查询
	* @param coreWechat
	* @return
	*/
	public CoreWechat getCoreWechatByName(CoreWechat coreWechat);
	
	/**
	* 查询所有List
	* @param crwctName
	* @return List
	*/
	public List<CoreWechat> findCoreWechatList(String crwctName);

	/**
	* 查询所有Page
	* @param crwctName
	* @return Page
	*/
	public Page<CoreWechat> findCoreWechatPage(String crwctName, int pageNum, int pageSize);

	/**
	 * 根据公众号UUID获取全局access_token
	 * @param crwctUuid
	 */
	public String getAccessToken(String crwctUuid);

	/**
	 * 根据公众号UUID获取微信JS-SDK接口的配置
	 * @param crwctUuid
	 * @param urlString
	 */
	public ShareConfig getShareConfig(String crwctUuid, String urlString);

}