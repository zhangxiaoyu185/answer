package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreClass;

import java.util.List;

/**
* 班级表
*/
public interface CoreClassService {

	/**
	* 添加
	* @param coreClass
	* @return
	*/
	public boolean insertCoreClass(CoreClass coreClass);

	/**
	* 修改
	* @param coreClass
	* @return
	*/
	public boolean updateCoreClass(CoreClass coreClass);

	/**
	* 删除
	* @param coreClass
	* @return
	*/
	public boolean deleteCoreClass(CoreClass coreClass);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param coreClass
	* @return
	*/
	public CoreClass getCoreClass(CoreClass coreClass);

	/**
	* 查询所有List
	* @return List
	*/
	public List<CoreClass> findCoreClassList(String crcasSchool);

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<CoreClass> findCoreClassPage(CoreClass coreClass, int pageNum, int pageSize);

}