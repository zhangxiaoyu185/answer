package com.xiaoyu.lingdian.service;

import java.util.List;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreFunction;

public interface CoreFunctionService {

	/**
	* 添加
	* @param coreFunction
	* @return
	*/
	public boolean insertCoreFunction(CoreFunction coreFunction);

	/**
	* 修改
	* @param coreFunction
	* @return
	*/
	public boolean updateCoreFunction(CoreFunction coreFunction);

	/**
	* 删除
	* @param coreFunction
	* @return
	*/
	public boolean deleteCoreFunction(CoreFunction coreFunction);

	/**
	* 查询
	* @param coreFunction
	* @return
	*/
	public CoreFunction getCoreFunction(CoreFunction coreFunction);
   
	/**
	* 获取所有的权限列表(分页)
	* @param pageNum
	* 
	* @return
	*/
	public Page<CoreFunction> findCoreFunctionPage(int pageNum, int pageSize);
	
	/**
	* 获取所有的权限列表
	* @return
	*/
	public List<CoreFunction> findCoreFunctionList();
	
	/**
	* 获取主菜单和一级菜单列表
	* @return
	*/
	public List<CoreFunction> getCoreFunctionByCnd();
	
	/**
	 * 获取角色下的权限名称
	 * @param ids
	 */
	public List<CoreFunction> findCoreFunctionsByIds(List<String> ids);
	
	/**
	* 批量删除
	* 
	* @param list
	* @return boolean
	*/
	public boolean deleteCoreFunctionsByCnd(List<String> list);
	
	/**
	* 批量启用禁用
	* 
	* @param list
	* @param crfntStatus
	* @return boolean
	*/
	public boolean updateCoreFunctionByCnd(List<String> list, Integer crfntStatus);
	
	/**
	* 根据父菜单统一修改子菜单
	* @param coreFunction
	* @return
	*/
	public boolean updateCoreFunctionByFather(CoreFunction coreFunction);
	
}