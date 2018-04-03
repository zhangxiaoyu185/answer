package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CorePapersAnswer;

import java.util.List;

/**
* 试卷回答表
*/
public interface CorePapersAnswerService {

	/**
	* 添加
	* @param corePapersAnswer
	* @return
	*/
	public boolean insertCorePapersAnswer(CorePapersAnswer corePapersAnswer);

	/**
	* 修改
	* @param corePapersAnswer
	* @return
	*/
	public boolean updateCorePapersAnswer(CorePapersAnswer corePapersAnswer);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param corePapersAnswer
	* @return
	*/
	public CorePapersAnswer getCorePapersAnswer(CorePapersAnswer corePapersAnswer);

	/**
	* 查询所有List
	* @return List
	*/
	public List<CorePapersAnswer> findCorePapersAnswerList();

	/**
	 * 查询所有List
	 *
	 * @param crpsaPapers
	 * @param crpsaUser
	 * @return List
	 */
	public List<CorePapersAnswer> findCorePapersAnswerByPaperAndUser(String crpsaPapers, String crpsaUser);

	/**
	 * 查询所有List
	 *
	 * @param papersList
	 * @param userList
	 * @return List
	 */
	public List<CorePapersAnswer> findCorePapersAnswerForListsByFilters(List<String> papersList, List<String> userList);

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<CorePapersAnswer> findCorePapersAnswerPage(CorePapersAnswer corePapersAnswer, int pageNum, int pageSize);

}