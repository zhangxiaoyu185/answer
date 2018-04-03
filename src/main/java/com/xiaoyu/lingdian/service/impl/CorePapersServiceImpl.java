package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CorePapers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.CorePapersService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 试卷表
*/
@Service("corePapersService")
public class CorePapersServiceImpl implements CorePapersService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertCorePapers(CorePapers corePapers) {
		myBatisDAO.insert(corePapers);
		return true;
	}

	@Override
	public boolean updateCorePapers(CorePapers corePapers) {
		myBatisDAO.update(corePapers);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		if(CollectionUtils.isEmpty(list)) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchCorePapersByIds",hashMap);
		return true;
	}

	@Override
	public CorePapers getCorePapers(CorePapers corePapers) {
		return (CorePapers) myBatisDAO.findForObject(corePapers);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorePapers> findCorePapersList(String crpesLening) {
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("crpesLening", crpesLening);
		return myBatisDAO.findForList("findCorePapersForLists", hashMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<CorePapers> findCorePapersPage(CorePapers corePapers, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(corePapers);
		return myBatisDAO.findForPage("findCorePapersForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

}