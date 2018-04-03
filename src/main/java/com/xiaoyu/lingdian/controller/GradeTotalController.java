package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.entity.*;
import com.xiaoyu.lingdian.service.*;
import com.xiaoyu.lingdian.tool.DateUtil;
import com.xiaoyu.lingdian.tool.ExcelUtils;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.GradeTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value="/gradeTotal")
public class GradeTotalController extends BaseController {
	
	/**
	 * 试卷表
	 */
	@Autowired
	private CorePapersService corePapersService;
	
	/**
	* 试卷回答表
	*/
	@Autowired
	private CorePapersAnswerService corePapersAnswerService;
	
	/**
	* 用户表
	*/
	@Autowired
	public CoreUserService coreUserService;

	/**
	 * 教辅表
	 */
	@Autowired
	private CoreLeningService coreLeningService;

	/**
	* 试卷学生
	* 
	* @return
	*/
	@RequestMapping(value="/papers/user", method=RequestMethod.POST)
	public void papersUser (String strLening, HttpServletResponse response) {
		logger.info("[GradeTotalController]:begin papersUser");
		List<GradeTotal> totalList = new ArrayList<GradeTotal>();
		if(StringUtil.isEmpty(strLening)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "", totalList), response);
			logger.info("[GradeTotalController]:end papersUser");
		}
		//1.查询教辅下的所有学生集合，userList->userIdList
		List<String> userIdList = new ArrayList<String>();
		List<CoreUser> userList = coreUserService.findCoreUserForListsByLening(strLening);
		for (CoreUser coreUser : userList) {
			userIdList.add(coreUser.getCrusrUuid());
		}
		//2.查询教辅下的所有试卷集合，papersList->papersIdList
		List<String> papersIdList = new ArrayList<String>();
		List<CorePapers> papersList = corePapersService.findCorePapersList(strLening);
		for (CorePapers corePapers : papersList) {
			papersIdList.add(corePapers.getCrpesUuid());
		}
		if (userIdList.size() <= 0 || papersIdList.size() <= 0) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "该教辅下没有学生或没有试卷!", totalList), response);
			logger.info("[GradeTotalController]:end papersUser");
		}
		//3.根据papersIdList和userIdList查询试卷answer集合,answerList->Map<key->userUuid+";"+papersUuid>
		Map<String, CorePapersAnswer> map = new HashMap<String, CorePapersAnswer>();
		List<CorePapersAnswer> answerList = corePapersAnswerService.findCorePapersAnswerForListsByFilters(papersIdList, userIdList);
		for (CorePapersAnswer corePapersAnswer : answerList) {
			map.put(corePapersAnswer.getCrpsaUser()+";"+corePapersAnswer.getCrpsaPaper(), corePapersAnswer);
		}
		//试卷学生统计，包装对象
		GradeTotal gradeTotal = null;
		for (CorePapers corePapers : papersList) {
			gradeTotal = new GradeTotal();
			int hwzKey = 0; //行未做Key
			StringBuffer hwzValue = new StringBuffer(); //行未做Value
			int hyzwmfKey = 0; //行已做未满分Key
			StringBuffer hyzwmfValue = new StringBuffer(); //行已做未满分Value
			int hyzmfKey = 0; //行已做满分Key
			StringBuffer hyzmfValue = new StringBuffer();; //行已做满分Value
			gradeTotal.setColumnObj(corePapers.getCrpesName());
			for (CoreUser coreUser : userList) {
				CorePapersAnswer corePapersAnswer = map.get(coreUser.getCrusrUuid() + ";" + corePapers.getCrpesUuid());
				if (corePapersAnswer != null) { //已做
					if (corePapersAnswer.getCrpsaScore().equals(corePapers.getCrpesScore())) { //满分
						hyzmfKey++;
						String code = coreUser.getCrusrCode() + "(" + corePapersAnswer.getCrpsaScore() + "分)";
						hyzmfValue.append(code).append(",");
					} else { //未满分
						hyzwmfKey++;
						String code = coreUser.getCrusrCode() + "(" + corePapersAnswer.getCrpsaScore() + "分)";
						hyzwmfValue.append(code).append(",");
					}					
				} else { //未做
					hwzKey++;
					String code = coreUser.getCrusrCode();
					hwzValue.append(code).append(",");
				}
			}
			gradeTotal.setHwzKey(String.valueOf(hwzKey)+"人");
			gradeTotal.setHwzValue(hwzValue.toString());
			gradeTotal.setHyzmfKey(String.valueOf(hyzmfKey)+"人");
			gradeTotal.setHyzmfValue(hyzmfValue.toString());
			gradeTotal.setHyzwmfKey(String.valueOf(hyzwmfKey)+"人");
			gradeTotal.setHyzwmfValue(hyzwmfValue.toString());
			totalList.add(gradeTotal);
		}
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "统计获取成功!", totalList),response);
		logger.info("[GradeTotalController]:end papersUser");
	}

	/**
	* 学生试卷
	* 
	* @return
	*/
	@RequestMapping(value="/user/papers", method=RequestMethod.POST)
	public void userPapers (String strLening, HttpServletResponse response) {
		logger.info("[GradeTotalController]:begin userPapers");
		List<GradeTotal> totalList = new ArrayList<GradeTotal>();
		if(StringUtil.isEmpty(strLening)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "", totalList), response);
			logger.info("[GradeTotalController]:end papersUser");
		}
		//1.查询教辅下的所有学生集合，userList->userIdList
		List<String> userIdList = new ArrayList<String>();
		List<CoreUser> userList = coreUserService.findCoreUserForListsByLening(strLening);
		for (CoreUser coreUser : userList) {
			userIdList.add(coreUser.getCrusrUuid());
		}
		//2.查询教辅下的所有模拟试卷集合，papersList->papersIdList
		List<String> papersIdList = new ArrayList<String>();
		List<CorePapers> papersList = corePapersService.findCorePapersList(strLening);
		for (CorePapers corePapers : papersList) {
			papersIdList.add(corePapers.getCrpesUuid());
		}
		if (userIdList.size() <= 0 || papersIdList.size() <= 0) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "该教辅下没有学生或没有试卷!", totalList), response);
			logger.info("[GradeTotalController]:end papersUser");
		}
		//3.根据papersIdList和userIdList查询试卷answer集合,answerList->Map<key->userUuid+";"+papersUuid>
		Map<String, CorePapersAnswer> map = new HashMap<String, CorePapersAnswer>();
		List<CorePapersAnswer> answerList = corePapersAnswerService.findCorePapersAnswerForListsByFilters(papersIdList, userIdList);
		for (CorePapersAnswer corePapersAnswer : answerList) {
			map.put(corePapersAnswer.getCrpsaUser()+";"+corePapersAnswer.getCrpsaPaper(), corePapersAnswer);
		}
		//学生试卷统计，包装对象
		GradeTotal gradeTotal = null;
		for (CoreUser coreUser : userList) {
			gradeTotal = new GradeTotal();
			int hwzKey = 0; //行未做Key
			StringBuffer hwzValue = new StringBuffer(); //行未做Value
			int hyzwmfKey = 0; //行已做未满分Key
			StringBuffer hyzwmfValue = new StringBuffer(); //行已做未满分Value
			int hyzmfKey = 0; //行已做满分Key
			StringBuffer hyzmfValue = new StringBuffer();; //行已做满分Value
			String code = coreUser.getCrusrCode()+"("+coreUser.getCrusrCode()+")";
			gradeTotal.setColumnObj(code);
			int totalScore = 0; //总分
			for (CorePapers corePapers : papersList) {
				CorePapersAnswer corePapersAnswer = map.get(coreUser.getCrusrUuid() + ";" + corePapers.getCrpesUuid());
				if (corePapersAnswer != null) { //已做
					if (corePapersAnswer.getCrpsaScore().equals(corePapers.getCrpesScore())) { //满分
						hyzmfKey++;
						hyzmfValue.append(corePapers.getCrpesName()).append("(").append(corePapersAnswer.getCrpsaScore()).append("分)").append(",");
					} else { //未满分
						hyzwmfKey++;
						hyzwmfValue.append(corePapers.getCrpesName()).append("(").append(corePapersAnswer.getCrpsaScore()).append("分)").append(",");
					}
					totalScore += corePapersAnswer.getCrpsaScore();
				} else { //未做
					hwzKey++;
					hwzValue.append(corePapers.getCrpesName()).append(",");
				}
			}
			gradeTotal.setHwzKey(String.valueOf(hwzKey)+"份");
			gradeTotal.setHwzValue(hwzValue.toString());
			gradeTotal.setHyzmfKey(String.valueOf(hyzmfKey)+"份");
			gradeTotal.setHyzmfValue(hyzmfValue.toString());
			gradeTotal.setHyzwmfKey(String.valueOf(hyzwmfKey)+"份");
			gradeTotal.setHyzwmfValue(hyzwmfValue.toString());
			gradeTotal.setTotalKey(String.valueOf(totalScore));
			totalList.add(gradeTotal);
		}
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "统计获取成功!", totalList),response);
		logger.info("[GradeTotalController]:end userPapers");
	}

	/**
	 * 后台导出试卷学生
	 *
	 * @return
	 */
	@RequestMapping(value = "/excel/export/papersuser", method = RequestMethod.GET)
	public void exportExcelPapersuser(String strLening, HttpServletResponse response) throws IOException {
		logger.info("[GradeTotalController]:begin exportExcelPapersuser");
		String strLeningName = "";
		if(!StringUtil.isEmpty(strLening)) {
			CoreLening coreLening = new CoreLening();
			coreLening.setCrlngUuid(strLening);
			coreLening = coreLeningService.getCoreLening(coreLening);
			if(null != coreLening) {
				strLeningName = coreLening.getCrlngName();
			}
		}
		final Map<String, String> linkedHeader = new LinkedHashMap<>();
		linkedHeader.put("columnObj", "试卷名称");
		linkedHeader.put("hwzKey", "未做人数");
		linkedHeader.put("hyzwmfKey", "已做未满分人数");
		linkedHeader.put("hyzmfKey", "已做满分人数");

		List<GradeTotal> list = getPapersUserList(strLening);
		List<String[]> contents = ExcelUtils.buildExcelList(list, new ExcelUtils.ExcelMapper<GradeTotal>() {

			@Override
			public String[] mapHeader() {
				return toExcelHeader(linkedHeader);
			}

			@Override
			public String[] mapRowData(GradeTotal obj) {
				return toExcelLine(obj, linkedHeader);
			}
		});
		ExcelUtils.writeXls(response, contents, strLeningName+"_试卷学生_list");
		logger.info("[GradeTotalController]:end exportExcelPapersuser");
	}

	/**
	 * 后台导出学生试卷
	 *
	 * @return
	 */
	@RequestMapping(value = "/excel/export/userpapers", method = RequestMethod.GET)
	public void exportExcelUserpapers(String strLening, HttpServletResponse response) throws IOException {
		logger.info("[GradeTotalController]:begin exportExcelUserpapers");
		String strLeningName = "";
		if(!StringUtil.isEmpty(strLening)) {
			CoreLening coreLening = new CoreLening();
			coreLening.setCrlngUuid(strLening);
			coreLening = coreLeningService.getCoreLening(coreLening);
			if(null != coreLening) {
				strLeningName = coreLening.getCrlngName();
			}
		}
		final Map<String, String> linkedHeader = new LinkedHashMap<>();
		linkedHeader.put("columnObj", "学生姓名");
		linkedHeader.put("hwzKey", "未做数量");
		linkedHeader.put("hyzwmfKey", "已做未满分数量");
		linkedHeader.put("hyzmfKey", "已做满分数量");
		linkedHeader.put("totalKey", "总分");

		List<GradeTotal> list = getUserPapersList(strLening);
		List<String[]> contents = ExcelUtils.buildExcelList(list, new ExcelUtils.ExcelMapper<GradeTotal>() {

			@Override
			public String[] mapHeader() {
				return toExcelHeader(linkedHeader);
			}

			@Override
			public String[] mapRowData(GradeTotal obj) {
				return toExcelLine(obj, linkedHeader);
			}
		});
		ExcelUtils.writeXls(response, contents, strLeningName+"_学生试卷_list");
		logger.info("[GradeTotalController]:end exportExcelUserpapers");
	}

	private String[] toExcelLine(GradeTotal obj, Map<String, String> linkedHeader) {
		List<String> list = new ArrayList<>();
		for (String key : linkedHeader.keySet()) {
			if(key.equals("columnObj")) {
				list.add(obj.getColumnObj());
			}
			if(key.equals("hwzKey")) {
				list.add(obj.getHwzKey()+"["+obj.getHwzValue()+"]");
			}
			if(key.equals("hyzwmfKey")) {
				list.add(obj.getHyzwmfKey()+"["+obj.getHyzwmfValue()+"]");
			}
			if(key.equals("hyzmfKey")) {
				list.add(obj.getHyzmfKey()+"["+obj.getHyzmfValue()+"]");
			}
			if(key.equals("totalKey")) {
				list.add(obj.getTotalKey());
			}
		}
		return list.toArray(new String[list.size()]);
	}

	private String[] toExcelHeader(Map<String, String> linkedHeader) {
		Collection<String> titles = linkedHeader.values();
		return new ArrayList<>(titles).toArray(new String[titles.size()]);
	}

	public List<GradeTotal> getPapersUserList(String strLening) {
		List<GradeTotal> totalList = new ArrayList<GradeTotal>();
		if(StringUtil.isEmpty(strLening)) {
			return totalList;
		}
		//1.查询教辅下的所有学生集合，userList->userIdList
		List<String> userIdList = new ArrayList<String>();
		List<CoreUser> userList = coreUserService.findCoreUserForListsByLening(strLening);
		for (CoreUser coreUser : userList) {
			userIdList.add(coreUser.getCrusrUuid());
		}
		//2.查询教辅下的所有试卷集合，papersList->papersIdList
		List<String> papersIdList = new ArrayList<String>();
		List<CorePapers> papersList = corePapersService.findCorePapersList(strLening);
		for (CorePapers corePapers : papersList) {
			papersIdList.add(corePapers.getCrpesUuid());
		}
		if (userIdList.size() <= 0 || papersIdList.size() <= 0) {
			return totalList;
		}
		//3.根据papersIdList和userIdList查询试卷answer集合,answerList->Map<key->userUuid+";"+papersUuid>
		Map<String, CorePapersAnswer> map = new HashMap<String, CorePapersAnswer>();
		List<CorePapersAnswer> answerList = corePapersAnswerService.findCorePapersAnswerForListsByFilters(papersIdList, userIdList);
		for (CorePapersAnswer corePapersAnswer : answerList) {
			map.put(corePapersAnswer.getCrpsaUser()+";"+corePapersAnswer.getCrpsaPaper(), corePapersAnswer);
		}
		//试卷学生统计，包装对象
		GradeTotal gradeTotal = null;
		for (CorePapers corePapers : papersList) {
			gradeTotal = new GradeTotal();
			int hwzKey = 0; //行未做Key
			StringBuffer hwzValue = new StringBuffer(); //行未做Value
			int hyzwmfKey = 0; //行已做未满分Key
			StringBuffer hyzwmfValue = new StringBuffer(); //行已做未满分Value
			int hyzmfKey = 0; //行已做满分Key
			StringBuffer hyzmfValue = new StringBuffer();; //行已做满分Value
			gradeTotal.setColumnObj(corePapers.getCrpesName());
			for (CoreUser coreUser : userList) {
				CorePapersAnswer corePapersAnswer = map.get(coreUser.getCrusrUuid() + ";" + corePapers.getCrpesUuid());
				if (corePapersAnswer != null) { //已做
					if (corePapersAnswer.getCrpsaScore().equals(corePapers.getCrpesScore())) { //满分
						hyzmfKey++;
						String code = coreUser.getCrusrCode() + "(" + corePapersAnswer.getCrpsaScore() + "分)";
						hyzmfValue.append(code).append(",");
					} else { //未满分
						hyzwmfKey++;
						String code = coreUser.getCrusrCode() + "(" + corePapersAnswer.getCrpsaScore() + "分)";
						hyzwmfValue.append(code).append(",");
					}
				} else { //未做
					hwzKey++;
					String code = coreUser.getCrusrCode();
					hwzValue.append(code).append(",");
				}
			}
			gradeTotal.setHwzKey(String.valueOf(hwzKey)+"人");
			gradeTotal.setHwzValue(hwzValue.toString());
			gradeTotal.setHyzmfKey(String.valueOf(hyzmfKey)+"人");
			gradeTotal.setHyzmfValue(hyzmfValue.toString());
			gradeTotal.setHyzwmfKey(String.valueOf(hyzwmfKey)+"人");
			gradeTotal.setHyzwmfValue(hyzwmfValue.toString());
			totalList.add(gradeTotal);
		}
		return totalList;
	}

	public List<GradeTotal> getUserPapersList(String strLening) {
		List<GradeTotal> totalList = new ArrayList<GradeTotal>();
		if(StringUtil.isEmpty(strLening)) {
			return totalList;
		}
		//1.查询教辅下的所有学生集合，userList->userIdList
		List<String> userIdList = new ArrayList<String>();
		List<CoreUser> userList = coreUserService.findCoreUserForListsByLening(strLening);
		for (CoreUser coreUser : userList) {
			userIdList.add(coreUser.getCrusrUuid());
		}
		//2.查询教辅下的所有模拟试卷集合，papersList->papersIdList
		List<String> papersIdList = new ArrayList<String>();
		List<CorePapers> papersList = corePapersService.findCorePapersList(strLening);
		for (CorePapers corePapers : papersList) {
			papersIdList.add(corePapers.getCrpesUuid());
		}
		if (userIdList.size() <= 0 || papersIdList.size() <= 0) {
			return totalList;
		}
		//3.根据papersIdList和userIdList查询试卷answer集合,answerList->Map<key->userUuid+";"+papersUuid>
		Map<String, CorePapersAnswer> map = new HashMap<String, CorePapersAnswer>();
		List<CorePapersAnswer> answerList = corePapersAnswerService.findCorePapersAnswerForListsByFilters(papersIdList, userIdList);
		for (CorePapersAnswer corePapersAnswer : answerList) {
			map.put(corePapersAnswer.getCrpsaUser()+";"+corePapersAnswer.getCrpsaPaper(), corePapersAnswer);
		}
		//学生试卷统计，包装对象
		GradeTotal gradeTotal = null;
		for (CoreUser coreUser : userList) {
			gradeTotal = new GradeTotal();
			int hwzKey = 0; //行未做Key
			StringBuffer hwzValue = new StringBuffer(); //行未做Value
			int hyzwmfKey = 0; //行已做未满分Key
			StringBuffer hyzwmfValue = new StringBuffer(); //行已做未满分Value
			int hyzmfKey = 0; //行已做满分Key
			StringBuffer hyzmfValue = new StringBuffer();; //行已做满分Value
			String code = coreUser.getCrusrCode()+"("+coreUser.getCrusrCode()+")";
			gradeTotal.setColumnObj(code);
			int totalScore = 0; //总分
			for (CorePapers corePapers : papersList) {
				CorePapersAnswer corePapersAnswer = map.get(coreUser.getCrusrUuid() + ";" + corePapers.getCrpesUuid());
				if (corePapersAnswer != null) { //已做
					if (corePapersAnswer.getCrpsaScore().equals(corePapers.getCrpesScore())) { //满分
						hyzmfKey++;
						hyzmfValue.append(corePapers.getCrpesName()).append("(").append(corePapersAnswer.getCrpsaScore()).append("分)").append(",");
					} else { //未满分
						hyzwmfKey++;
						hyzwmfValue.append(corePapers.getCrpesName()).append("(").append(corePapersAnswer.getCrpsaScore()).append("分)").append(",");
					}
					totalScore += corePapersAnswer.getCrpsaScore();
				} else { //未做
					hwzKey++;
					hwzValue.append(corePapers.getCrpesName()).append(",");
				}
			}
			gradeTotal.setHwzKey(String.valueOf(hwzKey)+"份");
			gradeTotal.setHwzValue(hwzValue.toString());
			gradeTotal.setHyzmfKey(String.valueOf(hyzmfKey)+"份");
			gradeTotal.setHyzmfValue(hyzmfValue.toString());
			gradeTotal.setHyzwmfKey(String.valueOf(hyzwmfKey)+"份");
			gradeTotal.setHyzwmfValue(hyzwmfValue.toString());
			gradeTotal.setTotalKey(String.valueOf(totalScore));
			totalList.add(gradeTotal);
		}
		return totalList;
	}
}