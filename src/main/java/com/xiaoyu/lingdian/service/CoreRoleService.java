package com.xiaoyu.lingdian.service;

import java.util.List;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreRole;

public interface CoreRoleService {

	/**
	* 添加
	* @param coreRole
	* @return
	*/
	public boolean insertCoreRole(CoreRole coreRole);

	/**
	* 修改
	* @param coreRole
	* @return
	*/
	public boolean updateCoreRole(CoreRole coreRole);

	/**
	* 删除
	* @param coreRole
	* @return
	*/
	public boolean deleteCoreRole(CoreRole coreRole);

	/**
	* 查询
	* @param coreRole
	* @return
	*/
	public CoreRole getCoreRole(CoreRole coreRole);

//<=================定制内容开始==============
//==================定制内容结束==============>
	
	/**
	* 获取所有的角色列表(分页)
	* @param pageNum
	* @param pageSize
	* @return
	*/
	public Page<CoreRole> findCoreRolePage(int pageNum, int pageSize);
	
	/**
	* 获取所有的角色列表
	* @return
	*/
	public List<CoreRole> findCoreRoleList();
	
	/**
	* 查询ids对应名称角色
	* @param ids
	* @return
	*/
	public List<CoreRole> findCoreRoleByIds(List<String> ids);
	
	/**
	* 批量删除
	* 
	* @param list
	* @return boolean
	*/
	public boolean deleteCoreRoleByCnd(List<String> list);
	
	/**
	* 查询
	* @param coreRole
	* @return
	*/
	public CoreRole getCoreRoleByName(CoreRole coreRole);
	
}