package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CorePapersAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.CorePapersAnswerService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 试卷回答表
*/
@Service("corePapersAnswerService")
public class CorePapersAnswerServiceImpl implements CorePapersAnswerService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertCorePapersAnswer(CorePapersAnswer corePapersAnswer) {
		myBatisDAO.insert(corePapersAnswer);
		return true;
	}

	@Override
	public boolean updateCorePapersAnswer(CorePapersAnswer corePapersAnswer) {
		myBatisDAO.update(corePapersAnswer);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		if(CollectionUtils.isEmpty(list)) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchCorePapersAnswerByIds",hashMap);
		return true;
	}

	@Override
	public CorePapersAnswer getCorePapersAnswer(CorePapersAnswer corePapersAnswer) {
		return (CorePapersAnswer) myBatisDAO.findForObject(corePapersAnswer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorePapersAnswer> findCorePapersAnswerList() {
		return myBatisDAO.findForList("findCorePapersAnswerForLists", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorePapersAnswer> findCorePapersAnswerByPaperAndUser(String crpsaPapers, String crpsaUser) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("crpsaPaper", crpsaPapers);
		hashMap.put("crpsaUser", crpsaUser);
		return myBatisDAO.findForList("findCorePapersAnswerByPaperAndUser", hashMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorePapersAnswer> findCorePapersAnswerForListsByFilters(
			List<String> papersList, List<String> userList) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("papersList", papersList);
		hashMap.put("userList", userList);
		return myBatisDAO.findForList("findCorePapersAnswerForListsByFilters", hashMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<CorePapersAnswer> findCorePapersAnswerPage(CorePapersAnswer corePapersAnswer, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(corePapersAnswer);
		return myBatisDAO.findForPage("findCorePapersAnswerForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

}