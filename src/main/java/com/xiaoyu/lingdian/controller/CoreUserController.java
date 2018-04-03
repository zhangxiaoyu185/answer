package com.xiaoyu.lingdian.controller;

import java.io.IOException;
import java.util.*;

import com.xiaoyu.lingdian.entity.*;
import com.xiaoyu.lingdian.enums.StatusEnum;
import com.xiaoyu.lingdian.service.*;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.tool.DateUtil;
import com.xiaoyu.lingdian.tool.ExcelUtils;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.encrypt.MD5Util;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreLeningVO;
import com.xiaoyu.lingdian.vo.CoreUserVO;
import com.xiaoyu.lingdian.vo.GradeTotal;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户表
 */
@Controller
@RequestMapping(value = "/coreUser")
public class CoreUserController extends BaseController {

    /**
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

    /**
     * 教辅学生表
     */
    @Autowired
    private CoreLeningService coreLeningService;

    /**
     * 教辅学生表
     */
    @Autowired
    private CoreLeningUserService coreLeningUserService;

    /**
     * 试卷答题表
     */
    @Autowired
    private CorePapersAnswerService corePapersAnswerService;

    /**
     * 判断是否有权限
     *
     * @param user
     * @param paper
     * @param response
     */
    @RequestMapping(value = "/judge/turn", method = RequestMethod.POST)
    public void judgeTurn(String user, String paper, HttpServletResponse response) {
        logger.info("[CoreUserController.judgeTurn]:begin judgeTurn.");
        if (StringUtils.isBlank(user)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "扫码的用户不存在！"), response);
            return;
        }
        if (StringUtils.isBlank(paper)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "扫码的试卷不存在！"), response);
            return;
        }
        //判断是否开通了教辅
        List<CoreLeningUser> leningUserList = coreLeningUserService.findCoreLeningUserByPaperAndUser(paper, user);
        if(CollectionUtils.isEmpty(leningUserList) || null == leningUserList.get(0)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "没有该试卷的答题权限，请联系管理员！"), response);
            return;
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "拥有权限"), response);
        logger.info("[CoreUserController.judgeTurn]:end judgeTurn.");
    }

    /**
     * 登录
     *
     * @param crusrName
     * @param crusrPassword
     * @param paper
     * @param response
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(String crusrName, String crusrPassword, String paper, HttpServletResponse response) {
        logger.info("[CoreUserController.login]:begin login.");
        if (StringUtils.isBlank(crusrName)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入帐户名称或手机号！"), response);
            return;
        }
        if (StringUtils.isBlank(crusrPassword)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入密码！"), response);
            return;
        }
        if (StringUtils.isBlank(paper)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "扫码的试卷不存在！"), response);
            return;
        }
        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrName(crusrName);
        coreUser.setCrusrMobile(crusrName);
        coreUser = coreUserService.loginByNameOrMobile(coreUser);
        String newPassword = MD5Util.md5(crusrPassword);
        if(coreUser == null || !newPassword.equalsIgnoreCase(coreUser.getCrusrPassword())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "密码错误！"), response);
            return;
        }
        CoreUserVO vo = new CoreUserVO();
        vo.convertPOToVO(coreUser);
        vo.setCrusrPassword(crusrPassword);
        //判断是否开通了教辅
        List<CoreLeningUser> leningUserList = coreLeningUserService.findCoreLeningUserByPaperAndUser(paper, vo.getCrusrUuid());
        if(CollectionUtils.isEmpty(leningUserList) || null == leningUserList.get(0)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "没有该试卷的答题权限，请联系管理员！"), response);
            return;
        }
        //判断是否答题过
        List<CorePapersAnswer> answerList = corePapersAnswerService.findCorePapersAnswerByPaperAndUser(paper, coreUser.getCrusrUuid());
        if(CollectionUtils.isEmpty(answerList) || null == answerList.get(0)) {
            //无答题记录，跳转到答题界面
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 2, "登录成功", vo), response);
            logger.info("[CoreUserController.login]:end login.");
        } else{
            //有答题记录，跳转到查看界面
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 3, "登录成功", vo), response);
            logger.info("[CoreUserController.login]:end login.");
        }
    }

    /**
     * 后台重置密码
     *
     * @param crusrUuid
     * @param password
     * @param response
     */
    @RequestMapping(value = "/reset/pwd", method = RequestMethod.POST)
    public void resetPwd(String crusrUuid, String password, HttpServletResponse response) {
        logger.info("[CoreUserController.resetPwd]:begin resetPwd.");
        if (StringUtils.isBlank(crusrUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入uuid！"), response);
            return;
        }
        if (StringUtils.isBlank(password)) {
            password = "e10adc3949ba59abbe56e057f20f883e";
        }
        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(crusrUuid);
        coreUser.setCrusrPassword(password);
        coreUser.setCrusrUdate(new Date());
        coreUserService.updateCoreUser(coreUser);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "重置密码成功！"), response);
        logger.info("[CoreUserController.resetPwd]:end resetPwd.");
    }

    /**
     * 注册
     *
     * @param crusrCode     真实姓名
     * @param crusrPassword 登录密码
     * @param crusrMobile   手机号码
     * @param crusrGender   性别:1男,0女,2其它
     * @param crusrClass    所属班级
     * @param crusrSchool   所属学校
     * @return
     */
    @RequestMapping(value = "/add/coreUser", method = RequestMethod.POST)
    public void addCoreUser(String crusrCode, String crusrPassword, String crusrMobile, Integer crusrGender,
                            String crusrClass, String crusrSchool, HttpServletResponse response) {
        logger.info("[CoreUserController]:begin addCoreUser");
        if (StringUtils.isBlank(crusrCode)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入真实姓名！"), response);
            return;
        }
        if (StringUtils.isBlank(crusrPassword)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入密码！"), response);
            return;
        }
        if (StringUtils.isBlank(crusrMobile)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入手机号！"), response);
            return;
        }
        if (null == crusrGender) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择性别！"), response);
            return;
        }
        if (crusrMobile.length() != 11) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入正确的手机号！"), response);
            return;
        }
        CoreUser coreUserMobile = new CoreUser();
        coreUserMobile.setCrusrMobile(crusrMobile);
        coreUserMobile.setCrusrName(crusrMobile);
        coreUserMobile = this.coreUserService.loginByNameOrMobile(coreUserMobile);
        if(null != coreUserMobile && !StringUtil.isEmpty(coreUserMobile.getCrusrUuid())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "该手机号已经注册，请直接登录！"), response);
            return;
        }
        CoreUser coreUser = new CoreUser();
        String uuid = RandomUtil.generateString(16);
        coreUser.setCrusrUuid(uuid);
        coreUser.setCrusrName(fillCrusrName());
        coreUser.setCrusrCode(crusrCode);
        coreUser.setCrusrPassword(MD5Util.md5(crusrPassword));
        coreUser.setCrusrMobile(crusrMobile);
        coreUser.setCrusrStatus(StatusEnum.ENABLE.getCode());
        coreUser.setCrusrCdate(new Date());
        coreUser.setCrusrUdate(new Date());
        coreUser.setCrusrGender(crusrGender);
        coreUser.setCrusrClass(crusrClass);
        coreUser.setCrusrSchool(crusrSchool);
        coreUserService.insertCoreUser(coreUser);
        coreUser.setCrusrPassword(crusrPassword);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "注册成功!", coreUser), response);
        logger.info("[CoreUserController]:end addCoreUser");
    }

    public String fillCrusrName(){
        int year = DateUtil.getYear(new Date());
        int max = coreUserService.getMaxUnidByUser()+1;
        StringBuffer buf = new StringBuffer();
        buf.append(year);
        for (int i = 0; i < 5 - String.valueOf(max).length(); i++) {
            buf.append("0");
        }
        return buf.append(max).append(RandomUtil.generateLowerString(1)).toString();
    }

    /**
     * 后台修改密码
     *
     * @param crusrUuid
     * @param newPwd
     * @param confirmPwd
     * @param response
     */
    @RequestMapping(value = "/update/pwd", method = RequestMethod.POST)
    public void updatePwd(String crusrUuid, String newPwd, String confirmPwd, HttpServletResponse response) {
        logger.info("[CoreUserController.updatePwd]:begin updatePwd.");
        if (StringUtils.isBlank(crusrUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入用户UUID！"), response);
            return;
        }
        if (StringUtils.isBlank(newPwd)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入新密码！"), response);
            return;
        }
        if (StringUtils.isBlank(confirmPwd)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入确认密码！"), response);
            return;
        }
        if (!newPwd.equals(confirmPwd)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "密码输入不一致！"), response);
            return;
        }
        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(crusrUuid);
        String newMd5PWD = MD5Util.md5(newPwd);
        coreUser.setCrusrPassword(newMd5PWD);
        coreUser.setCrusrUdate(new Date());
        coreUserService.updateCoreUser(coreUser);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改密码成功！"), response);
        logger.info("[CoreUserController.updatePwd]:end updatePwd.");
    }

    /**
     * 删除
     *
     * @param crusrUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteCoreUser(String crusrUuid, HttpServletResponse response) {
        logger.info("[CoreUserController]:begin deleteCoreUser");

        if (StringUtil.isEmpty(crusrUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(crusrUuid);
        coreUser.setCrusrStatus(StatusEnum.DISABLE.getCode());
        coreUserService.updateCoreUser(coreUser);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[CoreUserController]:end deleteCoreUser");
    }

    /**
     * 批量删除
     *
     * @param crusrUuids UUID集合
     * @return
     */
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchCoreUser(String crusrUuids, HttpServletResponse response) {
        logger.info("[CoreUserController]:begin deleteBatchCoreUser");

        if (StringUtil.isEmpty(crusrUuids)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[UUID集合]不能为空!"), response);
            return;
        }

        String[] uuids = crusrUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        coreUserService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[CoreUserController]:end deleteBatchCoreUser");
    }

    /**
     * 获取单个
     *
     * @param crusrUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/views", method = RequestMethod.POST)
    public void viewsCoreUser(String crusrUuid, HttpServletResponse response) {
        logger.info("[CoreUserController]:begin viewsCoreUser");

        if (StringUtil.isEmpty(crusrUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
            return;
        }

        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(crusrUuid);

        coreUser = coreUserService.getCoreUser(coreUser);

        CoreUserVO coreUserVO = new CoreUserVO();
        coreUserVO.convertPOToVO(coreUser);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreUserVO), response);
        logger.info("[CoreUserController]:end viewsCoreUser");
    }

    /**
     * 获取列表<List>
     *
     * @return
     */
    @RequestMapping(value = "/find/all", method = RequestMethod.POST)
    public void findCoreUserList(HttpServletResponse response) {
        logger.info("[CoreUserController]:begin findCoreUserList");

        List<CoreUser> lists = coreUserService.findCoreUserList();
        List<CoreUserVO> vos = new ArrayList<CoreUserVO>();
        CoreUserVO vo;
        for (CoreUser coreUser : lists) {
            vo = new CoreUserVO();
            vo.convertPOToVO(coreUser);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[CoreUserController]:end findCoreUserList");
    }

    /**
     * 获取列表<Page>
     *
     * @param coreUser
     * @param pageNum  页码
     * @param pageSize 页数
     * @return
     */
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findCoreUserPage(CoreUser coreUser, String lening, Integer pageNum, Integer pageSize, HttpServletResponse response) {
        logger.info("[CoreUserController]:begin findCoreUserPage");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CoreUser> page = coreUserService.findCoreUserPage(coreUser, lening, pageNum, pageSize);
        Page<CoreUserVO> pageVO = new Page<CoreUserVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CoreUserVO> vos = new ArrayList<CoreUserVO>();
        CoreUserVO vo;
        for (CoreUser coreUserPO : page.getResult()) {
            vo = new CoreUserVO();
            vo.convertPOToVO(coreUserPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreUserController]:end findCoreUserPage");
    }

    /**
     * 教辅下的所有学生List
     *
     * @return
     */
    @RequestMapping(value = "/find/all/by/lening", method = RequestMethod.POST)
    public void findCoreUserListByLening(String lening, HttpServletResponse response) {
        logger.info("[CoreUserController]:begin findCoreUserListByLening");
        if (StringUtil.isEmpty(lening)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "所属教辅不能为空!"), response);
            return;
        }
        List<CoreUser> lists = coreUserService.findCoreUserForListsByLening(lening);
        List<CoreUserVO> vos = new ArrayList<CoreUserVO>();
        CoreUserVO vo;
        for (CoreUser coreUser : lists) {
            vo = new CoreUserVO();
            vo.convertPOToVO(coreUser);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[CoreUserController]:end findCoreUserListByLening");
    }

    /**
     * 开通教辅权限
     *
     * @param crlurLenings 所属教辅集合
     * @param crlurUser 开通学生
     * @return
     */
    @RequestMapping(value="/add/coreLeningUser", method=RequestMethod.POST)
    public void addCoreLeningUser (String crlurLenings, String crlurUser,  HttpServletResponse response) {
        logger.info("[CoreLeningUserController]:begin addCoreLeningUser");
        if (StringUtil.isEmpty(crlurUser)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "开通学生不能为空!"), response);
            return;
        }
        //先根据开通学生删除原有教辅权限
        CoreLeningUser coreLeningUser = new CoreLeningUser();
        coreLeningUser.setCrlurUser(crlurUser);
        coreLeningUserService.deleteCoreLeningUser(coreLeningUser);
        //再循环添加教辅权限
        if(!StringUtil.isEmpty(crlurLenings)) {
            String[] uuids = crlurLenings.split("\\|");
            if(uuids.length > 0) {
                for (int i=0;i<uuids.length;i++){
                    coreLeningUser = new CoreLeningUser();
                    String uuid = RandomUtil.generateString(16);
                    coreLeningUser.setCrlurUuid(uuid);
                    coreLeningUser.setCrlurLening(uuids[i]);
                    coreLeningUser.setCrlurUser(crlurUser);
                    coreLeningUserService.insertCoreLeningUser(coreLeningUser);
                }
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "开通教辅权限成功!"),response);
        logger.info("[CoreLeningUserController]:end addCoreLeningUser");
    }

    /**
     * 学生拥有的教辅权限列表<List>
     *
     * @return
     */
    @RequestMapping(value="/find/all/by/user", method=RequestMethod.POST)
    public void findCoreLeningUserList (String user, HttpServletResponse response) {
        logger.info("[CoreLeningUserController]:begin findCoreLeningUserList");
        if (StringUtil.isEmpty(user)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "所属用户不能为空!"), response);
            return;
        }
        List<CoreLening> lists = coreLeningService.findCoreLeningForListsByUser(user);
        List<CoreLeningVO> vos = new ArrayList<CoreLeningVO>();
        CoreLeningVO vo;
        for (CoreLening coreLening : lists) {
            vo = new CoreLeningVO();
            vo.convertPOToVO(coreLening);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos),response);
        logger.info("[CoreLeningUserController]:end findCoreLeningUserList");
    }

    /**
     * 后台导出学生
     *
     * @return
     */
    @RequestMapping(value = "/excel/export/user", method = RequestMethod.GET)
    public void exportExcelUser(CoreUser coreUser, String lening, HttpServletResponse response) throws IOException {
        logger.info("[CoreUserController]:begin exportExcelUser");
        final Map<String, String> linkedHeader = new LinkedHashMap<>();
        linkedHeader.put("crusrName", "学号");
        linkedHeader.put("crusrCode", "姓名");
        linkedHeader.put("crusrMobile", "手机号码");
        linkedHeader.put("crusrCdate", "创建日期");
        linkedHeader.put("crusrGender", "性别");
        linkedHeader.put("crusrSchool", "学校");
        linkedHeader.put("crusrClass", "班级");

        List<CoreUser> list = coreUserService.findCoreUserListByParam(coreUser, lening);
        List<String[]> contents = ExcelUtils.buildExcelList(list, new ExcelUtils.ExcelMapper<CoreUser>() {

            @Override
            public String[] mapHeader() {
                return toExcelHeader(linkedHeader);
            }

            @Override
            public String[] mapRowData(CoreUser obj) {
                return toExcelLine(obj, linkedHeader);
            }
        });
        ExcelUtils.writeXls(response, contents, "user_list");
        logger.info("[CoreUserController]:end exportExcelUser");
    }

    private String[] toExcelLine(CoreUser obj, Map<String, String> linkedHeader) {
        List<String> list = new ArrayList<>();
        for (String key : linkedHeader.keySet()) {
            if(key.equals("crusrName")) {
                list.add(obj.getCrusrName());
            }
            if(key.equals("crusrCode")) {
                list.add(obj.getCrusrCode());
            }
            if(key.equals("crusrMobile")) {
                list.add(obj.getCrusrMobile());
            }
            if(key.equals("crusrCdate")) {
                list.add(DateUtil.formatDefaultDate(obj.getCrusrCdate()));
            }
            if(key.equals("crusrGender")) {
                if(obj.getCrusrGender() == 1) {
                    list.add("男");
                } else {
                    list.add("女");
                }
            }
            if(key.equals("crusrSchool")) {
                list.add(obj.getCrusrSchool());
            }
            if(key.equals("crusrClass")) {
                list.add(obj.getCrusrClass());
            }
        }
        return list.toArray(new String[list.size()]);
    }

    private String[] toExcelHeader(Map<String, String> linkedHeader) {
        Collection<String> titles = linkedHeader.values();
        return new ArrayList<>(titles).toArray(new String[titles.size()]);
    }
}