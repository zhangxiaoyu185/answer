package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreSchool;

import java.util.List;

/**
* 学校表
*/
public interface CoreSchoolService {

	/**
	* 添加
	* @param coreSchool
	* @return
	*/
	public boolean insertCoreSchool(CoreSchool coreSchool);

	/**
	* 修改
	* @param coreSchool
	* @return
	*/
	public boolean updateCoreSchool(CoreSchool coreSchool);

	/**
	* 删除
	* @param coreSchool
	* @return
	*/
	public boolean deleteCoreSchool(CoreSchool coreSchool);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param coreSchool
	* @return
	*/
	public CoreSchool getCoreSchool(CoreSchool coreSchool);

	/**
	* 查询所有List
	* @return List
	*/
	public List<CoreSchool> findCoreSchoolList();

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<CoreSchool> findCoreSchoolPage(CoreSchool coreSchool, int pageNum, int pageSize);

}