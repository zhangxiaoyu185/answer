package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.entity.CoreDirMovie;
import java.util.List;
import com.xiaoyu.lingdian.core.mybatis.page.Page;

/**
* 视频目录表
*/
public interface CoreDirMovieService {

	/**
	* 添加
	* @param coreDirMovie
	* @return
	*/
	public boolean insertCoreDirMovie(CoreDirMovie coreDirMovie);

	/**
	* 修改
	* @param coreDirMovie
	* @return
	*/
	public boolean updateCoreDirMovie(CoreDirMovie coreDirMovie);

	/**
	* 删除
	* @param coreDirMovie
	* @return
	*/
	public boolean deleteCoreDirMovie(CoreDirMovie coreDirMovie);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param coreDirMovie
	* @return
	*/
	public CoreDirMovie getCoreDirMovie(CoreDirMovie coreDirMovie);

	/**
	* 查询所有List
	* @return List
	*/
	public List<CoreDirMovie> findCoreDirMovieList();

	/**
	* 根据文件夹名查询List
	* 
	* @param
	* @return List
	*/
	public List<CoreDirMovie> getCoreDirMovieByName(String crdirName);
	
	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<CoreDirMovie> findCoreDirMoviePage(String crdirName, int pageNum, int pageSize);

//<=================定制内容开始==============
//==================定制内容结束==============>

}