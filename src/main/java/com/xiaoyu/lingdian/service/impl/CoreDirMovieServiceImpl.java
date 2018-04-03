package com.xiaoyu.lingdian.service.impl;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.service.CoreDirMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.entity.CoreDirMovie;

/**
* 视频目录表
*/
@Service("coreDirMovieService")
public class CoreDirMovieServiceImpl implements CoreDirMovieService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertCoreDirMovie(CoreDirMovie coreDirMovie) {
		myBatisDAO.insert(coreDirMovie);
		return true;
	}

	@Override
	public boolean updateCoreDirMovie(CoreDirMovie coreDirMovie) {
		myBatisDAO.update(coreDirMovie);
		return true;
	}

	@Override
	public boolean deleteCoreDirMovie(CoreDirMovie coreDirMovie) {
		myBatisDAO.delete(coreDirMovie);
		return true;
	}

	private static final String DELETEBATCH_COREDIR_BY_IDS="deleteBatchCoreDirMovieByIds";

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete(DELETEBATCH_COREDIR_BY_IDS,hashMap);
		return true;
	}

	@Override
	public CoreDirMovie getCoreDirMovie(CoreDirMovie coreDirMovie) {
		return (CoreDirMovie) myBatisDAO.findForObject(coreDirMovie);
	}

	private static final String FIND_COREDIR_FOR_LISTS="findCoreDirMovieForLists";

	@SuppressWarnings("unchecked")
	@Override
	public List<CoreDirMovie> findCoreDirMovieList() {
		return myBatisDAO.findForList(FIND_COREDIR_FOR_LISTS, null);
	}

	private static final String FIND_COREDIR_FOR_PAGES="findCoreDirMovieForPages";

	@SuppressWarnings("unchecked")
	@Override
	public Page<CoreDirMovie> findCoreDirMoviePage(String crdirName, int pageNum, int pageSize) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("crdirName", crdirName);
		return myBatisDAO.findForPage(FIND_COREDIR_FOR_PAGES, new PageRequest(pageNum, pageSize, hashMap));
	}

	private static final String GET_COREDIR_BY_NAME="getCoreDirMovieByName";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CoreDirMovie> getCoreDirMovieByName(String crdirName) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("crdirName", crdirName);
		return myBatisDAO.findForList(GET_COREDIR_BY_NAME, hashMap);
	}

//<=================定制内容开始==============
//==================定制内容结束==============>

}