package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreUser;

import java.util.List;

/**
* 用户表
*/
public interface CoreUserService {

	/**
	* 添加
	* @param coreUser
	* @return
	*/
	public boolean insertCoreUser(CoreUser coreUser);

	/**
	* 修改
	* @param coreUser
	* @return
	*/
	public boolean updateCoreUser(CoreUser coreUser);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	 * 根据学号查询
	 * @param coreUser
	 * @return
	 */
	public CoreUser getCoreUserByName(CoreUser coreUser);

	/**
	 * 根据账号或手机号登录
	 * @param coreUser
	 * @return
	 */
	public CoreUser loginByNameOrMobile(CoreUser coreUser);

	/**
	* 查询
	* @param coreUser
	* @return
	*/
	public CoreUser getCoreUser(CoreUser coreUser);

	/**
	 * 查询最大UNID
	 * @return
	 */
	public Integer getMaxUnidByUser();

	/**
	* 查询所有List
	* @return List
	*/
	public List<CoreUser> findCoreUserList();

	/**
	 * 根据条件查询所有List
	 * @return List
	 */
	public List<CoreUser> findCoreUserListByParam(CoreUser coreUser, String lening);

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<CoreUser> findCoreUserPage(CoreUser coreUser, String lening, int pageNum, int pageSize);

	/**
	 * 教辅下的所有学生List
	 * @return List
	 */
	public List<CoreUser> findCoreUserForListsByLening(String lening);

	/**
	 * 教辅下的所有学生Page
	 * @return Page
	 */
	public Page<CoreUser> findCoreUserForListsByLening(String lening, int pageNum, int pageSize);

}