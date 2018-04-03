package com.xiaoyu.lingdian.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.xiaoyu.lingdian.service.CoreRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreRole;

@Service("coreRoleService")
public class CoreRoleServiceImpl implements CoreRoleService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertCoreRole(CoreRole coreRole) {
		myBatisDAO.insert(coreRole);
		return true;
	}

	@Override
	public boolean updateCoreRole(CoreRole coreRole) {
		myBatisDAO.update(coreRole);
		return true;
	}

	@Override
	public boolean deleteCoreRole(CoreRole coreRole) {
		myBatisDAO.delete(coreRole);
		return true;
	}

	@Override
	public CoreRole getCoreRole(CoreRole coreRole) {
		return (CoreRole) myBatisDAO.findForObject(coreRole);
	}


//<=================定制内容开始==============
//==================定制内容结束==============>

	private static final String FIND_COREROLE_LIST = "findCoreRoleList";
	private static final String FIND_COREROLE_BY_IDS = "findCoreRoleByIds";
	private static final String DELETE_COREROLE_BY_CND = "deleteCoreRoleByCnd";
	private static final String GET_CORE_ROLE_BY_NAME = "getCoreRoleByName";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CoreRole> findCoreRoleList() {
		return myBatisDAO.findForList(FIND_COREROLE_LIST);
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CoreRole> findCoreRoleByIds(List<String> ids) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", ids);
		return myBatisDAO.findForList(FIND_COREROLE_BY_IDS, hashMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<CoreRole> findCoreRolePage(int pageNum, int pageSize) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		return myBatisDAO.findForPage(FIND_COREROLE_LIST, new PageRequest(pageNum, pageSize, hashMap));
	}

	@Override
	public boolean deleteCoreRoleByCnd(List<String> list) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete(DELETE_COREROLE_BY_CND, hashMap);
		return true;
	}

	@Override
	public CoreRole getCoreRoleByName(CoreRole coreRole) {
		return (CoreRole) myBatisDAO.findForObject(GET_CORE_ROLE_BY_NAME, coreRole);
	}
	
}