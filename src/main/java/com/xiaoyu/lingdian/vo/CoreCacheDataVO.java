package com.xiaoyu.lingdian.vo;

import com.xiaoyu.lingdian.entity.CoreCacheData;
import com.xiaoyu.lingdian.vo.BaseVO;

/**
 * 缓存数据表
 */
public class CoreCacheDataVO implements BaseVO {

    /**
     * 标识UUID
     */
    private String crcdaUuid;

    /**
     * 缓存数据名称
     */
    private String crcdaName;

    /**
     * 缓存数据值
     */
    private String crcdaValue;

    public void setCrcdaUuid(String crcdaUuid) {
        this.crcdaUuid = crcdaUuid;
    }

    public String getCrcdaUuid() {
        return crcdaUuid;
    }

    public void setCrcdaName(String crcdaName) {
        this.crcdaName = crcdaName;
    }

    public String getCrcdaName() {
        return crcdaName;
    }

    public void setCrcdaValue(String crcdaValue) {
        this.crcdaValue = crcdaValue;
    }

    public String getCrcdaValue() {
        return crcdaValue;
    }

    public CoreCacheDataVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreCacheData po = (CoreCacheData) poObj;
        this.crcdaUuid = po.getCrcdaUuid();
        this.crcdaName = po.getCrcdaName();
        this.crcdaValue = po.getCrcdaValue();
    }
//<=================定制内容开始==============
//==================定制内容结束==============>

}