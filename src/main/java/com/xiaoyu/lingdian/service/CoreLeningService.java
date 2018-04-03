package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreLening;

import java.util.List;

/**
* 教辅表
*/
public interface CoreLeningService {

	/**
	* 添加
	* @param coreLening
	* @return
	*/
	public boolean insertCoreLening(CoreLening coreLening);

	/**
	* 修改
	* @param coreLening
	* @return
	*/
	public boolean updateCoreLening(CoreLening coreLening);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param coreLening
	* @return
	*/
	public CoreLening getCoreLening(CoreLening coreLening);

	/**
	* 查询所有List
	* @return List
	*/
	public List<CoreLening> findCoreLeningList();

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<CoreLening> findCoreLeningPage(CoreLening coreLening, int pageNum, int pageSize);

	/**
	 * 学生拥有的教辅权限列表
	 */
	public List<CoreLening> findCoreLeningForListsByUser(String user);

	/**
	 * 学生拥有的教辅权限列表
	 */
	public Page<CoreLening> findCoreLeningForListsByUser(String user, int pageNum, int pageSize);

}