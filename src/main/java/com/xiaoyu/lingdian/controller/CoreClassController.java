package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.service.CoreClassService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreClass;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 班级表
 */
@Controller
@RequestMapping(value = "/coreClass")
public class CoreClassController extends BaseController {

    /**
     * 班级表
     */
    @Autowired
    private CoreClassService coreClassService;

    /**
     * 添加
     *
     * @param crcasSchool 所属学校
     * @param crcasName   班级名称
     * @return
     */
    @RequestMapping(value = "/add/coreClass", method = RequestMethod.POST)
    public void addCoreClass(String crcasSchool, String crcasName, HttpServletResponse response) {
        logger.info("[CoreClassController]:begin addCoreClass");
        if (StringUtil.isEmpty(crcasSchool)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "所属学校不能为空!"), response);
            return;
        }
        if (StringUtil.isEmpty(crcasName)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "班级名称不能为空!"), response);
            return;
        }

        CoreClass coreClass = new CoreClass();
        String uuid = RandomUtil.generateString(16);
        coreClass.setCrcasUuid(uuid);
        coreClass.setCrcasSchool(crcasSchool);
        coreClass.setCrcasName(crcasName);

        coreClassService.insertCoreClass(coreClass);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[CoreClassController]:end addCoreClass");
    }

    /**
     * 修改
     *
     * @param crcasUuid   标识UUID
     * @param crcasName   班级名称
     * @return
     */
    @RequestMapping(value = "/update/coreClass", method = RequestMethod.POST)
    public void updateCoreClass(String crcasUuid, String crcasName, HttpServletResponse response) {
        logger.info("[CoreClassController]:begin updateCoreClass");
        if (StringUtil.isEmpty(crcasUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }
        if (StringUtil.isEmpty(crcasName)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "班级名称不能为空!"), response);
            return;
        }

        CoreClass coreClass = new CoreClass();
        coreClass.setCrcasUuid(crcasUuid);
        coreClass.setCrcasName(crcasName);

        coreClassService.updateCoreClass(coreClass);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[CoreClassController]:end updateCoreClass");
    }

    /**
     * 删除
     *
     * @param crcasUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteCoreClass(String crcasUuid, HttpServletResponse response) {
        logger.info("[CoreClassController]:begin deleteCoreClass");

        if (StringUtil.isEmpty(crcasUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CoreClass coreClass = new CoreClass();
        coreClass.setCrcasUuid(crcasUuid);

        coreClassService.deleteCoreClass(coreClass);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[CoreClassController]:end deleteCoreClass");
    }

    /**
     * 批量删除
     *
     * @param crcasUuids UUID集合
     * @return
     */
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchCoreClass(String crcasUuids, HttpServletResponse response) {
        logger.info("[CoreClassController]:begin deleteBatchCoreClass");

        if (StringUtil.isEmpty(crcasUuids)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[UUID集合]不能为空!"), response);
            return;
        }

        String[] uuids = crcasUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        coreClassService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[CoreClassController]:end deleteBatchCoreClass");
    }

    /**
     * 获取单个
     *
     * @param crcasUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/views", method = RequestMethod.POST)
    public void viewsCoreClass(String crcasUuid, HttpServletResponse response) {
        logger.info("[CoreClassController]:begin viewsCoreClass");

        if (StringUtil.isEmpty(crcasUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CoreClass coreClass = new CoreClass();
        coreClass.setCrcasUuid(crcasUuid);

        coreClass = coreClassService.getCoreClass(coreClass);

        CoreClassVO coreClassVO = new CoreClassVO();
        coreClassVO.convertPOToVO(coreClass);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreClassVO), response);
        logger.info("[CoreClassController]:end viewsCoreClass");
    }

    /**
     * 获取学校下的班级列表<List>
     *
     * @return
     */
    @RequestMapping(value = "/find/all", method = RequestMethod.POST)
    public void findCoreClassList(String crcasSchool, HttpServletResponse response) {
        logger.info("[CoreClassController]:begin findCoreClassList");
        if (StringUtil.isEmpty(crcasSchool)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "所属学校不能为空!"), response);
            return;
        }
        List<CoreClass> lists = coreClassService.findCoreClassList(crcasSchool);
        List<CoreClassVO> vos = new ArrayList<CoreClassVO>();
        CoreClassVO vo;
        for (CoreClass coreClass : lists) {
            vo = new CoreClassVO();
            vo.convertPOToVO(coreClass);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[CoreClassController]:end findCoreClassList");
    }

    /**
     * 获取列表<Page>
     *
     * @param coreClass
     * @param pageNum   页码
     * @param pageSize  页数
     * @return
     */
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findCoreClassPage(CoreClass coreClass, Integer pageNum, Integer pageSize, HttpServletResponse response) {
        logger.info("[CoreClassController]:begin findCoreClassPage");

        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CoreClass> page = coreClassService.findCoreClassPage(coreClass, pageNum, pageSize);
        Page<CoreClassVO> pageVO = new Page<CoreClassVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CoreClassVO> vos = new ArrayList<CoreClassVO>();
        CoreClassVO vo;
        for (CoreClass coreClassPO : page.getResult()) {
            vo = new CoreClassVO();
            vo.convertPOToVO(coreClassPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreClassController]:end findCoreClassPage");
    }

}