package com.xiaoyu.lingdian.vo;

import com.xiaoyu.lingdian.entity.CoreSchool;

/**
 * 学校表
 *
 * @author: zhangy
 * @since: 2017年12月31日 14:44:09
 * @history:
 */
public class CoreSchoolVO implements BaseVO {
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

    public CoreSchoolVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreSchool po = (CoreSchool) poObj;
        this.crsclUuid = po.getCrsclUuid();
        this.crsclName = po.getCrsclName();
    }
}