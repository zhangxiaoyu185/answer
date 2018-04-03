package com.xiaoyu.lingdian.vo;

import com.xiaoyu.lingdian.entity.CoreUser;

import java.util.Date;

/**
 * 用户表
 *
 * @author: zhangy
 * @since: 2017年12月31日 14:44:08
 * @history:
 */
public class CoreUserVO implements BaseVO {
    /**
     * 标识UNID
     */
    private Integer crusrUnid;

    /**
     * 标识UUID
     */
    private String crusrUuid;

    /**
     * 帐户名称
     */
    private String crusrName;

    /**
     * 真实姓名
     */
    private String crusrCode;

    /**
     * 登录密码
     */
    private String crusrPassword;

    /**
     * 电子邮件
     */
    private String crusrEmail;

    /**
     * 手机号码
     */
    private String crusrMobile;

    /**
     * 会员等级
     */
    private Integer crusrType;

    /**
     * 状态:1启用,0禁用
     */
    private Integer crusrStatus;

    /**
     * 创建日期
     */
    private Date crusrCdate;

    /**
     * 修改日期
     */
    private Date crusrUdate;

    /**
     * 生日
     */
    private String crusrBirthday;

    /**
     * 性别:1男,0女,2其它
     */
    private Integer crusrGender;

    /**
     * QQ
     */
    private String crusrQq;

    /**
     * 地址
     */
    private String crusrAddress;

    /**
     * 备注
     */
    private String crusrRemarks;

    /**
     * 所属班级
     */
    private String crusrClass;

    /**
     * 所属学校
     */
    private String crusrSchool;

    public Integer getCrusrUnid() {
        return crusrUnid;
    }

    public void setCrusrUnid(Integer crusrUnid) {
        this.crusrUnid = crusrUnid;
    }

    public String getCrusrUuid() {
        return crusrUuid;
    }

    public void setCrusrUuid(String crusrUuid) {
        this.crusrUuid = crusrUuid;
    }

    public String getCrusrName() {
        return crusrName;
    }

    public void setCrusrName(String crusrName) {
        this.crusrName = crusrName;
    }

    public String getCrusrCode() {
        return crusrCode;
    }

    public void setCrusrCode(String crusrCode) {
        this.crusrCode = crusrCode;
    }

    public String getCrusrPassword() {
        return crusrPassword;
    }

    public void setCrusrPassword(String crusrPassword) {
        this.crusrPassword = crusrPassword;
    }

    public String getCrusrEmail() {
        return crusrEmail;
    }

    public void setCrusrEmail(String crusrEmail) {
        this.crusrEmail = crusrEmail;
    }

    public String getCrusrMobile() {
        return crusrMobile;
    }

    public void setCrusrMobile(String crusrMobile) {
        this.crusrMobile = crusrMobile;
    }

    public Integer getCrusrType() {
        return crusrType;
    }

    public void setCrusrType(Integer crusrType) {
        this.crusrType = crusrType;
    }

    public Integer getCrusrStatus() {
        return crusrStatus;
    }

    public void setCrusrStatus(Integer crusrStatus) {
        this.crusrStatus = crusrStatus;
    }

    public Date getCrusrCdate() {
        return crusrCdate;
    }

    public void setCrusrCdate(Date crusrCdate) {
        this.crusrCdate = crusrCdate;
    }

    public Date getCrusrUdate() {
        return crusrUdate;
    }

    public void setCrusrUdate(Date crusrUdate) {
        this.crusrUdate = crusrUdate;
    }

    public String getCrusrBirthday() {
        return crusrBirthday;
    }

    public void setCrusrBirthday(String crusrBirthday) {
        this.crusrBirthday = crusrBirthday;
    }

    public Integer getCrusrGender() {
        return crusrGender;
    }

    public void setCrusrGender(Integer crusrGender) {
        this.crusrGender = crusrGender;
    }

    public String getCrusrQq() {
        return crusrQq;
    }

    public void setCrusrQq(String crusrQq) {
        this.crusrQq = crusrQq;
    }

    public String getCrusrAddress() {
        return crusrAddress;
    }

    public void setCrusrAddress(String crusrAddress) {
        this.crusrAddress = crusrAddress;
    }

    public String getCrusrRemarks() {
        return crusrRemarks;
    }

    public void setCrusrRemarks(String crusrRemarks) {
        this.crusrRemarks = crusrRemarks;
    }

    public String getCrusrClass() {
        return crusrClass;
    }

    public void setCrusrClass(String crusrClass) {
        this.crusrClass = crusrClass;
    }

    public String getCrusrSchool() {
        return crusrSchool;
    }

    public void setCrusrSchool(String crusrSchool) {
        this.crusrSchool = crusrSchool;
    }

    public CoreUserVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreUser po = (CoreUser) poObj;
        this.crusrUuid = po.getCrusrUuid();
        this.crusrName = po.getCrusrName();
        this.crusrCode = po.getCrusrCode();
        this.crusrPassword = po.getCrusrPassword();
        this.crusrEmail = po.getCrusrEmail();
        this.crusrMobile = po.getCrusrMobile();
        this.crusrType = po.getCrusrType();
        this.crusrStatus = po.getCrusrStatus();
        this.crusrCdate = po.getCrusrCdate();
        this.crusrUdate = po.getCrusrUdate();
        this.crusrBirthday = po.getCrusrBirthday();
        this.crusrGender = po.getCrusrGender();
        this.crusrQq = po.getCrusrQq();
        this.crusrAddress = po.getCrusrAddress();
        this.crusrRemarks = po.getCrusrRemarks();
        this.crusrClass = po.getCrusrClass();
        this.crusrSchool = po.getCrusrSchool();
    }
}