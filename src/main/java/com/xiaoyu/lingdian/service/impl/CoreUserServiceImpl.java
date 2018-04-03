package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.CoreUserService;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 用户表
*/
@Service("coreUserService")
public class CoreUserServiceImpl implements CoreUserService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertCoreUser(CoreUser coreUser) {
		myBatisDAO.insert(coreUser);
		return true;
	}

	@Override
	public boolean updateCoreUser(CoreUser coreUser) {
		myBatisDAO.update(coreUser);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		if(CollectionUtils.isEmpty(list)) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.update("deleteBatchCoreUserByIds",hashMap);
		return true;
	}

	@Override
	public CoreUser getCoreUser(CoreUser coreUser) {
		return (CoreUser) myBatisDAO.findForObject(coreUser);
	}

	/**
	 * 查询最大UNID
	 * @return
	 */
	public Integer getMaxUnidByUser(){
		Integer max = (Integer) myBatisDAO.findForObject("getMaxUnidByUser", null);
		return max==null?0:max;
	}

	@Override
	public CoreUser getCoreUserByName(CoreUser coreUser) {
		return (CoreUser) myBatisDAO.findForObject("getCoreUserByName",coreUser);
	}

	public CoreUser loginByNameOrMobile(CoreUser coreUser) {
		List<CoreUser> list = myBatisDAO.findForList("loginByNameOrMobile",coreUser);
		if(!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoreUser> findCoreUserList() {
		return myBatisDAO.findForList("findCoreUserForLists", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoreUser> findCoreUserListByParam(CoreUser coreUser, String lening){
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(coreUser);
		hashMap.put("lening", lening);
		return myBatisDAO.findForList("findCoreUserForPages", hashMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<CoreUser> findCoreUserPage(CoreUser coreUser, String lening, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(coreUser);
		hashMap.put("lening", lening);
		return myBatisDAO.findForPage("findCoreUserForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

	@Override
	public List<CoreUser> findCoreUserForListsByLening(String lening){
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("lening", lening);
		return myBatisDAO.findForList("findCoreUserForListsByLening", hashMap);
	}

	@Override
	public Page<CoreUser> findCoreUserForListsByLening(String lening, int pageNum, int pageSize){
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("lening", lening);
		return myBatisDAO.findForPage("findCoreUserForListsByLening", new PageRequest(pageNum, pageSize, hashMap));
	}

}