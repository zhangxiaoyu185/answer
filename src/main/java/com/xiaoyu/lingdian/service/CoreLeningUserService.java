package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.entity.CoreLeningUser;

import java.util.List;

/**
* 教辅学生表
*/
public interface CoreLeningUserService {

	/**
	* 添加
	* @param coreLeningUser
	* @return
	*/
	public boolean insertCoreLeningUser(CoreLeningUser coreLeningUser);

	/**
	* 删除
	* @param coreLeningUser
	* @return
	*/
	public boolean deleteCoreLeningUser(CoreLeningUser coreLeningUser);

	/**
	 * 查询用户是否拥有教辅权限
	 */
	public List<CoreLeningUser> findCoreLeningUserByPaperAndUser(String paper, String user);
}