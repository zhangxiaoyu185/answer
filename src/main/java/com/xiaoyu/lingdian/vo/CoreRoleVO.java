package com.xiaoyu.lingdian.vo;

import com.xiaoyu.lingdian.entity.CoreRole;
import com.xiaoyu.lingdian.vo.BaseVO;

public class CoreRoleVO implements BaseVO {

    /**
     * 标识UUID
     */
    private String crrolUuid;

    /**
     * 角色名称
     */
    private String crrolName;

    /**
     * 功能菜单ID集合
     */
    private String crrolFuns;

    /**
     * 功能菜单名称集合
     */
    private String crrolFunsName;

    /**
     * 角色描述
     */
    private String crrolDesc;

    public void setCrrolUuid(String crrolUuid) {
        this.crrolUuid = crrolUuid;
    }

    public String getCrrolUuid() {
        return crrolUuid;
    }

    public void setCrrolName(String crrolName) {
        this.crrolName = crrolName;
    }

    public String getCrrolName() {
        return crrolName;
    }

    public void setCrrolFuns(String crrolFuns) {
        this.crrolFuns = crrolFuns;
    }

    public String getCrrolFuns() {
        return crrolFuns;
    }

    public String getCrrolFunsName() {
        return crrolFunsName;
    }

    public void setCrrolFunsName(String crrolFunsName) {
        this.crrolFunsName = crrolFunsName;
    }

    public void setCrrolDesc(String crrolDesc) {
        this.crrolDesc = crrolDesc;
    }

    public String getCrrolDesc() {
        return crrolDesc;
    }

    public CoreRoleVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreRole po = (CoreRole) poObj;
        this.crrolUuid = po.getCrrolUuid();
        this.crrolName = po.getCrrolName();
        this.crrolFuns = po.getCrrolFuns();
        this.crrolDesc = po.getCrrolDesc();
    }
//<=================定制内容开始==============
//==================定制内容结束==============>

}