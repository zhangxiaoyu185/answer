package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.enums.StatusEnum;
import com.xiaoyu.lingdian.service.CoreLeningService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreLening;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreLeningVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 教辅表
 */
@Controller
@RequestMapping(value = "/coreLening")
public class CoreLeningController extends BaseController {

    /**
     * 教辅表
     */
    @Autowired
    private CoreLeningService coreLeningService;

    /**
     * 添加
     *
     * @param crlngName 教辅名称
     * @return
     */
    @RequestMapping(value = "/add/coreLening", method = RequestMethod.POST)
    public void addCoreLening(String crlngName, HttpServletResponse response) {
        logger.info("[CoreLeningController]:begin addCoreLening");
        if (StringUtil.isEmpty(crlngName)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "教辅名称不能为空!"), response);
            return;
        }

        CoreLening coreLening = new CoreLening();
        String uuid = RandomUtil.generateString(16);
        coreLening.setCrlngUuid(uuid);
        coreLening.setCrlngName(crlngName);
        coreLening.setCrlngStatus(StatusEnum.ENABLE.getCode());

        coreLeningService.insertCoreLening(coreLening);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[CoreLeningController]:end addCoreLening");
    }

    /**
     * 修改
     *
     * @param crlngUuid   标识UUID
     * @param crlngName   教辅名称
     * @return
     */
    @RequestMapping(value = "/update/coreLening", method = RequestMethod.POST)
    public void updateCoreLening(String crlngUuid, String crlngName, HttpServletResponse response) {
        logger.info("[CoreLeningController]:begin updateCoreLening");
        if (StringUtil.isEmpty(crlngUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }
        if (StringUtil.isEmpty(crlngName)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "教辅名称不能为空!"), response);
            return;
        }

        CoreLening coreLening = new CoreLening();
        coreLening.setCrlngUuid(crlngUuid);
        coreLening.setCrlngName(crlngName);

        coreLeningService.updateCoreLening(coreLening);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[CoreLeningController]:end updateCoreLening");
    }

    /**
     * 删除
     *
     * @param crlngUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteCoreLening(String crlngUuid, HttpServletResponse response) {
        logger.info("[CoreLeningController]:begin deleteCoreLening");

        if (StringUtil.isEmpty(crlngUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CoreLening coreLening = new CoreLening();
        coreLening.setCrlngUuid(crlngUuid);
        coreLening.setCrlngStatus(StatusEnum.DISABLE.getCode());
        coreLeningService.updateCoreLening(coreLening);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[CoreLeningController]:end deleteCoreLening");
    }

    /**
     * 批量删除
     *
     * @param crlngUuids UUID集合
     * @return
     */
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchCoreLening(String crlngUuids, HttpServletResponse response) {
        logger.info("[CoreLeningController]:begin deleteBatchCoreLening");

        if (StringUtil.isEmpty(crlngUuids)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[UUID集合]不能为空!"), response);
            return;
        }

        String[] uuids = crlngUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        coreLeningService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[CoreLeningController]:end deleteBatchCoreLening");
    }

    /**
     * 获取单个
     *
     * @param crlngUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/views", method = RequestMethod.POST)
    public void viewsCoreLening(String crlngUuid, HttpServletResponse response) {
        logger.info("[CoreLeningController]:begin viewsCoreLening");

        if (StringUtil.isEmpty(crlngUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CoreLening coreLening = new CoreLening();
        coreLening.setCrlngUuid(crlngUuid);

        coreLening = coreLeningService.getCoreLening(coreLening);

        CoreLeningVO coreLeningVO = new CoreLeningVO();
        coreLeningVO.convertPOToVO(coreLening);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreLeningVO), response);
        logger.info("[CoreLeningController]:end viewsCoreLening");
    }

    /**
     * 获取列表<List>
     *
     * @return
     */
    @RequestMapping(value = "/find/all", method = RequestMethod.POST)
    public void findCoreLeningList(HttpServletResponse response) {
        logger.info("[CoreLeningController]:begin findCoreLeningList");
        List<CoreLening> lists = coreLeningService.findCoreLeningList();
        List<CoreLeningVO> vos = new ArrayList<CoreLeningVO>();
        CoreLeningVO vo;
        for (CoreLening coreLening : lists) {
            vo = new CoreLeningVO();
            vo.convertPOToVO(coreLening);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[CoreLeningController]:end findCoreLeningList");
    }

    /**
     * 获取列表<Page>
     *
     * @param coreLening
     * @param pageNum    页码
     * @param pageSize   页数
     * @return
     */
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findCoreLeningPage(CoreLening coreLening, Integer pageNum, Integer pageSize, HttpServletResponse response) {
        logger.info("[CoreLeningController]:begin findCoreLeningPage");

        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CoreLening> page = coreLeningService.findCoreLeningPage(coreLening, pageNum, pageSize);
        Page<CoreLeningVO> pageVO = new Page<CoreLeningVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CoreLeningVO> vos = new ArrayList<CoreLeningVO>();
        CoreLeningVO vo;
        for (CoreLening coreLeningPO : page.getResult()) {
            vo = new CoreLeningVO();
            vo.convertPOToVO(coreLeningPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreLeningController]:end findCoreLeningPage");
    }
}