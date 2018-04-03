package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 试卷表
 *
 * @author: zhangy
 * @since: 2017年12月31日 14:44:10
 * @history:
 */
public class CorePapers extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crpesUnid;

    /**
     * 标识UUID
     */
    private String crpesUuid;

    /**
     * 所属教辅
     */
    private String crpesLening;

    /**
     * 试卷名
     */
    private String crpesName;

    /**
     * 查看视频过期时间（小时）
     */
    private String crpesExpire;

    /**
     * 题目内容
     */
    private String crpesQuestion;

    /**
     * 总分（各题目分数之和）
     */
    private Integer crpesScore;

    /**
     * 创建时间
     */
    private Date crpesCdate;

    /**
     * 修改时间
     */
    private Date crpesUdate;

    /**
     * 状态:1启用,0禁用
     */
    private Integer crpesStatus;

    /**
     * 开放状态:1已开放,0未开放
     */
    private Integer crpesOpen;

    /**
     * 教辅名称
     */
    private String crlngName;

    public Integer getCrpesUnid() {
        return crpesUnid;
    }

    public void setCrpesUnid(Integer crpesUnid) {
        this.crpesUnid = crpesUnid;
    }

    public String getCrpesUuid() {
        return crpesUuid;
    }

    public void setCrpesUuid(String crpesUuid) {
        this.crpesUuid = crpesUuid;
    }

    public String getCrpesLening() {
        return crpesLening;
    }

    public void setCrpesLening(String crpesLening) {
        this.crpesLening = crpesLening;
    }

    public String getCrpesName() {
        return crpesName;
    }

    public void setCrpesName(String crpesName) {
        this.crpesName = crpesName;
    }

    public String getCrpesExpire() {
        return crpesExpire;
    }

    public void setCrpesExpire(String crpesExpire) {
        this.crpesExpire = crpesExpire;
    }

    public Integer getCrpesScore() {
        return crpesScore;
    }

    public void setCrpesScore(Integer crpesScore) {
        this.crpesScore = crpesScore;
    }

    public Date getCrpesCdate() {
        return crpesCdate;
    }

    public void setCrpesCdate(Date crpesCdate) {
        this.crpesCdate = crpesCdate;
    }

    public Date getCrpesUdate() {
        return crpesUdate;
    }

    public void setCrpesUdate(Date crpesUdate) {
        this.crpesUdate = crpesUdate;
    }

    public Integer getCrpesStatus() {
        return crpesStatus;
    }

    public void setCrpesStatus(Integer crpesStatus) {
        this.crpesStatus = crpesStatus;
    }

    public String getCrlngName() {
        return crlngName;
    }

    public void setCrlngName(String crlngName) {
        this.crlngName = crlngName;
    }

    public String getCrpesQuestion() {
        return crpesQuestion;
    }

    public void setCrpesQuestion(String crpesQuestion) {
        this.crpesQuestion = crpesQuestion;
    }

    public Integer getCrpesOpen() {
        return crpesOpen;
    }

    public void setCrpesOpen(Integer crpesOpen) {
        this.crpesOpen = crpesOpen;
    }

    public CorePapers() {
    }

}