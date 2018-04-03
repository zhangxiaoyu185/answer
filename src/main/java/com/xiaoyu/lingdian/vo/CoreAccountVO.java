package com.xiaoyu.lingdian.vo;

import java.util.List;

import com.xiaoyu.lingdian.entity.CoreAccount;
import com.xiaoyu.lingdian.entity.CoreFunction;
import com.xiaoyu.lingdian.vo.BaseVO;
import com.xiaoyu.lingdian.tool.DateUtil;

public class CoreAccountVO implements BaseVO {

    /**
     * 标识UUID
     */
    private String cractUuid;

    /**
     * 帐户名称
     */
    private String cractName;

    /**
     * 登录密码
     */
    private String cractPassword;

    /**
     * 状态:1启用,0禁用
     */
    private Integer cractStatus;

    /**
     * 角色集合
     */
    private String cractRoles;

    /**
     * 角色名称集合
     */
    private String cractRolesName;

    /**
     * 创建日期
     */
    private String cractCdate;

    /**
     * 修改日期
     */
    private String cractUdate;

    /**
     * 联系方式
     */
    private String cractTel;

    /**
     * 邮箱
     */
    private String cractEmail;

    /**
     * 备注
     */
    private String cractRemarks;

    /**
     * 菜单对象集合
     */
    private List<CoreFunction> coreFunctions;

    /**
     * 推送token值
     */
    private String pushToken;

    /**
     * 推送权限栏目
     */
    private String pushMenuList;

    public void setCractUuid(String cractUuid) {
        this.cractUuid = cractUuid;
    }

    public String getCractUuid() {
        return cractUuid;
    }

    public void setCractName(String cractName) {
        this.cractName = cractName;
    }

    public String getCractName() {
        return cractName;
    }

    public void setCractPassword(String cractPassword) {
        this.cractPassword = cractPassword;
    }

    public String getCractPassword() {
        return cractPassword;
    }

    public void setCractStatus(Integer cractStatus) {
        this.cractStatus = cractStatus;
    }

    public Integer getCractStatus() {
        return cractStatus;
    }

    public void setCractRoles(String cractRoles) {
        this.cractRoles = cractRoles;
    }

    public String getCractRoles() {
        return cractRoles;
    }

    public String getCractRolesName() {
        return cractRolesName;
    }

    public void setCractRolesName(String cractRolesName) {
        this.cractRolesName = cractRolesName;
    }

    public void setCractCdate(String cractCdate) {
        this.cractCdate = cractCdate;
    }

    public String getCractCdate() {
        return cractCdate;
    }

    public void setCractUdate(String cractUdate) {
        this.cractUdate = cractUdate;
    }

    public String getCractUdate() {
        return cractUdate;
    }

    public String getCractTel() {
        return cractTel;
    }

    public void setCractTel(String cractTel) {
        this.cractTel = cractTel;
    }

    public String getCractEmail() {
        return cractEmail;
    }

    public void setCractEmail(String cractEmail) {
        this.cractEmail = cractEmail;
    }

    public void setCractRemarks(String cractRemarks) {
        this.cractRemarks = cractRemarks;
    }

    public String getCractRemarks() {
        return cractRemarks;
    }

    public List<CoreFunction> getCoreFunctions() {
        return coreFunctions;
    }

    public void setCoreFunctions(List<CoreFunction> coreFunctions) {
        this.coreFunctions = coreFunctions;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public String getPushMenuList() {
        return pushMenuList;
    }

    public void setPushMenuList(String pushMenuList) {
        this.pushMenuList = pushMenuList;
    }

    public CoreAccountVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreAccount po = (CoreAccount) poObj;
        this.cractUuid = po.getCractUuid();
        this.cractName = po.getCractName();
        this.cractPassword = po.getCractPassword();
        this.cractStatus = po.getCractStatus();
        this.cractRoles = po.getCractRoles();
        this.cractCdate = po.getCractCdate() != null ? DateUtil.formatDate(DateUtil.DEFAULT_PATTERN, po.getCractCdate()) : "";
        this.cractUdate = po.getCractUdate() != null ? DateUtil.formatDate(DateUtil.DEFAULT_PATTERN, po.getCractUdate()) : "";
        this.cractEmail = po.getCractEmail();
        this.cractTel = po.getCractTel();
        this.cractRemarks = po.getCractRemarks();
    }

}