package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CorePapers;

import java.util.List;

/**
* 试卷表
*/
public interface CorePapersService {

	/**
	* 添加
	* @param corePapers
	* @return
	*/
	public boolean insertCorePapers(CorePapers corePapers);

	/**
	* 修改
	* @param corePapers
	* @return
	*/
	public boolean updateCorePapers(CorePapers corePapers);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param corePapers
	* @return
	*/
	public CorePapers getCorePapers(CorePapers corePapers);

	/**
	* 查询所有List
	* @return List
	*/
	public List<CorePapers> findCorePapersList(String crpesLening);

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<CorePapers> findCorePapersPage(CorePapers corePapers, int pageNum, int pageSize);

}