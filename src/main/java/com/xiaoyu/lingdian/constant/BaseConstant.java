package com.xiaoyu.lingdian.constant;

import java.util.regex.Pattern;

/**
 * 项目常量
 */
public class BaseConstant {
    /**
     * 验证图片
     */
    public static Pattern P = Pattern.compile("(?i).+?\\.(jpg|gif|bmp|png)$");

    /**
     * 验证音频
     */
    public static Pattern AUDIO_P = Pattern.compile("(?i).+?\\.(ilbc|amr|mp3)$");

    /**
     * 生成音频默认存放目录
     */
    public static String DEFAULT_MEDIA_AUDIO_DIR = "C:\\attachment\\audio\\";

    /**
     * 生成图片默认存放目录
     */
    public static String DEFAULT_MEDIA_PIC_DIR = "C:\\attachment\\pic\\";

    /**
     * 默认附件文件夹
     */
    public static String DEFAULT_ATTACHMENT_DIR = "C:\\file\\answer\\";

    /**
     * 项目域名
     */
    public static String DEFAULT_DOMAIN_NAME = "";

    /**
     * 生成音频默认URL前缀
     */
    public static String DEFAULT_MEDIA_AUDIO_PREFIX = "http://media.hzlingdian.com/audio/";

    /**
     * 生成图片默认URL前缀
     */
    public static String DEFAULT_MEDIA_PIC_PREFIX = "http://media.hzlingdian.com/pic/";

    /**
     * 音频软件默认目录
     */
    public static String DEFAULT_FFMPEG_DIR = "C:\\ffmpeg\\bin\\ffmpeg.exe";

    /**
     * 项目名第一个目录
     */
    public static String FIRST_DIR = "answer\\";

}
