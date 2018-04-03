package com.xiaoyu.lingdian.vo;

import com.xiaoyu.lingdian.entity.CoreClass;

/**
 * 班级表
 *
 * @author: zhangy
 * @since: 2017年12月31日 14:44:09
 * @history:
 */
public class CoreClassVO implements BaseVO {
    /**
     * 标识UNID
     */
    private Integer crcasUnid;

    /**
     * 标识UUID
     */
    private String crcasUuid;

    /**
     * 所属学校
     */
    private String crcasSchool;

    /**
     * 学校名称
     */
    private String crsclName;

    /**
     * 班级名称
     */
    private String crcasName;

    public Integer getCrcasUnid() {
        return crcasUnid;
    }

    public void setCrcasUnid(Integer crcasUnid) {
        this.crcasUnid = crcasUnid;
    }

    public String getCrcasUuid() {
        return crcasUuid;
    }

    public void setCrcasUuid(String crcasUuid) {
        this.crcasUuid = crcasUuid;
    }

    public String getCrcasSchool() {
        return crcasSchool;
    }

    public void setCrcasSchool(String crcasSchool) {
        this.crcasSchool = crcasSchool;
    }

    public String getCrcasName() {
        return crcasName;
    }

    public void setCrcasName(String crcasName) {
        this.crcasName = crcasName;
    }

    public String getCrsclName() {
        return crsclName;
    }

    public void setCrsclName(String crsclName) {
        this.crsclName = crsclName;
    }

    public CoreClassVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreClass po = (CoreClass) poObj;
        this.crcasUuid = po.getCrcasUuid();
        this.crcasSchool = po.getCrcasSchool();
        this.crsclName = po.getCrsclName();
        this.crcasName = po.getCrcasName();
    }
}