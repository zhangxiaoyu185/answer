package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.entity.CoreLening;
import com.xiaoyu.lingdian.entity.CoreLeningUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.service.CoreLeningUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 教辅学生表
*/
@Service("coreLeningUserService")
public class CoreLeningUserServiceImpl implements CoreLeningUserService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertCoreLeningUser(CoreLeningUser coreLeningUser) {
		myBatisDAO.insert(coreLeningUser);
		return true;
	}

	@Override
	public boolean deleteCoreLeningUser(CoreLeningUser coreLeningUser) {
		myBatisDAO.delete(coreLeningUser);
		return true;
	}

	/**
	 * 查询用户是否拥有教辅权限
	 */
	public List<CoreLeningUser> findCoreLeningUserByPaperAndUser(String paper, String user) {
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("paper", paper);
		hashMap.put("user", user);
		return myBatisDAO.findForList("findCoreLeningUserByPaperAndUser", hashMap);
	}

}