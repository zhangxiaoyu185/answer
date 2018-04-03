package com.xiaoyu.lingdian.entity;


/**
 * 学校表
 *
 * @author: zhangy
 * @since: 2017年12月31日 14:44:09
 * @history:
 */
public class CoreSchool extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crsclUnid;

    /**
     * 标识UUID
     */
    private String crsclUuid;

    /**
     * 学校名称
     */
    private String crsclName;

    public Integer getCrsclUnid() {
        return crsclUnid;
    }

    public void setCrsclUnid(Integer crsclUnid) {
        this.crsclUnid = crsclUnid;
    }

    public String getCrsclUuid() {
        return crsclUuid;
    }

    public void setCrsclUuid(String crsclUuid) {
        this.crsclUuid = crsclUuid;
    }

    public String getCrsclName() {
        return crsclName;
    }

    public void setCrsclName(String crsclName) {
        this.crsclName = crsclName;
    }

    public CoreSchool() {
    }

}