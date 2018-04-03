package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.CoreClassService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 班级表
*/
@Service("coreClassService")
public class CoreClassServiceImpl implements CoreClassService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertCoreClass(CoreClass coreClass) {
		myBatisDAO.insert(coreClass);
		return true;
	}

	@Override
	public boolean updateCoreClass(CoreClass coreClass) {
		myBatisDAO.update(coreClass);
		return true;
	}

	@Override
	public boolean deleteCoreClass(CoreClass coreClass) {
		myBatisDAO.delete(coreClass);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchCoreClassByIds",hashMap);
		return true;
	}

	@Override
	public CoreClass getCoreClass(CoreClass coreClass) {
		return (CoreClass) myBatisDAO.findForObject(coreClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoreClass> findCoreClassList(String crcasSchool) {
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("crcasSchool", crcasSchool);
		return myBatisDAO.findForList("findCoreClassForLists", hashMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<CoreClass> findCoreClassPage(CoreClass coreClass, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(coreClass);
		return myBatisDAO.findForPage("findCoreClassForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

}