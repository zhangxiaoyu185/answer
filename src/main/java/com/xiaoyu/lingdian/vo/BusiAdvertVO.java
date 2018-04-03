package com.xiaoyu.lingdian.vo;

import com.xiaoyu.lingdian.entity.BusiAdvert;

import java.util.Date;

/**
 * 广告表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:49
 * @history:
 */
public class BusiAdvertVO implements BaseVO {

    /**
     * 标识UUID
     */
    private String bsaetUuid;

    /**
     * 链接
     */
    private String bsaetLink;

    /**
     * 封面图
     */
    private String bsaetPic;

    /**
     * 顺序
     */
    private Integer bsaetOrd;

    /**
     * 状态:1启动0禁用
     */
    private Integer bsaetStatus;

    /**
     * 描述
     */
    private String bsaetDesc;

    /**
     * 创建时间
     */
    private Date bsaetCdate;

    /**
     * 更新时间
     */
    private Date bsaetUdate;

    public String getBsaetUuid() {
        return bsaetUuid;
    }

    public void setBsaetUuid(String bsaetUuid) {
        this.bsaetUuid = bsaetUuid;
    }

    public String getBsaetLink() {
        return bsaetLink;
    }

    public void setBsaetLink(String bsaetLink) {
        this.bsaetLink = bsaetLink;
    }

    public String getBsaetPic() {
        return bsaetPic;
    }

    public void setBsaetPic(String bsaetPic) {
        this.bsaetPic = bsaetPic;
    }

    public Integer getBsaetOrd() {
        return bsaetOrd;
    }

    public void setBsaetOrd(Integer bsaetOrd) {
        this.bsaetOrd = bsaetOrd;
    }

    public Integer getBsaetStatus() {
        return bsaetStatus;
    }

    public void setBsaetStatus(Integer bsaetStatus) {
        this.bsaetStatus = bsaetStatus;
    }

    public String getBsaetDesc() {
        return bsaetDesc;
    }

    public void setBsaetDesc(String bsaetDesc) {
        this.bsaetDesc = bsaetDesc;
    }

    public Date getBsaetCdate() {
        return bsaetCdate;
    }

    public void setBsaetCdate(Date bsaetCdate) {
        this.bsaetCdate = bsaetCdate;
    }

    public Date getBsaetUdate() {
        return bsaetUdate;
    }

    public void setBsaetUdate(Date bsaetUdate) {
        this.bsaetUdate = bsaetUdate;
    }

    public BusiAdvertVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        BusiAdvert po = (BusiAdvert) poObj;
        this.bsaetUuid = po.getBsaetUuid();
        this.bsaetLink = po.getBsaetLink();
        this.bsaetPic = po.getBsaetPic();
        this.bsaetOrd = po.getBsaetOrd();
        this.bsaetStatus = po.getBsaetStatus();
        this.bsaetDesc = po.getBsaetDesc();
        this.bsaetCdate = po.getBsaetCdate();
        this.bsaetUdate = po.getBsaetUdate();
    }
}