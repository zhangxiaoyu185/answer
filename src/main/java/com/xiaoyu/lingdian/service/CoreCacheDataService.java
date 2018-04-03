package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.entity.CoreCacheData;
import java.util.List;
import com.xiaoyu.lingdian.core.mybatis.page.Page;

/**
* 缓存数据表
*/
public interface CoreCacheDataService {

	/**
	* 添加
	* @param coreCacheData
	* @return
	*/
	public boolean insertCoreCacheData(CoreCacheData coreCacheData);

	/**
	* 修改
	* @param coreCacheData
	* @return
	*/
	public boolean updateCoreCacheData(CoreCacheData coreCacheData);

	/**
	* 删除
	* @param coreCacheData
	* @return
	*/
	public boolean deleteCoreCacheData(CoreCacheData coreCacheData);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param coreCacheData
	* @return
	*/
	public CoreCacheData getCoreCacheData(CoreCacheData coreCacheData);

	/**
	* 查询所有List
	* @return List
	*/
	public List<CoreCacheData> findCoreCacheDataList();

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<CoreCacheData> findCoreCacheDataPage(int pageNum, int pageSize);

//<=================定制内容开始==============
//==================定制内容结束==============>

}