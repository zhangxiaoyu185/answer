package com.xiaoyu.lingdian.service;

import java.util.List;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreAccount;

public interface CoreAccountService {

	/**
	* 添加
	* @param coreAccount
	* @return
	*/
	public boolean insertCoreAccount(CoreAccount coreAccount);

	/**
	* 修改
	* @param coreAccount
	* @return
	*/
	public boolean updateCoreAccount(CoreAccount coreAccount);

	/**
	* 删除
	* @param coreAccount
	* @return
	*/
	public boolean deleteCoreAccount(CoreAccount coreAccount);

	/**
	* 查询
	* @param coreAccount
	* @return
	*/
	public CoreAccount getCoreAccount(CoreAccount coreAccount);
	
	/**
	* 查询所有的账户(分页)
	* @param pageNum
	* @param pageSize
	* @return
	*/
	public Page<CoreAccount> findCoreAccountPage(int pageNum, int pageSize);
	
	/**
	* 查询所有的账户
	* @param coreAccount
	* @return
	*/
	public List<CoreAccount> getCoreAccounts();

	/**
	* 批量删除
	* 
	* @param list
	* @return boolean
	*/
	public boolean deleteCoreAccountByCnd(List<String> list);
	
}