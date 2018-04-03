package com.xiaoyu.lingdian.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoyu.lingdian.constant.BaseConstant;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.xiaoyu.lingdian.entity.CoreAttachment;
import com.xiaoyu.lingdian.service.CoreAttachmentService;
import com.xiaoyu.lingdian.tool.IOUtil;
import com.xiaoyu.lingdian.tool.MediaUtil;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.init.ConfigIni;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreAttachmentVO;

@Controller
@RequestMapping(value = "/coreAttachment")
public class CoreAttachmentController extends BaseController {

    @Autowired
    private CoreAttachmentService coreAttachmentService;

    /**
     * 上传附件（包括文件、图片、压缩包、音频、视频）,支持多文件
     *
     * @param cratmType 类型：1图片2音频3视频4其它
     * @param cratmDir  目录,例a/b
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(Integer cratmType, String cratmDir, HttpServletRequest request, HttpServletResponse response) {
        logger.info("[CoreAttachmentController.upload]:begin upload.");
        if (StringUtils.isBlank(cratmDir)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少上级目录！"), response);
            return;
        }
        String uuids = "";
        String error = "";
        // 解析器解析request的上下文/创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            // 再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    if (file.isEmpty()) {
                        writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "文件为空，不能上传"), response);
                        logger.info("[CoreAttachmentController.upload]:end upload.");
                        return;
                    }
                    // 记录上传过程起始时的时间，用来计算上传时间
                    int pre = (int) System.currentTimeMillis();
                    String orignalFilename = file.getOriginalFilename(); //原文件名,带后缀
                    String fileName = delAfterLastPoint(orignalFilename) + RandomUtil.generateString(5) + getAfterLastPoint(orignalFilename); //带随机数的新文件名
                    String uuid = RandomUtil.generateString(16);
                    if (cratmType == 1) { //图片
                        Matcher m = BaseConstant.P.matcher(fileName);
                        if (!m.matches()) {
                            error += orignalFilename;
                            error += "上传失败：只支持JPG或JPEG、BMP、PNG格式的图片文件";
                            break;
                        }
                        if (file.getSize() <= 3145728) { //大小超过3M的不上传
                            //获得上传图片的宽度
                            int width = getPicWidth(file);
                            int height = getPicHeight(file);
                            if (width == -1 || width == 0) {
                                error += orignalFilename;
                                error += "上传失败：图片损坏，不能上传";
                                break;
                            }
                            uuids += uuid;
                            uuids += "|";
                            String path = ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + cratmDir + "/" + fileName;
                            IOUtil.createFile(path);
                            File localFile = new File(path);
                            // 写文件到本地
                            try {
                                file.transferTo(localFile);
                            } catch (IllegalStateException e) {
                                logger.info(e.getMessage());
                                error += orignalFilename;
                                error += "上传失败：文件错误，不能上传到服务器";
                                break;
                            } catch (IOException e) {
                                logger.info(e.getMessage());
                                error += orignalFilename;
                                error += "上传失败：文件错误，不能上传到服务器";
                                break;
                            }
                            // 记录上传该文件后的时间
                            int finaltime = (int) System.currentTimeMillis();
                            int min = finaltime - pre;
                            logger.info(fileName + "耗时 : " + min);
                            //插入数据库中
                            CoreAttachment coreAttachment = new CoreAttachment();
                            coreAttachment.setCratmUuid(uuid);
                            coreAttachment.setCratmCdate(new Date());
                            coreAttachment.setCratmFileName(fileName);
                            coreAttachment.setCratmStatus(1);
                            coreAttachment.setCratmDir(cratmDir);
                            coreAttachment.setCratmExtension(getAfterLastPoint(fileName));
                            coreAttachment.setCratmHeight(height);
                            coreAttachment.setCratmType(cratmType);
                            coreAttachment.setCratmWidth(width);
                            coreAttachmentService.insertCoreAttachment(coreAttachment);
                        } else {
                            error += orignalFilename;
                            error += "上传失败：图片大小请保持在3M以内";
                            break;
                        }
                    }
                    if (cratmType == 2) { //音频
                        Matcher m = BaseConstant.AUDIO_P.matcher(fileName);
                        if (!m.matches()) {
                            error += orignalFilename;
                            error += "上传失败：只支持ILBC或AMR或MP3格式的音频文件";
                            break;
                        }
                        if (file.getSize() <= 10485760) { //大小超过10M的不上传
                            uuids += uuid;
                            uuids += "|";
                            String path = ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + cratmDir + "/";
                            IOUtil.createFolder(path);
                            File localFile = new File(path + fileName);
                            // 写文件到本地
                            try {
                                file.transferTo(localFile);
                            } catch (IllegalStateException e) {
                                logger.info(e.getMessage());
                                error += orignalFilename;
                                error += "上传失败：文件错误，不能上传到服务器";
                                break;
                            } catch (IOException e) {
                                logger.info(e.getMessage());
                                error += orignalFilename;
                                error += "上传失败：文件错误，不能上传到服务器";
                                break;
                            }
//							if (getAfterLastPoint(path).equalsIgnoreCase(".ilbc")) {
//								AudioUtil.convertIlbc2Amr(ConfigIni.getIniStrValue("FFMPEG_DIR", "path"), localFile, path);
//							} else if (getAfterLastPoint(path).equalsIgnoreCase(".amr")) {
//								AudioUtil.convertAmr2Ilbc(ConfigIni.getIniStrValue("FFMPEG_DIR", "path"), localFile, path);
//							} else if (getAfterLastPoint(path).equalsIgnoreCase(".mp3")) {
//								AudioUtil.convertAmrOrIlbcToMp3(ConfigIni.getIniStrValue("FFMPEG_DIR", "path"), localFile, path);
//							}
                            // 记录上传该文件后的时间
                            int finaltime = (int) System.currentTimeMillis();
                            int min = finaltime - pre;
                            logger.info(fileName + "耗时 : " + min);
                            //插入数据库中
                            CoreAttachment coreAttachment = new CoreAttachment();
                            coreAttachment.setCratmUuid(uuid);
                            coreAttachment.setCratmCdate(new Date());
                            coreAttachment.setCratmFileName(fileName);
                            coreAttachment.setCratmStatus(1);
                            coreAttachment.setCratmDir(cratmDir);
                            coreAttachment.setCratmExtension(getAfterLastPoint(fileName));
                            coreAttachment.setCratmType(cratmType);
                            coreAttachmentService.insertCoreAttachment(coreAttachment);
                        } else {
                            error += orignalFilename;
                            error += "上传失败：音频大小请保持在10M以内";
                            break;
                        }
                    }
                    if (cratmType == 3) { //视频
                        if (file.getSize() <= 52428800) { //大小超过50M的不上传
                            uuids += uuid;
                            uuids += "|";
                            String flvFileName = delAfterLastPoint(fileName) + ".flv";
                            String oldPath = ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + cratmDir + "/" + fileName;
                            String newPath = ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + cratmDir + "/" + flvFileName;
                            String picPath = ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + cratmDir + "/pic/" + delAfterLastPoint(fileName) + ".jpg"; // 截图路径
                            IOUtil.createFile(oldPath);
                            File localFile = new File(oldPath);
                            // 写文件到本地
                            try {
                                file.transferTo(localFile);
                            } catch (IllegalStateException e) {
                                logger.info(e.getMessage());
                                error += orignalFilename;
                                error += "上传失败：文件错误，不能上传到服务器";
                                break;
                            } catch (IOException e) {
                                logger.info(e.getMessage());
                                error += orignalFilename;
                                error += "上传失败：文件错误，不能上传到服务器";
                                break;
                            }
                            // 转码
                            try {
                                MediaUtil.executeCodecs(ConfigIni.getIniStrValue("FFMPEG_DIR", "path"), oldPath, newPath, picPath);
                            } catch (Exception e) {
                                logger.info("转码失败：" + e.getMessage());
                                error += orignalFilename;
                                error += "上传失败：转码失败";
                            }
                            // 记录上传该文件后的时间
                            int finaltime = (int) System.currentTimeMillis();
                            int min = finaltime - pre;
                            logger.info(fileName + "耗时 : " + min);
                            //插入数据库中
                            CoreAttachment coreAttachment = new CoreAttachment();
                            coreAttachment.setCratmUuid(uuid);
                            coreAttachment.setCratmCdate(new Date());
                            coreAttachment.setCratmFileName(fileName);
                            coreAttachment.setCratmStatus(1);
                            coreAttachment.setCratmDir(cratmDir);
                            coreAttachment.setCratmExtension(getAfterLastPoint(fileName));
                            coreAttachment.setCratmType(cratmType);
                            coreAttachmentService.insertCoreAttachment(coreAttachment);
                        } else {
                            error += orignalFilename;
                            error += "上传失败：视频大小请保持在50M以内";
                            break;
                        }
                    }
                    if (cratmType == 4) { //其他
                        if (file.getSize() <= 31457280) { //大小超过30M的不上传
                            uuids += uuid;
                            uuids += "|";
                            String path = ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + cratmDir + "/" + fileName;
                            IOUtil.createFile(path);
                            File localFile = new File(path);
                            // 写文件到本地
                            try {
                                file.transferTo(localFile);
                            } catch (IllegalStateException e) {
                                logger.info(e.getMessage());
                                error += orignalFilename;
                                error += "上传失败：文件错误，不能上传到服务器";
                                break;
                            } catch (IOException e) {
                                logger.info(e.getMessage());
                                error += orignalFilename;
                                error += "上传失败：文件错误，不能上传到服务器";
                                break;
                            }
                            // 记录上传该文件后的时间
                            int finaltime = (int) System.currentTimeMillis();
                            int min = finaltime - pre;
                            logger.info(fileName + "耗时 : " + min);
                            //插入数据库中
                            CoreAttachment coreAttachment = new CoreAttachment();
                            coreAttachment.setCratmUuid(uuid);
                            coreAttachment.setCratmCdate(new Date());
                            coreAttachment.setCratmFileName(fileName);
                            coreAttachment.setCratmStatus(1);
                            coreAttachment.setCratmDir(cratmDir);
                            coreAttachment.setCratmExtension(getAfterLastPoint(fileName));
                            coreAttachment.setCratmType(cratmType);
                            coreAttachmentService.insertCoreAttachment(coreAttachment);
                        } else {
                            error += orignalFilename;
                            error += "上传失败：附件大小请保持在30M以内";
                            break;
                        }
                    }
                }
            }
        }
        if (!("").equals(error)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, error), response);
            logger.info("[CoreAttachmentController.upload]:end upload.");
            return;
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "上传附件成功", uuids), response);
        logger.info("[CoreAttachmentController.upload]:end upload.");
    }

    /**
     * base64流上传附件（包括文件、图片、压缩包、音频、视频）
     *
     * @param cratmType 类型：1图片2音频3视频4其它
     * @param cratmDir  目录,例a/b
     * @param imgData   base64流
     * @param response
     * @return
     */
    @RequestMapping(value = "/stream/upload", method = RequestMethod.POST)
    public void uploadStream(Integer cratmType, String cratmDir, String imgData, HttpServletResponse response) {
        logger.info("[CoreAttachmentController.uploadStream]:begin uploadStream.");
        if (StringUtils.isBlank(imgData)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少图像Base64流！"), response);
            return;
        }
        if (StringUtils.isBlank(cratmDir)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少上级目录！"), response);
            return;
        }
        String uuid = RandomUtil.generateString(16);
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            String[] strList = imgData.split(";");
            if (strList.length == 2) {
                imgData = strList[1].substring(7, strList[1].length());
            }
            byte[] b = decoder.decodeBuffer(imgData); // Base64解码
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) { // 调整异常数据
                    b[i] += 256;
                }
            }
            String name = RandomUtil.generateString(20) + ".png"; //带随机数的新文件名
            String path = ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + cratmDir + "/" + name;

            IOUtil.createFile(path);
            out = new FileOutputStream(path); // 对字节数组字符串进行Base64解码
            out.write(b);
            out.flush();
            out.close();

            //插入数据库中
            CoreAttachment coreAttachment = new CoreAttachment();
            coreAttachment.setCratmUuid(uuid);
            coreAttachment.setCratmCdate(new Date());
            coreAttachment.setCratmFileName(name);
            coreAttachment.setCratmStatus(1);
            coreAttachment.setCratmDir(cratmDir);
            coreAttachment.setCratmExtension(getAfterLastPoint(name));
            coreAttachment.setCratmHeight(null);
            coreAttachment.setCratmType(cratmType);
            coreAttachment.setCratmWidth(null);
            coreAttachmentService.insertCoreAttachment(coreAttachment);
        } catch (IOException e) {
            logger.info(e.getMessage());
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "上传附件失败！"), response);
            return;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.info(e.getMessage());
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "上传附件失败！"), response);
                return;
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "上传附件成功！", uuid), response);
        logger.info("[CoreAttachmentController.uploadStream]:end uploadStream.");
    }

    /**
     * 修改时调用,上传头像等单个图片，即一个业务对应一个图片
     *
     * @param cratmDir     目录,例a/b
     * @param cratmBusUuid 业务UUID
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/upload/update", method = RequestMethod.POST)
    public void uploadUpdate(String cratmDir, String cratmBusUuid, HttpServletRequest request, HttpServletResponse response) {
        logger.info("[CoreAttachmentController.uploadUpdate]:begin uploadUpdate.");
        if (StringUtils.isBlank(cratmBusUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少业务UUID！"), response);
            return;
        }
        if (StringUtils.isBlank(cratmDir)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少上级目录！"), response);
            return;
        }
        String error = "";
        // 解析器解析request的上下文/创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            // 再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    if (file.isEmpty()) {
                        writeAjaxJSONResponse(ResultMessageBuilder.build(false,
                                -1, "文件为空，不能上传"), response);
                        logger.info("[CoreAttachmentController.uploadUpdate]:end uploadUpdate.");
                        return;
                    }
                    // 记录上传过程起始时的时间，用来计算上传时间
                    int pre = (int) System.currentTimeMillis();
                    String orignalFilename = file.getOriginalFilename(); // 原文件名,带后缀
                    String fileName = delAfterLastPoint(orignalFilename)
                            + RandomUtil.generateString(5)
                            + getAfterLastPoint(orignalFilename); // 带随机数的新文件名
                    Matcher m = BaseConstant.P.matcher(fileName);
                    if (!m.matches()) {
                        error += orignalFilename;
                        error += "上传失败：只支持JPG或JPEG、BMP、PNG格式的图片文件";
                    }
                    if (file.getSize() <= 3145728) { // 大小超过3M的不上传
                        // 获得上传图片的宽度
                        int width = getPicWidth(file);
                        int height = getPicHeight(file);
                        if (width == -1 || width == 0) {
                            error += orignalFilename;
                            error += "上传失败：图片损坏，不能上传";
                        }
                        String path = ConfigIni.getIniStrValue(
                                "ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR)
                                + cratmDir
                                + "/"
                                + fileName;
                        IOUtil.createFile(path);
                        File localFile = new File(path);
                        // 写文件到本地
                        try {
                            file.transferTo(localFile);
                        } catch (IllegalStateException e) {
                            logger.info(e.getMessage());
                            error += orignalFilename;
                            error += "上传失败：文件错误，不能上传到服务器";
                        } catch (IOException e) {
                            logger.info(e.getMessage());
                            error += orignalFilename;
                            error += "上传失败：文件错误，不能上传到服务器";
                        }
                        // 记录上传该文件后的时间
                        int finaltime = (int) System.currentTimeMillis();
                        int min = finaltime - pre;
                        logger.info(fileName + "耗时 : " + min);
                        // 修改数据库
                        CoreAttachment coreAttachment = new CoreAttachment();
                        String uuid = RandomUtil.generateString(16);
                        coreAttachment.setCratmUuid(uuid);
                        coreAttachment.setCratmBusUuid(cratmBusUuid);
                        coreAttachment.setCratmFileName(fileName);
                        coreAttachment.setCratmStatus(1);
                        coreAttachment.setCratmDir(cratmDir);
                        coreAttachment.setCratmExtension(getAfterLastPoint(fileName));
                        coreAttachment.setCratmHeight(height);
                        coreAttachment.setCratmType(1);
                        coreAttachment.setCratmWidth(width);
                        coreAttachmentService.updateCoreAttachmentByBus(coreAttachment);
                    } else {
                        error += orignalFilename;
                        error += "上传失败：图片大小请保持在3M以内";
                    }
                }
            }
        }
        if (!("").equals(error)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, error), response);
            logger.info("[CoreAttachmentController.uploadUpdate]:end uploadUpdate.");
            return;
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "上传附件成功"), response);
        logger.info("[CoreAttachmentController.uploadUpdate]:end uploadUpdate.");
    }

    /**
     * 修改时调用,base64流上传头像等单个图片，即一个业务对应一个图片
     *
     * @param fileName     文件名,带后缀
     * @param cratmDir     目录,例a/b
     * @param imgData      base64流
     * @param cratmBusUuid 业务UUID
     * @param response
     * @return
     */
    @RequestMapping(value = "/upload/update/stream", method = RequestMethod.POST)
    public void uploadUpdateStream(String cratmDir, String fileName, String imgData, String cratmBusUuid, HttpServletResponse response) {
        logger.info("[CoreAttachmentController.uploadUpdateStream]:begin uploadUpdateStream.");
        String error = "";
        if (StringUtils.isBlank(cratmBusUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少业务UUID！"), response);
            return;
        }
        if (StringUtils.isBlank(fileName)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少带后缀的文件名！"), response);
            return;
        }
        if (StringUtils.isBlank(imgData)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少图像Base64流！"), response);
            return;
        }
        if (StringUtils.isBlank(cratmDir)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少上级目录！"), response);
            return;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            int pre = (int) System.currentTimeMillis();
            String[] strList = imgData.split(";");
            if (strList.length == 2) {
                imgData = strList[1].substring(7, strList[1].length());
            }
            byte[] b = decoder.decodeBuffer(imgData); // Base64解码
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) { // 调整异常数据
                    b[i] += 256;
                }
            }
            String name = delAfterLastPoint(fileName)
                    + RandomUtil.generateString(5)
                    + getAfterLastPoint(fileName); // 带随机数的新文件名
            String path = ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR)
                    + cratmDir + "/" + name;
            IOUtil.createFile(path);
            out = new FileOutputStream(path); // 对字节数组字符串进行Base64解码
            out.write(b);
            out.flush();
            out.close();
            File file = new File(path);
            Matcher m = BaseConstant.P.matcher(name);
            if (!m.matches()) {
                error += fileName;
                error += "上传失败：只支持JPG或JPEG、BMP、PNG格式的图片文件";
                writeAjaxJSONResponse(
                        ResultMessageBuilder.build(false, -1, error), response);
                logger.info("[CoreAttachmentController.uploadUpdateStream]:end uploadUpdateStream.");
                return;
            }
            if (file.length() <= 3145728) { // 大小超过3M的不上传
                // 获得上传图片的宽度
                int width = getPicWidth(file);
                int height = getPicHeight(file);
                if (width == -1 || width == 0) {
                    error += fileName;
                    error += "上传失败：图片损坏，不能上传";
                    writeAjaxJSONResponse(
                            ResultMessageBuilder.build(false, -1, error),
                            response);
                    logger.info("[CoreAttachmentController.uploadUpdateStream]:end uploadUpdateStream.");
                    return;
                }
                // 记录上传该文件后的时间
                int finaltime = (int) System.currentTimeMillis();
                int min = finaltime - pre;
                logger.info(fileName + "耗时 : " + min);
                // 修改数据库
                CoreAttachment coreAttachment = new CoreAttachment();
                String uuid = RandomUtil.generateString(16);
                coreAttachment.setCratmUuid(uuid);
                coreAttachment.setCratmBusUuid(cratmBusUuid);
                coreAttachment.setCratmFileName(name);
                coreAttachment.setCratmStatus(1);
                coreAttachment.setCratmDir(cratmDir);
                coreAttachment.setCratmExtension(getAfterLastPoint(name));
                coreAttachment.setCratmHeight(height);
                coreAttachment.setCratmType(1);
                coreAttachment.setCratmWidth(width);
                coreAttachmentService.updateCoreAttachmentByBus(coreAttachment);
            } else {
                error += fileName;
                error += "上传失败：图片大小请保持在3M以内";
                writeAjaxJSONResponse(
                        ResultMessageBuilder.build(false, -1, error), response);
                logger.info("[CoreAttachmentController.uploadUpdateStream]:end uploadUpdateStream.");
                return;
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "上传附件失败！"), response);
            return;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.info(e.getMessage());
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "上传附件失败！"), response);
                return;
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "上传附件成功！"), response);
        logger.info("[CoreAttachmentController.uploadUpdateStream]:end uploadUpdateStream.");
    }

    /**
     * 关联附件表
     *
     * @param cratmBusUuid
     * @param uuids
     * @param response
     * @return
     */
    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public void connect(String cratmBusUuid, String uuids, HttpServletResponse response)
            throws IllegalStateException, IOException {
        logger.info("[CoreAttachmentController.connect]:begin connect.");
        if (StringUtils.isBlank(cratmBusUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少业务UUID参数！"), response);
            return;
        }
        if (StringUtils.isBlank(uuids)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少uuids参数！"), response);
            return;
        }
        CoreAttachment coreAttachment = new CoreAttachment();
        String[] uuid = uuids.split("\\|");
        for (int i = 0; i < uuid.length; i++) {
            coreAttachment = new CoreAttachment();
            coreAttachment.setCratmUuid(uuid[i]);
            coreAttachment.setCratmBusUuid(cratmBusUuid);
            coreAttachmentService.updateCoreAttachment(coreAttachment);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "添加附件成功"), response);
        logger.info("[CoreAttachmentController.connect]:end connect.");
    }

    /**
     * 根据业务UUID查询附件列表
     *
     * @param cratmBusUuid
     * @param response
     */
    @RequestMapping(value = "/find/attachement", method = RequestMethod.POST)
    public void findAttachement(String cratmBusUuid, HttpServletResponse response) {
        logger.info("[CorePostController.findAttachement]:begin findAttachement.");
        if (StringUtils.isBlank(cratmBusUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入业务UUID！"), response);
            return;
        }
        List<CoreAttachment> list = coreAttachmentService.findCoreAttachmentByCnd(cratmBusUuid);
        List<CoreAttachmentVO> voList = new ArrayList<>();
        CoreAttachmentVO vo = new CoreAttachmentVO();
        for (CoreAttachment coreAttachment : list) {
            vo = new CoreAttachmentVO();
            vo.convertPOToVO(coreAttachment);
            voList.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "查询附件列表成功", voList), response);
        logger.info("[CorePostController.findAttachement]:end findAttachement.");
    }

    /**
     * 查看附件详情
     *
     * @param cratmUuid
     * @param response
     */
    @RequestMapping(value = "/find/attachment/info", method = RequestMethod.POST)
    public void findAttachmentInfo(String cratmUuid, HttpServletResponse response) {
        logger.info("[CoreAttachmentController.findAttachmentInfo]:begin findAttachmentInfo");
        if (StringUtils.isBlank(cratmUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少cratmUuid参数！"), response);
            return;
        }
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmUuid(cratmUuid);
        coreAttachment = coreAttachmentService.getCoreAttachment(coreAttachment);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "查询附件详细信息成功！", coreAttachment), response);
        logger.info("[CoreAttachmentController.findAttachmentInfo]:end findAttachmentInfo");
    }

    /**
     * 根据业务ID删除附件
     *
     * @param cratmBusUuid
     * @param response
     */
    @RequestMapping(value = "/delete/by/busi", method = RequestMethod.POST)
    public void deleteByBusi(String cratmBusUuid, HttpServletResponse response) {
        logger.info("[CoreAttachmentController.deleteByBusi]:begin deleteByBusi");
        if (StringUtils.isBlank(cratmBusUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少业务UUID参数！"), response);
            return;
        }
        List<CoreAttachment> list = coreAttachmentService.findCoreAttachmentByCnd(cratmBusUuid);
        //删附件数据
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmBusUuid(cratmBusUuid);
        coreAttachmentService.deleteCoreAttachmentByBusi(coreAttachment);
        //删附件文件
        for (CoreAttachment model : list) {
            if (model.getCratmFileName() != null && !("").equals(model.getCratmFileName())) {
                IOUtil.deleteFile(ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + model.getCratmDir() + "/" + model.getCratmFileName()); //删除服务器上文件
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除附件成功！"), response);
        logger.info("[CoreAttachmentController.deleteByBusi]:end deleteByBusi");
    }

    /**
     * 根据附件UUID删除附件
     *
     * @param cratmUuid
     * @param response
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(String cratmUuid, HttpServletResponse response) {
        logger.info("[CoreAttachmentController.delete]:begin delete");
        if (StringUtils.isBlank(cratmUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少附件UUID参数！"), response);
            return;
        }
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmUuid(cratmUuid);
        coreAttachment = coreAttachmentService.getCoreAttachment(coreAttachment);
        if (coreAttachment == null) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "该附件不存在！"), response);
            return;
        }
        coreAttachmentService.deleteCoreAttachment(coreAttachment);
        if (coreAttachment.getCratmFileName() != null && !("").equals(coreAttachment.getCratmFileName())) {
            IOUtil.deleteFile(ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + coreAttachment.getCratmDir() + "/" + coreAttachment.getCratmFileName()); //删除服务器上文件
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除附件成功！"), response);
        logger.info("[CoreAttachmentController.delete]:end delete");
    }

    /**
     * 根据附件UUID获取原图
     *
     * @param cratmUuid
     * @return
     */
    @RequestMapping(value = "/image/get/{cratmUuid}", method = RequestMethod.GET)
    public void getImage(@PathVariable String cratmUuid, HttpServletResponse response) {
        logger.info("[CoreAttachmentController.getImage]:begin getImage");
        byte[] bytes = null;
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmUuid(cratmUuid);
        coreAttachment = coreAttachmentService.getCoreAttachment(coreAttachment); // 获取
        String path = ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + coreAttachment.getCratmDir() + "/" + coreAttachment.getCratmFileName();
        InputStream in = null;
        try {
            in = new FileInputStream(new File(path));
            bytes = IOUtils.toByteArray(in); // 文件二进制码
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }

        writePicStream(response, bytes);
        logger.info("[CoreAttachmentController.getImage]:end getImage");
    }

    /**
     * 根据附件UUID获取音频
     *
     * @param cratmUuid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/audio/get/{cratmUuid}", method = RequestMethod.GET, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public byte[] getAudio(@PathVariable String cratmUuid) {
        logger.info("[CoreAttachmentController.getAudio]:begin getAudio");
        byte[] bytes = null;
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmUuid(cratmUuid);
        coreAttachment = coreAttachmentService.getCoreAttachment(coreAttachment); // 获取
        String path = ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + coreAttachment.getCratmDir() + "/" + coreAttachment.getCratmFileName();
        InputStream in = null;
        try {
            in = new FileInputStream(new File(path));
            bytes = IOUtils.toByteArray(in); // 文件二进制码
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }
        logger.info("[CoreAttachmentController.getAudio]:end getAudio");
        return bytes;
    }

    /**
     * 根据附件UUID获取原图
     *
     * @param cratmUuid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/image/get/other/{cratmUuid}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getOtherImage(@PathVariable String cratmUuid) {
        logger.info("[CoreAttachmentController.getOtherImage]:begin getOtherImage");
        byte[] bytes = null;
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmUuid(cratmUuid);
        coreAttachment = coreAttachmentService.getCoreAttachment(coreAttachment); // 获取
        String path = ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + coreAttachment.getCratmDir() + "/" + coreAttachment.getCratmFileName();
        InputStream in = null;
        try {
            in = new FileInputStream(new File(path));
            bytes = IOUtils.toByteArray(in); // 文件二进制码
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }
        logger.info("[CoreAttachmentController.getOtherImage]:end getOtherImage");
        return bytes;
    }

    /**
     * 根据附件UUID获取附件base64流
     *
     * @param cratmUuid
     * @param response
     * @return
     */
    @RequestMapping(value = "/get/stream", method = RequestMethod.POST)
    public void getStream(String cratmUuid, HttpServletResponse response) {
        logger.info("[CoreAttachmentController.getStream]:begin getStream");
        if (StringUtils.isBlank(cratmUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少附件UUID参数！"), response);
            return;
        }
        byte[] bytes = null;
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmUuid(cratmUuid);
        coreAttachment = coreAttachmentService.getCoreAttachment(coreAttachment); // 获取
        String path = ConfigIni.getIniStrValue("ATTACHMENT_DIR", "path", BaseConstant.DEFAULT_ATTACHMENT_DIR) + coreAttachment.getCratmDir() + "/" + coreAttachment.getCratmFileName();
        String strBase64 = "";
        InputStream in = null;
        try {
            in = new FileInputStream(new File(path));
            bytes = IOUtils.toByteArray(in); // 文件二进制码
            strBase64 = new BASE64Encoder().encode(bytes);      //将字节流数组转换为字符串
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取附件base64流成功！", strBase64), response);
        logger.info("[CoreAttachmentController.getStream]:end getStream");
    }

    /**
     * 获取一个文件名的后缀名（最后一个点后面的字符串）,带上点
     *
     * @param fileName
     * @return
     */
    public String getAfterLastPoint(String fileName) {
        int last = fileName.lastIndexOf(".");
        if (last <= 0) {
            return "";
        }
        return fileName.substring(last, fileName.length());
    }

    /**
     * 去除一个文件的后缀名（去除最后一个点后面的字符串）,获取文件名
     *
     * @param fileName
     * @return
     */
    public String delAfterLastPoint(String fileName) {
        int last = fileName.lastIndexOf(".");
        if (last <= 0) {
            return "";
        }
        return fileName.substring(0, last);
    }

    /**
     * 重新生成图像
     *
     * @param file
     * @param width
     * @param height
     * @param out
     */
    private void changeImage(MultipartFile file, int width, int height, OutputStream out) {
        try {
            Image img = ImageIO.read(file.getInputStream());
            int newWidth;
            int newHeight;
            if (img.getHeight(null) < height && img.getWidth(null) < width) {
                newWidth = img.getWidth(null);
                newHeight = img.getHeight(null);
            } else {
                newWidth = width;
                newHeight = img.getHeight(null) < height ? img.getHeight(null) : height;
            }
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();
        } catch (Exception e) {
            logger.error("unknown exception", e);
        }
    }

    /**
     * 重新生成图像
     *
     * @param file
     * @param width
     * @param height
     * @param out
     */
    private void changeImage(File file, int width, int height, OutputStream out) {
        try {
            Image img = ImageIO.read(file);
            int newWidth;
            int newHeight;
            if (img.getHeight(null) < height && img.getWidth(null) < width) {
                newWidth = img.getWidth(null);
                newHeight = img.getHeight(null);
            } else {
                newWidth = width;
                newHeight = img.getHeight(null) < height ? img.getHeight(null) : height;
            }
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();
        } catch (Exception e) {
            logger.error("unknown exception", e);
        }
    }

    /**
     * 获得上传图片文件的高度
     *
     * @param file
     * @return
     */
    private int getPicHeight(MultipartFile file) {
        int picHeight = 0;
        try {
            Image img = ImageIO.read(file.getInputStream());
            picHeight = img.getHeight(null);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return picHeight;
    }

    /**
     * 获得上传图片文件的宽度
     *
     * @param file
     * @return
     */
    private int getPicWidth(MultipartFile file) {
        int picWidth = 0;
        try {
            Image img = ImageIO.read(file.getInputStream());
            picWidth = img.getWidth(null);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return picWidth;
    }

    /**
     * 获得上传图片文件的高度
     *
     * @param file
     * @return
     */
    private int getPicHeight(File file) {
        int picHeight = 0;
        try {
            Image img = ImageIO.read(file);
            picHeight = img.getHeight(null);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return picHeight;
    }

    /**
     * 获得上传图片文件的宽度
     *
     * @param file
     * @return
     */
    private int getPicWidth(File file) {
        int picWidth = 0;
        try {
            Image img = ImageIO.read(file);
            picWidth = img.getWidth(null);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return picWidth;
    }

}