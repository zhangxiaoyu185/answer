package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreLening;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.CoreLeningService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 教辅表
*/
@Service("coreLeningService")
public class CoreLeningServiceImpl implements CoreLeningService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertCoreLening(CoreLening coreLening) {
		myBatisDAO.insert(coreLening);
		return true;
	}

	@Override
	public boolean updateCoreLening(CoreLening coreLening) {
		myBatisDAO.update(coreLening);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		if(CollectionUtils.isEmpty(list)) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchCoreLeningByIds",hashMap);
		return true;
	}

	@Override
	public CoreLening getCoreLening(CoreLening coreLening) {
		return (CoreLening) myBatisDAO.findForObject(coreLening);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoreLening> findCoreLeningList() {
		return myBatisDAO.findForList("findCoreLeningForLists", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<CoreLening> findCoreLeningPage(CoreLening coreLening, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(coreLening);
		return myBatisDAO.findForPage("findCoreLeningForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

	/**
	 * 学生拥有的教辅权限列表
	 */
	public List<CoreLening> findCoreLeningForListsByUser(String user){
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("user", user);
		return myBatisDAO.findForList("findCoreLeningForListsByUser", hashMap);
	}

	/**
	 * 学生拥有的教辅权限列表
	 */
	public Page<CoreLening> findCoreLeningForListsByUser(String user, int pageNum, int pageSize){
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("user", user);
		return myBatisDAO.findForPage("findCoreLeningForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

}