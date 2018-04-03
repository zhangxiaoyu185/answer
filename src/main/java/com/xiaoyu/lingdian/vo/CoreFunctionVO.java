package com.xiaoyu.lingdian.vo;

import com.xiaoyu.lingdian.entity.CoreFunction;
import com.xiaoyu.lingdian.vo.BaseVO;
import com.xiaoyu.lingdian.tool.DateUtil;

public class CoreFunctionVO implements BaseVO {

    /**
     * 标识UUID
     */
    private String crfntUuid;

    /**
     * 功能项名称
     */
    private String crfntFunName;

    /**
     * 资源请求地址
     */
    private String crfntResource;

    /**
     * 状态
     */
    private Integer crfntStatus;

    /**
     * 创建日期
     */
    private String crfntCdate;

    /**
     * 修改日期
     */
    private String crfntUdate;

    /**
     * 排序号
     */
    private Integer crfntOrd;

    /**
     * 上级菜单
     */
    private String crfntFatherUuid;

    /**
     * 上级菜单名
     */
    private String crfntFatherName;

    public void setCrfntUuid(String crfntUuid) {
        this.crfntUuid = crfntUuid;
    }

    public String getCrfntUuid() {
        return crfntUuid;
    }

    public void setCrfntFunName(String crfntFunName) {
        this.crfntFunName = crfntFunName;
    }

    public String getCrfntFunName() {
        return crfntFunName;
    }

    public void setCrfntResource(String crfntResource) {
        this.crfntResource = crfntResource;
    }

    public String getCrfntResource() {
        return crfntResource;
    }

    public void setCrfntStatus(Integer crfntStatus) {
        this.crfntStatus = crfntStatus;
    }

    public Integer getCrfntStatus() {
        return crfntStatus;
    }

    public void setCrfntCdate(String crfntCdate) {
        this.crfntCdate = crfntCdate;
    }

    public String getCrfntCdate() {
        return crfntCdate;
    }

    public void setCrfntUdate(String crfntUdate) {
        this.crfntUdate = crfntUdate;
    }

    public String getCrfntUdate() {
        return crfntUdate;
    }

    public void setCrfntOrd(Integer crfntOrd) {
        this.crfntOrd = crfntOrd;
    }

    public Integer getCrfntOrd() {
        return crfntOrd;
    }

    public void setCrfntFatherUuid(String crfntFatherUuid) {
        this.crfntFatherUuid = crfntFatherUuid;
    }

    public String getCrfntFatherUuid() {
        return crfntFatherUuid;
    }

    public String getCrfntFatherName() {
        return crfntFatherName;
    }

    public void setCrfntFatherName(String crfntFatherName) {
        this.crfntFatherName = crfntFatherName;
    }

    public CoreFunctionVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreFunction po = (CoreFunction) poObj;
        this.crfntUuid = po.getCrfntUuid();
        this.crfntFunName = po.getCrfntFunName();
        this.crfntResource = po.getCrfntResource();
        this.crfntStatus = po.getCrfntStatus();
        this.crfntCdate = po.getCrfntCdate() != null ? DateUtil.formatDate(DateUtil.DEFAULT_PATTERN, po.getCrfntCdate()) : "";
        this.crfntUdate = po.getCrfntUdate() != null ? DateUtil.formatDate(DateUtil.DEFAULT_PATTERN, po.getCrfntUdate()) : "";
        this.crfntOrd = po.getCrfntOrd();
        this.crfntFatherUuid = po.getCrfntFatherUuid();
    }

}