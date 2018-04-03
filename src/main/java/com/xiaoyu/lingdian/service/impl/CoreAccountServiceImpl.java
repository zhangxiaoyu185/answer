package com.xiaoyu.lingdian.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.xiaoyu.lingdian.service.CoreAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreAccount;

@Service("coreAccountService")
public class CoreAccountServiceImpl implements CoreAccountService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertCoreAccount(CoreAccount coreAccount) {
		myBatisDAO.insert(coreAccount);
		return true;
	}

	@Override
	public boolean updateCoreAccount(CoreAccount coreAccount) {
		myBatisDAO.update(coreAccount);
		return true;
	}

	@Override
	public boolean deleteCoreAccount(CoreAccount coreAccount) {
		myBatisDAO.delete(coreAccount);
		return true;
	}

	@Override
	public CoreAccount getCoreAccount(CoreAccount coreAccount) {
		return (CoreAccount) myBatisDAO.findForObject(coreAccount);
	}

	private static final String GET_COREACCOUNTS = "getCoreAccounts"; 
	private static final String DELETE_ACCOUNT_BY_CND = "deleteCoreAccountByCnd";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CoreAccount> getCoreAccounts() {
		return myBatisDAO.findForList(GET_COREACCOUNTS,null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<CoreAccount> findCoreAccountPage(int pageNum, int pageSize) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		return myBatisDAO.findForPage(GET_COREACCOUNTS, new PageRequest(pageNum, pageSize, hashMap));
	}

	@Override
	public boolean deleteCoreAccountByCnd(List<String> list) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete(DELETE_ACCOUNT_BY_CND, hashMap);
		return true;
	}

}