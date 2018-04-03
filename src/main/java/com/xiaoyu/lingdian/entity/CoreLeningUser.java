package com.xiaoyu.lingdian.entity;


/**
 * 教辅学生表
 *
 * @author: zhangy
 * @since: 2017年12月31日 14:44:10
 * @history:
 */
public class CoreLeningUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crlurUnid;

    /**
     * 标识UUID
     */
    private String crlurUuid;

    /**
     * 所属教辅
     */
    private String crlurLening;

    /**
     * 开通学生
     */
    private String crlurUser;

    public Integer getCrlurUnid() {
        return crlurUnid;
    }

    public void setCrlurUnid(Integer crlurUnid) {
        this.crlurUnid = crlurUnid;
    }

    public String getCrlurUuid() {
        return crlurUuid;
    }

    public void setCrlurUuid(String crlurUuid) {
        this.crlurUuid = crlurUuid;
    }

    public String getCrlurLening() {
        return crlurLening;
    }

    public void setCrlurLening(String crlurLening) {
        this.crlurLening = crlurLening;
    }

    public String getCrlurUser() {
        return crlurUser;
    }

    public void setCrlurUser(String crlurUser) {
        this.crlurUser = crlurUser;
    }

    public CoreLeningUser() {
    }

}