package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreSchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.CoreSchoolService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 学校表
*/
@Service("coreSchoolService")
public class CoreSchoolServiceImpl implements CoreSchoolService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertCoreSchool(CoreSchool coreSchool) {
		myBatisDAO.insert(coreSchool);
		return true;
	}

	@Override
	public boolean updateCoreSchool(CoreSchool coreSchool) {
		myBatisDAO.update(coreSchool);
		return true;
	}

	@Override
	public boolean deleteCoreSchool(CoreSchool coreSchool) {
		myBatisDAO.delete(coreSchool);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchCoreSchoolByIds",hashMap);
		return true;
	}

	@Override
	public CoreSchool getCoreSchool(CoreSchool coreSchool) {
		return (CoreSchool) myBatisDAO.findForObject(coreSchool);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoreSchool> findCoreSchoolList() {
		return myBatisDAO.findForList("findCoreSchoolForLists", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<CoreSchool> findCoreSchoolPage(CoreSchool coreSchool, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(coreSchool);
		return myBatisDAO.findForPage("findCoreSchoolForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

}