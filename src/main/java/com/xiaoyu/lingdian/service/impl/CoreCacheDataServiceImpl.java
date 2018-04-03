package com.xiaoyu.lingdian.service.impl;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.service.CoreCacheDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.entity.CoreCacheData;

/**
* 缓存数据表
*/
@Service("coreCacheDataService")
public class CoreCacheDataServiceImpl implements CoreCacheDataService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertCoreCacheData(CoreCacheData coreCacheData) {
		myBatisDAO.insert(coreCacheData);
		return true;
	}

	@Override
	public boolean updateCoreCacheData(CoreCacheData coreCacheData) {
		myBatisDAO.update(coreCacheData);
		return true;
	}

	@Override
	public boolean deleteCoreCacheData(CoreCacheData coreCacheData) {
		myBatisDAO.delete(coreCacheData);
		return true;
	}

	private static final String DELETEBATCH_CORECACHEDATA_BY_IDS="deleteBatchCoreCacheDataByIds";

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete(DELETEBATCH_CORECACHEDATA_BY_IDS,hashMap);
		return true;
	}

	@Override
	public CoreCacheData getCoreCacheData(CoreCacheData coreCacheData) {
		return (CoreCacheData) myBatisDAO.findForObject(coreCacheData);
	}

	private static final String FIND_CORECACHEDATA_FOR_LISTS="findCoreCacheDataForLists";

	@SuppressWarnings("unchecked")
	@Override
	public List<CoreCacheData> findCoreCacheDataList() {
		return myBatisDAO.findForList(FIND_CORECACHEDATA_FOR_LISTS, null);
	}

	private static final String FIND_CORECACHEDATA_FOR_PAGES="findCoreCacheDataForPages";

	@SuppressWarnings("unchecked")
	@Override
	public Page<CoreCacheData> findCoreCacheDataPage(int pageNum, int pageSize) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		return myBatisDAO.findForPage(FIND_CORECACHEDATA_FOR_PAGES, new PageRequest(pageNum, pageSize, hashMap));
	}

//<=================定制内容开始==============
//==================定制内容结束==============>

}