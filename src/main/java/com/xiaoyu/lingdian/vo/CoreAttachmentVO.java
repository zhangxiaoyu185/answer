package com.xiaoyu.lingdian.vo;

import com.xiaoyu.lingdian.entity.CoreAttachment;
import com.xiaoyu.lingdian.vo.BaseVO;
import com.xiaoyu.lingdian.tool.DateUtil;

public class CoreAttachmentVO implements BaseVO {

    /**
     * 标识UUID
     */
    private String cratmUuid;

    /**
     * 业务实体UUID
     */
    private String cratmBusUuid;

    /**
     * 名称
     */
    private String cratmFileName;

    /**
     * 目录,例a/b
     */
    private String cratmDir;

    /**
     * 文件扩展名
     */
    private String cratmExtension;

    /**
     * 创建日期
     */
    private String cratmCdate;

    /**
     * 状态:1启用,0禁用
     */
    private Integer cratmStatus;

    /**
     * 宽度
     */
    private Integer cratmWidth;

    /**
     * 高度
     */
    private Integer cratmHeight;

    /**
     * 1图片2音频3视频4其它
     */
    private Integer cratmType;

    public void setCratmUuid(String cratmUuid) {
        this.cratmUuid = cratmUuid;
    }

    public String getCratmUuid() {
        return cratmUuid;
    }

    public void setCratmBusUuid(String cratmBusUuid) {
        this.cratmBusUuid = cratmBusUuid;
    }

    public String getCratmBusUuid() {
        return cratmBusUuid;
    }

    public void setCratmFileName(String cratmFileName) {
        this.cratmFileName = cratmFileName;
    }

    public String getCratmFileName() {
        return cratmFileName;
    }

    public void setCratmDir(String cratmDir) {
        this.cratmDir = cratmDir;
    }

    public String getCratmDir() {
        return cratmDir;
    }

    public void setCratmExtension(String cratmExtension) {
        this.cratmExtension = cratmExtension;
    }

    public String getCratmExtension() {
        return cratmExtension;
    }

    public void setCratmCdate(String cratmCdate) {
        this.cratmCdate = cratmCdate;
    }

    public String getCratmCdate() {
        return cratmCdate;
    }

    public void setCratmStatus(Integer cratmStatus) {
        this.cratmStatus = cratmStatus;
    }

    public Integer getCratmStatus() {
        return cratmStatus;
    }

    public Integer getCratmWidth() {
        return cratmWidth;
    }

    public void setCratmWidth(Integer cratmWidth) {
        this.cratmWidth = cratmWidth;
    }

    public Integer getCratmHeight() {
        return cratmHeight;
    }

    public void setCratmHeight(Integer cratmHeight) {
        this.cratmHeight = cratmHeight;
    }

    public Integer getCratmType() {
        return cratmType;
    }

    public void setCratmType(Integer cratmType) {
        this.cratmType = cratmType;
    }

    public CoreAttachmentVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreAttachment po = (CoreAttachment) poObj;
        this.cratmUuid = po.getCratmUuid();
        this.cratmBusUuid = po.getCratmBusUuid();
        this.cratmFileName = po.getCratmFileName();
        this.cratmDir = po.getCratmDir();
        this.cratmExtension = po.getCratmExtension();
        this.cratmCdate = po.getCratmCdate() != null ? DateUtil.formatDate(DateUtil.DEFAULT_PATTERN, po.getCratmCdate()) : "";
        this.cratmStatus = po.getCratmStatus();
        this.cratmHeight = po.getCratmHeight();
        this.cratmWidth = po.getCratmWidth();
        this.cratmType = po.getCratmType();
    }

}