package com.xiaoyu.lingdian.controller;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.xiaoyu.lingdian.entity.CorePapers;
import com.xiaoyu.lingdian.enums.StatusEnum;
import com.xiaoyu.lingdian.service.CorePapersAnswerService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CorePapersAnswer;
import com.xiaoyu.lingdian.service.CorePapersService;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.ContentList;
import com.xiaoyu.lingdian.vo.CorePapersAnswerVO;
import com.xiaoyu.lingdian.vo.QuestionsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 试卷回答表
 */
@Controller
@RequestMapping(value = "/corePapersAnswer")
public class CorePapersAnswerController extends BaseController {

    /**
     * 试卷回答表
     */
    @Autowired
    private CorePapersAnswerService corePapersAnswerService;

    /**
     * 试卷表
     */
    @Autowired
    private CorePapersService corePapersService;

    /**
     * 添加
     *
     * @param crpsaLening     所属教辅
     * @param crpsaPaper      所属试卷
     * @param crpsaUser       所属用户
     * @param questionsAnswerContent 题目回答集合
     * @return
     */
    @RequestMapping(value = "/add/corePapersAnswer", method = RequestMethod.POST)
    public void addCorePapersAnswer(String crpsaLening, String crpsaPaper, String crpsaUser, String questionsAnswerContent, HttpServletResponse response) {
        logger.info("[CorePapersAnswerController]:begin addCorePapersAnswer");
        if (StringUtil.isEmpty(crpsaLening)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "所属教辅不能为空!"), response);
            return;
        }
        if (StringUtil.isEmpty(crpsaPaper)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "所属试卷不能为空!"), response);
            return;
        }
        if (StringUtil.isEmpty(crpsaUser)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "所属用户不能为空!"), response);
            return;
        }
        if(StringUtil.isEmpty(questionsAnswerContent)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "不能提交空白卷!"), response);
            return;
        }

        List<CorePapersAnswer> answerList = this.corePapersAnswerService.findCorePapersAnswerByPaperAndUser(crpsaPaper, crpsaUser);
        if(!CollectionUtils.isEmpty(answerList) && null != answerList.get(0)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "已答过题，查看答题详情!"),response);
            return;
        }
        ContentList contentList;
        List<QuestionsVO> problemList;
        try {
            contentList = JSON.parseObject(questionsAnswerContent, ContentList.class);
            problemList = contentList.getProblem();
        } catch (Exception e) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "解析题目失败!"),response);
            return;
        }

        CorePapers corePapers = new CorePapers();
        corePapers.setCrpesUuid(crpsaPaper);
        corePapers = corePapersService.getCorePapers(corePapers);
        if(null == corePapers) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "该份试卷不存在!"),response);
            return;
        }
        int crpsaScore = 0; //总得分
        for (QuestionsVO questionsVO : problemList) {
            String strAnswer = questionsVO.getContent()==null?"":questionsVO.getContent();
            String strResult = questionsVO.getResult()==null?"":questionsVO.getResult();
            strAnswer = strAnswer.replace("，", ",").replace(" ", "");
            strResult = strResult.replace("，", ",").replace(" ", "");
            questionsVO.setContent(strAnswer);
            questionsVO.setResult(strResult);
            if (strAnswer.equals(strResult)) { //正确
                crpsaScore = crpsaScore + questionsVO.getOldScore();
                questionsVO.setScore(questionsVO.getOldScore());
                questionsVO.setJudge(1);
            } else { //错误
                questionsVO.setScore(0);
                questionsVO.setJudge(2);
            }
        }
        contentList.setProblem(problemList);
        String crpsaQuestion = JSON.toJSONString(contentList);
        Date date = new Date();
        CorePapersAnswer corePapersAnswer = new CorePapersAnswer();
        String uuid = RandomUtil.generateString(16);
        corePapersAnswer.setCrpsaUuid(uuid);
        corePapersAnswer.setCrpsaLening(crpsaLening);
        corePapersAnswer.setCrpsaPaper(crpsaPaper);
        corePapersAnswer.setCrpsaUser(crpsaUser);
        corePapersAnswer.setCrpsaPaperScore(corePapers.getCrpesScore());
        corePapersAnswer.setCrpsaScore(crpsaScore); //总得分
        corePapersAnswer.setCrpsaQuestion(crpsaQuestion);
        corePapersAnswer.setCrpsaExpire(corePapers.getCrpesExpire());
        corePapersAnswer.setCrpsaStatus(StatusEnum.ENABLE.getCode());
        corePapersAnswer.setCrpsaCdate(date);
        corePapersAnswer.setCrpsaUdate(date);

        corePapersAnswerService.insertCorePapersAnswer(corePapersAnswer);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "提交成功!"), response);
        logger.info("[CorePapersAnswerController]:end addCorePapersAnswer");
    }

    /**
     * 删除
     *
     * @param crpsaUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteCorePapersAnswer(String crpsaUuid, HttpServletResponse response) {
        logger.info("[CorePapersAnswerController]:begin deleteCorePapersAnswer");

        if (StringUtil.isEmpty(crpsaUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CorePapersAnswer corePapersAnswer = new CorePapersAnswer();
        corePapersAnswer.setCrpsaUuid(crpsaUuid);
        corePapersAnswer.setCrpsaStatus(StatusEnum.DISABLE.getCode());
        corePapersAnswerService.updateCorePapersAnswer(corePapersAnswer);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[CorePapersAnswerController]:end deleteCorePapersAnswer");
    }

    /**
     * 批量删除
     *
     * @param crpsaUuids UUID集合
     * @return
     */
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchCorePapersAnswer(String crpsaUuids, HttpServletResponse response) {
        logger.info("[CorePapersAnswerController]:begin deleteBatchCorePapersAnswer");

        if (StringUtil.isEmpty(crpsaUuids)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[UUID集合]不能为空!"), response);
            return;
        }

        String[] uuids = crpsaUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        corePapersAnswerService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[CorePapersAnswerController]:end deleteBatchCorePapersAnswer");
    }

    /**
     * 获取单个
     *
     * @param crpsaUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/views", method = RequestMethod.POST)
    public void viewsCorePapersAnswer(String crpsaUuid, HttpServletResponse response) {
        logger.info("[CorePapersAnswerController]:begin viewsCorePapersAnswer");

        if (StringUtil.isEmpty(crpsaUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CorePapersAnswer corePapersAnswer = new CorePapersAnswer();
        corePapersAnswer.setCrpsaUuid(crpsaUuid);

        corePapersAnswer = corePapersAnswerService.getCorePapersAnswer(corePapersAnswer);

        CorePapersAnswerVO corePapersAnswerVO = new CorePapersAnswerVO();
        corePapersAnswerVO.convertPOToVO(corePapersAnswer);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", corePapersAnswerVO), response);
        logger.info("[CorePapersAnswerController]:end viewsCorePapersAnswer");
    }

    /**
     * 根据试卷和用户标识获取答题情况<List>
     *
     * @return
     */
    @RequestMapping(value = "/view/by/paperuser", method = RequestMethod.POST)
    public void viewByPaperuser(String paper, String user, HttpServletResponse response) {
        logger.info("[CorePapersAnswerController]:begin viewByPaperuser");
        if (StringUtil.isEmpty(paper)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "试卷不能为空!"), response);
            return;
        }
        if (StringUtil.isEmpty(user)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "用户标识不能为空!"), response);
            return;
        }
        List<CorePapersAnswer> lists = corePapersAnswerService.findCorePapersAnswerByPaperAndUser(paper, user);
        if(CollectionUtils.isEmpty(lists) || null == lists.get(0)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "没有答题记录!"), response);
            return;
        }
        CorePapersAnswerVO vo = new CorePapersAnswerVO();
        vo.convertPOToVO(lists.get(0));

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vo), response);
        logger.info("[CorePapersAnswerController]:end viewByPaperuser");
    }

    /**
     * 获取列表<List>
     *
     * @return
     */
    @RequestMapping(value = "/find/all", method = RequestMethod.POST)
    public void findCorePapersAnswerList(HttpServletResponse response) {
        logger.info("[CorePapersAnswerController]:begin findCorePapersAnswerList");

        List<CorePapersAnswer> lists = corePapersAnswerService.findCorePapersAnswerList();
        List<CorePapersAnswerVO> vos = new ArrayList<CorePapersAnswerVO>();
        CorePapersAnswerVO vo;
        for (CorePapersAnswer corePapersAnswer : lists) {
            vo = new CorePapersAnswerVO();
            vo.convertPOToVO(corePapersAnswer);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[CorePapersAnswerController]:end findCorePapersAnswerList");
    }

    /**
     * 获取列表<Page>
     *
     * @param corePapersAnswer
     * @param pageNum          页码
     * @param pageSize         页数
     * @return
     */
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findCorePapersAnswerPage(CorePapersAnswer corePapersAnswer, Integer pageNum, Integer pageSize, HttpServletResponse response) {
        logger.info("[CorePapersAnswerController]:begin findCorePapersAnswerPage");

        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CorePapersAnswer> page = corePapersAnswerService.findCorePapersAnswerPage(corePapersAnswer, pageNum, pageSize);
        Page<CorePapersAnswerVO> pageVO = new Page<CorePapersAnswerVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CorePapersAnswerVO> vos = new ArrayList<CorePapersAnswerVO>();
        CorePapersAnswerVO vo;
        for (CorePapersAnswer corePapersAnswerPO : page.getResult()) {
            vo = new CorePapersAnswerVO();
            vo.convertPOToVO(corePapersAnswerPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CorePapersAnswerController]:end findCorePapersAnswerPage");
    }

}