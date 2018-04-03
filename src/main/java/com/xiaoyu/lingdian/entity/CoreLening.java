package com.xiaoyu.lingdian.entity;


/**
 * 教辅表
 *
 * @author: zhangy
 * @since: 2017年12月31日 14:44:10
 * @history:
 */
public class CoreLening extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crlngUnid;

    /**
     * 标识UUID
     */
    private String crlngUuid;

    /**
     * 教辅名称
     */
    private String crlngName;

    /**
     * 状态:1启用,0禁用
     */
    private Integer crlngStatus;

    public Integer getCrlngUnid() {
        return crlngUnid;
    }

    public void setCrlngUnid(Integer crlngUnid) {
        this.crlngUnid = crlngUnid;
    }

    public String getCrlngUuid() {
        return crlngUuid;
    }

    public void setCrlngUuid(String crlngUuid) {
        this.crlngUuid = crlngUuid;
    }

    public String getCrlngName() {
        return crlngName;
    }

    public void setCrlngName(String crlngName) {
        this.crlngName = crlngName;
    }

    public Integer getCrlngStatus() {
        return crlngStatus;
    }

    public void setCrlngStatus(Integer crlngStatus) {
        this.crlngStatus = crlngStatus;
    }

    public CoreLening() {
    }

}