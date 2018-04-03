package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 试卷回答表
 *
 * @author: zhangy
 * @since: 2017年12月31日 14:44:11
 * @history:
 */
public class CorePapersAnswer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crpsaUnid;

    /**
     * 标识UUID
     */
    private String crpsaUuid;

    /**
     * 所属教辅
     */
    private String crpsaLening;

    /**
     * 所属试卷
     */
    private String crpsaPaper;

    /**
     * 所属用户
     */
    private String crpsaUser;

    /**
     * 试卷分值
     */
    private Integer crpsaPaperScore;

    /**
     * 总得分
     */
    private Integer crpsaScore;

    /**
     * 题目回答集合
     */
    private String crpsaQuestion;

    /**
     * 查看视频过期时间（小时）
     */
    private String crpsaExpire;

    /**
     * 状态:1启用,0禁用
     */
    private Integer crpsaStatus;

    /**
     * 创建时间
     */
    private Date crpsaCdate;

    /**
     * 修改时间
     */
    private Date crpsaUdate;

    /**
     * 教辅名称
     */
    private String crlngName;

    /**
     * 试卷名
     */
    private String crpesName;

    /**
     * 学号
     */
    private String crusrName;

    /**
     * 真实姓名
     */
    private String crusrCode;

    public Integer getCrpsaUnid() {
        return crpsaUnid;
    }

    public void setCrpsaUnid(Integer crpsaUnid) {
        this.crpsaUnid = crpsaUnid;
    }

    public String getCrpsaUuid() {
        return crpsaUuid;
    }

    public void setCrpsaUuid(String crpsaUuid) {
        this.crpsaUuid = crpsaUuid;
    }

    public String getCrpsaLening() {
        return crpsaLening;
    }

    public void setCrpsaLening(String crpsaLening) {
        this.crpsaLening = crpsaLening;
    }

    public String getCrpsaPaper() {
        return crpsaPaper;
    }

    public void setCrpsaPaper(String crpsaPaper) {
        this.crpsaPaper = crpsaPaper;
    }

    public String getCrpsaUser() {
        return crpsaUser;
    }

    public void setCrpsaUser(String crpsaUser) {
        this.crpsaUser = crpsaUser;
    }

    public Integer getCrpsaPaperScore() {
        return crpsaPaperScore;
    }

    public void setCrpsaPaperScore(Integer crpsaPaperScore) {
        this.crpsaPaperScore = crpsaPaperScore;
    }

    public Integer getCrpsaScore() {
        return crpsaScore;
    }

    public void setCrpsaScore(Integer crpsaScore) {
        this.crpsaScore = crpsaScore;
    }

    public String getCrpsaExpire() {
        return crpsaExpire;
    }

    public void setCrpsaExpire(String crpsaExpire) {
        this.crpsaExpire = crpsaExpire;
    }

    public Integer getCrpsaStatus() {
        return crpsaStatus;
    }

    public void setCrpsaStatus(Integer crpsaStatus) {
        this.crpsaStatus = crpsaStatus;
    }

    public Date getCrpsaCdate() {
        return crpsaCdate;
    }

    public void setCrpsaCdate(Date crpsaCdate) {
        this.crpsaCdate = crpsaCdate;
    }

    public Date getCrpsaUdate() {
        return crpsaUdate;
    }

    public void setCrpsaUdate(Date crpsaUdate) {
        this.crpsaUdate = crpsaUdate;
    }

    public String getCrpsaQuestion() {
        return crpsaQuestion;
    }

    public void setCrpsaQuestion(String crpsaQuestion) {
        this.crpsaQuestion = crpsaQuestion;
    }

    public String getCrlngName() {
        return crlngName;
    }

    public void setCrlngName(String crlngName) {
        this.crlngName = crlngName;
    }

    public String getCrpesName() {
        return crpesName;
    }

    public void setCrpesName(String crpesName) {
        this.crpesName = crpesName;
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

    public CorePapersAnswer() {
    }

}