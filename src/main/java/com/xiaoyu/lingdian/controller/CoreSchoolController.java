package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.service.CoreSchoolService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreSchool;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreSchoolVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 学校表
 */
@Controller
@RequestMapping(value = "/coreSchool")
public class CoreSchoolController extends BaseController {

    /**
     * 学校表
     */
    @Autowired
    private CoreSchoolService coreSchoolService;

    /**
     * 添加
     *
     * @param crsclName 学校名称
     * @return
     */
    @RequestMapping(value = "/add/coreSchool", method = RequestMethod.POST)
    public void addCoreSchool(String crsclName, HttpServletResponse response) {
        logger.info("[CoreSchoolController]:begin addCoreSchool");
        if (StringUtil.isEmpty(crsclName)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "学校名称不能为空!"), response);
            return;
        }

        CoreSchool coreSchool = new CoreSchool();
        String uuid = RandomUtil.generateString(16);
        coreSchool.setCrsclUuid(uuid);
        coreSchool.setCrsclName(crsclName);

        coreSchoolService.insertCoreSchool(coreSchool);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[CoreSchoolController]:end addCoreSchool");
    }

    /**
     * 修改
     *
     * @param crsclUuid 标识UUID
     * @param crsclName 学校名称
     * @return
     */
    @RequestMapping(value = "/update/coreSchool", method = RequestMethod.POST)
    public void updateCoreSchool(String crsclUuid, String crsclName, HttpServletResponse response) {
        logger.info("[CoreSchoolController]:begin updateCoreSchool");
        if (StringUtil.isEmpty(crsclUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }
        if (StringUtil.isEmpty(crsclName)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "学校名称不能为空!"), response);
            return;
        }

        CoreSchool coreSchool = new CoreSchool();
        coreSchool.setCrsclUuid(crsclUuid);
        coreSchool.setCrsclName(crsclName);

        coreSchoolService.updateCoreSchool(coreSchool);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[CoreSchoolController]:end updateCoreSchool");
    }

    /**
     * 删除
     *
     * @param crsclUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteCoreSchool(String crsclUuid, HttpServletResponse response) {
        logger.info("[CoreSchoolController]:begin deleteCoreSchool");

        if (StringUtil.isEmpty(crsclUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CoreSchool coreSchool = new CoreSchool();
        coreSchool.setCrsclUuid(crsclUuid);

        coreSchoolService.deleteCoreSchool(coreSchool);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[CoreSchoolController]:end deleteCoreSchool");
    }

    /**
     * 批量删除
     *
     * @param crsclUuids UUID集合
     * @return
     */
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchCoreSchool(String crsclUuids, HttpServletResponse response) {
        logger.info("[CoreSchoolController]:begin deleteBatchCoreSchool");

        if (StringUtil.isEmpty(crsclUuids)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[UUID集合]不能为空!"), response);
            return;
        }

        String[] uuids = crsclUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        coreSchoolService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[CoreSchoolController]:end deleteBatchCoreSchool");
    }

    /**
     * 获取单个
     *
     * @param crsclUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/views", method = RequestMethod.POST)
    public void viewsCoreSchool(String crsclUuid, HttpServletResponse response) {
        logger.info("[CoreSchoolController]:begin viewsCoreSchool");

        if (StringUtil.isEmpty(crsclUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CoreSchool coreSchool = new CoreSchool();
        coreSchool.setCrsclUuid(crsclUuid);

        coreSchool = coreSchoolService.getCoreSchool(coreSchool);

        CoreSchoolVO coreSchoolVO = new CoreSchoolVO();
        coreSchoolVO.convertPOToVO(coreSchool);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreSchoolVO), response);
        logger.info("[CoreSchoolController]:end viewsCoreSchool");
    }

    /**
     * 获取所有学校列表<List>
     *
     * @return
     */
    @RequestMapping(value = "/find/all", method = RequestMethod.POST)
    public void findCoreSchoolList(HttpServletResponse response) {
        logger.info("[CoreSchoolController]:begin findCoreSchoolList");
        List<CoreSchool> lists = coreSchoolService.findCoreSchoolList();
        List<CoreSchoolVO> vos = new ArrayList<CoreSchoolVO>();
        CoreSchoolVO vo;
        for (CoreSchool coreSchool : lists) {
            vo = new CoreSchoolVO();
            vo.convertPOToVO(coreSchool);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[CoreSchoolController]:end findCoreSchoolList");
    }

    /**
     * 获取列表<Page>
     *
     * @param coreSchool
     * @param pageNum    页码
     * @param pageSize   页数
     * @return
     */
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findCoreSchoolPage(CoreSchool coreSchool, Integer pageNum, Integer pageSize, HttpServletResponse response) {
        logger.info("[CoreSchoolController]:begin findCoreSchoolPage");

        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CoreSchool> page = coreSchoolService.findCoreSchoolPage(coreSchool, pageNum, pageSize);
        Page<CoreSchoolVO> pageVO = new Page<CoreSchoolVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CoreSchoolVO> vos = new ArrayList<CoreSchoolVO>();
        CoreSchoolVO vo;
        for (CoreSchool coreSchoolPO : page.getResult()) {
            vo = new CoreSchoolVO();
            vo.convertPOToVO(coreSchoolPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreSchoolController]:end findCoreSchoolPage");
    }

}