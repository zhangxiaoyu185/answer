package com.xiaoyu.lingdian.controller;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.xiaoyu.lingdian.entity.CoreCacheData;
import com.xiaoyu.lingdian.enums.StatusEnum;
import com.xiaoyu.lingdian.service.CoreCacheDataService;
import com.xiaoyu.lingdian.service.CorePapersService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CorePapers;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.ContentList;
import com.xiaoyu.lingdian.vo.CorePapersVO;
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
 * 试卷表
 */
@Controller
@RequestMapping(value = "/corePapers")
public class CorePapersController extends BaseController {

    /**
     * 试卷表
     */
    @Autowired
    private CorePapersService corePapersService;

    /**
     * 缓存数据表
     */
    @Autowired
    private CoreCacheDataService coreCacheDataService;

    /**
     * 添加
     *
     * @param crpesLening 所属教辅
     * @param crpesName   试卷名
     * @param crpesExpire 查看视频过期时间（小时）
     * @return
     */
    @RequestMapping(value = "/add/corePapers", method = RequestMethod.POST)
    public void addCorePapers(String crpesLening, String crpesName, String crpesExpire, HttpServletResponse response) {
        logger.info("[CorePapersController]:begin addCorePapers");
        if (StringUtil.isEmpty(crpesLening)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "所属教辅不能为空!"), response);
            return;
        }
        if (StringUtil.isEmpty(crpesName)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "试卷名不能为空!"), response);
            return;
        }
        if (StringUtil.isEmpty(crpesExpire)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "视频过期时间不能为空!"), response);
            return;
        }
        try{
            Double.parseDouble(crpesExpire);
        }catch (Exception e) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "视频过期时间必须为数字!"), response);
            return;
        }
        Date date = new Date();
        CorePapers corePapers = new CorePapers();
        String uuid = RandomUtil.generateString(16);
        corePapers.setCrpesUuid(uuid);
        corePapers.setCrpesLening(crpesLening);
        corePapers.setCrpesName(crpesName);
        corePapers.setCrpesExpire(crpesExpire);
        corePapers.setCrpesQuestion("");
        corePapers.setCrpesScore(0);
        corePapers.setCrpesCdate(date);
        corePapers.setCrpesUdate(date);
        corePapers.setCrpesStatus(StatusEnum.ENABLE.getCode());
        corePapersService.insertCorePapers(corePapers);

        //保存教辅uuid
        CoreCacheData coreCacheData = new CoreCacheData();
        coreCacheData.setCrcdaUuid("AAAAA");
        coreCacheData.setCrcdaValue(crpesLening);
        coreCacheDataService.updateCoreCacheData(coreCacheData);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[CorePapersController]:end addCorePapers");
    }

    /**
     * 修改
     *
     * @param crpesUuid   标识UUID
     * @param crpesName   试卷名
     * @param crpesExpire 查看视频过期时间（小时）
     * @return
     */
    @RequestMapping(value = "/update/corePapers", method = RequestMethod.POST)
    public void updateCorePapers(String crpesUuid, String crpesName, String crpesExpire, HttpServletResponse response) {
        logger.info("[CorePapersController]:begin updateCorePapers");
        if (StringUtil.isEmpty(crpesUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }
        if (StringUtil.isEmpty(crpesName)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "试卷名不能为空!"), response);
            return;
        }
        if (StringUtil.isEmpty(crpesExpire)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "视频过期时间不能为空!"), response);
            return;
        }
        try{
            Double.parseDouble(crpesExpire);
        }catch (Exception e) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "视频过期时间必须为数字!"), response);
            return;
        }
        Date date = new Date();
        CorePapers corePapers = new CorePapers();
        corePapers.setCrpesUuid(crpesUuid);
        corePapers.setCrpesName(crpesName);
        corePapers.setCrpesExpire(crpesExpire);
        corePapers.setCrpesUdate(date);
        corePapersService.updateCorePapers(corePapers);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[CorePapersController]:end updateCorePapers");
    }

    /**
     * 编辑题目
     *
     * @param crpesUuid   标识UUID
     * @param crpesQuestion 题目内容
     * @return
     */
    @RequestMapping(value = "/update/question", method = RequestMethod.POST)
    public void updateQuestion(String crpesUuid, String crpesQuestion, HttpServletResponse response) {
        logger.info("[CorePapersController]:begin updateQuestion");
        if (StringUtil.isEmpty(crpesUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }
        if (StringUtil.isEmpty(crpesQuestion)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请添加题目!"), response);
            return;
        }
        ContentList contentList;
        List<QuestionsVO> problemList;
        try {
            contentList = JSON.parseObject(crpesQuestion, ContentList.class);
            problemList = contentList.getProblem();
        } catch (Exception e) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "解析题目失败!"),response);
            return;
        }
        if(CollectionUtils.isEmpty(problemList)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请添加题目!"), response);
            return;
        }
        int crpesScore = 0;
        for (QuestionsVO questionsVO : problemList) {
            if(null == questionsVO || StringUtil.isEmpty(questionsVO.getName()) || StringUtil.isEmpty(questionsVO.getResult()) || null == questionsVO.getOldScore()){
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "添加的题目请填写完整!"), response);
                return;
            }
            try{
                int oldScore = Integer.valueOf(questionsVO.getOldScore());
                crpesScore = crpesScore + oldScore;
            }catch (Exception e) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "题目的分数必须为整数!"), response);
                return;
            }
        }
        Date date = new Date();
        CorePapers corePapers = new CorePapers();
        corePapers.setCrpesUuid(crpesUuid);
        corePapers.setCrpesQuestion(crpesQuestion);
        corePapers.setCrpesScore(crpesScore);
        corePapers.setCrpesUdate(date);
        corePapersService.updateCorePapers(corePapers);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "编辑题目成功!"), response);
        logger.info("[CorePapersController]:end updateQuestion");
    }

    /**
     * 删除
     *
     * @param crpesUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteCorePapers(String crpesUuid, HttpServletResponse response) {
        logger.info("[CorePapersController]:begin deleteCorePapers");

        if (StringUtil.isEmpty(crpesUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CorePapers corePapers = new CorePapers();
        corePapers.setCrpesUuid(crpesUuid);
        corePapers.setCrpesStatus(StatusEnum.DISABLE.getCode());
        corePapersService.updateCorePapers(corePapers);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[CorePapersController]:end deleteCorePapers");
    }

    /**
     * 打开
     *
     * @param crpesUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    public void open(String crpesUuid, HttpServletResponse response) {
        logger.info("[CorePapersController]:begin open");

        if (StringUtil.isEmpty(crpesUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CorePapers corePapers = new CorePapers();
        corePapers.setCrpesUuid(crpesUuid);
        corePapers.setCrpesOpen(StatusEnum.ENABLE.getCode());
        corePapersService.updateCorePapers(corePapers);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "打开成功!"), response);
        logger.info("[CorePapersController]:end open");
    }

    /**
     * 关闭
     *
     * @param crpesUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public void close(String crpesUuid, HttpServletResponse response) {
        logger.info("[CorePapersController]:begin close");

        if (StringUtil.isEmpty(crpesUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CorePapers corePapers = new CorePapers();
        corePapers.setCrpesUuid(crpesUuid);
        corePapers.setCrpesOpen(StatusEnum.DISABLE.getCode());
        corePapersService.updateCorePapers(corePapers);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "关闭成功!"), response);
        logger.info("[CorePapersController]:end close");
    }

    /**
     * 批量删除
     *
     * @param crpesUuids UUID集合
     * @return
     */
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchCorePapers(String crpesUuids, HttpServletResponse response) {
        logger.info("[CorePapersController]:begin deleteBatchCorePapers");

        if (StringUtil.isEmpty(crpesUuids)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[UUID集合]不能为空!"), response);
            return;
        }

        String[] uuids = crpesUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        corePapersService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[CorePapersController]:end deleteBatchCorePapers");
    }

    /**
     * 获取单个
     *
     * @param crpesUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/views", method = RequestMethod.POST)
    public void viewsCorePapers(String crpesUuid, HttpServletResponse response) {
        logger.info("[CorePapersController]:begin viewsCorePapers");

        if (StringUtil.isEmpty(crpesUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CorePapers corePapers = new CorePapers();
        corePapers.setCrpesUuid(crpesUuid);

        corePapers = corePapersService.getCorePapers(corePapers);

        CorePapersVO corePapersVO = new CorePapersVO();
        corePapersVO.convertPOToVO(corePapers);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", corePapersVO), response);
        logger.info("[CorePapersController]:end viewsCorePapers");
    }

    /**
     * 获取列表<List>
     *
     * @return
     */
    @RequestMapping(value = "/find/all", method = RequestMethod.POST)
    public void findCorePapersList(String crpesLening, HttpServletResponse response) {
        logger.info("[CorePapersController]:begin findCorePapersList");
        List<CorePapers> lists = corePapersService.findCorePapersList(crpesLening);
        List<CorePapersVO> vos = new ArrayList<CorePapersVO>();
        CorePapersVO vo;
        for (CorePapers corePapers : lists) {
            vo = new CorePapersVO();
            vo.convertPOToVO(corePapers);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[CorePapersController]:end findCorePapersList");
    }

    /**
     * 获取列表<Page>
     *
     * @param corePapers
     * @param pageNum    页码
     * @param pageSize   页数
     * @return
     */
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findCorePapersPage(CorePapers corePapers, Integer pageNum, Integer pageSize, HttpServletResponse response) {
        logger.info("[CorePapersController]:begin findCorePapersPage");

        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CorePapers> page = corePapersService.findCorePapersPage(corePapers, pageNum, pageSize);
        Page<CorePapersVO> pageVO = new Page<CorePapersVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CorePapersVO> vos = new ArrayList<CorePapersVO>();
        CorePapersVO vo;
        for (CorePapers corePapersPO : page.getResult()) {
            vo = new CorePapersVO();
            vo.convertPOToVO(corePapersPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CorePapersController]:end findCorePapersPage");
    }

}