package com.xiaoyu.lingdian.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaoyu.lingdian.entity.weixin.CheckModel;
import com.xiaoyu.lingdian.entity.weixin.ShareConfig;
import com.xiaoyu.lingdian.service.CoreWechatService;
import com.xiaoyu.lingdian.service.TokenService;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.tool.wx.WeixinUtil;

@Controller
@RequestMapping("/weixin")
public class TokenController extends BaseController {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private CoreWechatService coreWechatService;
	
	/**
	 * 获取AccessToken
	 * 
	 * @param crwctUuid 公众号标识
	 * @return
	 */
	@RequestMapping(value = "/getAccessToken", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getAccessToken(String crwctUuid) {
		if (StringUtil.isEmpty(crwctUuid)) {
			return "公众号标识必传";
		}
		
		return coreWechatService.getAccessToken(crwctUuid);
	}
	
	/**
	 * 开发者模式token校验
	 * 
	 * @param wxToken 开发者url后缀
	 * @param tokenModel
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/{wxToken}", method = RequestMethod.GET, produces = "text/plain")
	public @ResponseBody String validate(@PathVariable("wxToken")String wxToken, CheckModel tokenModel) throws ParseException, IOException {
		return WeixinUtil.validate(wxToken, tokenModel);
	}

	/**
	 * 获取JS-SDK配置
	 * 
	 * @param crwctUuid 公众号标识
	 * @param urlString URL路径
	 * @return
	 */
	@RequestMapping(value = "/share/config", method = RequestMethod.POST)
	public void getShareConfig(String crwctUuid, String urlString, HttpServletRequest request, HttpServletResponse response) {
		logger.info("[TokenController.getShareConfig]:start getShareConfig.");	
		if (StringUtil.isEmpty(crwctUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "公众号标识必传"), response);
			logger.info("[TokenController.getShareConfig]:end getShareConfig.");
			return;
		}
		if (StringUtil.isEmpty(urlString)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "URL路径必传"), response);
			logger.info("[TokenController.getShareConfig]:end getShareConfig.");
			return;
		}	
		
		ShareConfig shareConfig = this.coreWechatService.getShareConfig(crwctUuid, urlString);
		logger.info("urlString:" + urlString + ";signature:" + shareConfig.getSignature());

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取JS-SDK配置成功！", shareConfig), response);
        logger.info("[TokenController.getShareConfig]:end getShareConfig.");
	}

}