package com.xiaoyu.lingdian.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.xiaoyu.lingdian.service.CoreFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreFunction;

@Service("coreFunctionService")
public class CoreFunctionServiceImpl implements CoreFunctionService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertCoreFunction(CoreFunction coreFunction) {
		myBatisDAO.insert(coreFunction);
		return true;
	}

	@Override
	public boolean updateCoreFunction(CoreFunction coreFunction) {
		myBatisDAO.update(coreFunction);
		return true;
	}

	@Override
	public boolean deleteCoreFunction(CoreFunction coreFunction) {
		myBatisDAO.delete(coreFunction);
		return true;
	}

	@Override
	public CoreFunction getCoreFunction(CoreFunction coreFunction) {
		return (CoreFunction) myBatisDAO.findForObject(coreFunction);
	}
	
	private static final String FIND_COREFUNCTIONS_BYIDS = "findCoreFunctionsByIds";	
	private static final String GET_COREFUNCTIONS = "getCoreFunctions";
	private static final String DELETE_COREFUNCTIONS_BY_CND = "deleteCoreFunctionsByCnd";
	private static final String UPDATE_COREFUNCTION_BY_CND = "updateCoreFunctionByCnd";
	private static final String GET_COREFUNCTION_BY_CND = "getCoreFunctionByCnd";
	private static final String UPDATE_COREFUNCTION_BY_FATHER = "updateCoreFunctionByFather";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CoreFunction> findCoreFunctionsByIds(List<String> ids) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", ids);
		return myBatisDAO.findForList(FIND_COREFUNCTIONS_BYIDS, hashMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CoreFunction> findCoreFunctionList() {
		return myBatisDAO.findForList(GET_COREFUNCTIONS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<CoreFunction> findCoreFunctionPage(int pageNum, int pageSize) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		return myBatisDAO.findForPage(GET_COREFUNCTIONS, new PageRequest(pageNum, pageSize, hashMap));
	}

	@Override
	public boolean deleteCoreFunctionsByCnd(List<String> list) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete(DELETE_COREFUNCTIONS_BY_CND, hashMap);
		return true;
	}

	@Override
	public boolean updateCoreFunctionByCnd(List<String> list, Integer crfntStatus) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		hashMap.put("crfntStatus", crfntStatus);
		myBatisDAO.update(UPDATE_COREFUNCTION_BY_CND, hashMap);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoreFunction> getCoreFunctionByCnd() {
		return myBatisDAO.findForList(GET_COREFUNCTION_BY_CND);
	}

	@Override
	public boolean updateCoreFunctionByFather(CoreFunction coreFunction) {
		myBatisDAO.update(UPDATE_COREFUNCTION_BY_FATHER, coreFunction);
		return true;
	}
	
}