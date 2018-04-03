package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.tool.StringUtil;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import com.xiaoyu.lingdian.controller.BaseController;
import com.xiaoyu.lingdian.entity.CoreCacheData;
import com.xiaoyu.lingdian.service.CoreCacheDataService;
import com.xiaoyu.lingdian.vo.CoreCacheDataVO;

@Controller
@RequestMapping(value="/coreCacheData")
public class CoreCacheDataController extends BaseController {

	/**
	* 缓存数据表
	*/
	@Autowired
	private CoreCacheDataService coreCacheDataService;

	/**
	* 修改
	*
	* @param crcdaUuid 标识UUID
	* @param crcdaValue 缓存数据值
	* @return
	*/
	@RequestMapping(value="/update/coreCacheData", method=RequestMethod.POST)
	public void updateCoreCacheData (String crcdaUuid, String crcdaName, String crcdaValue, HttpServletResponse response) {
		logger.info("[CoreCacheDataController]:begin updateCoreCacheData");

		if (StringUtil.isEmpty(crcdaUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
			return;
		}

		CoreCacheData coreCacheData = new CoreCacheData();
		coreCacheData.setCrcdaUuid(crcdaUuid);
		coreCacheData.setCrcdaName(crcdaName);
		coreCacheData.setCrcdaValue(crcdaValue);

		coreCacheDataService.updateCoreCacheData(coreCacheData);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"),response);
		logger.info("[CoreCacheDataController]:end updateCoreCacheData");

	}

	/**
	* 获取单个
	*
	* @param crcdaUuid 标识UUID
	* @return
	*/
	@RequestMapping(value="/views", method=RequestMethod.POST)
	public void viewsCoreCacheData (String crcdaUuid, HttpServletResponse response) {
		logger.info("[CoreCacheDataController]:begin viewsCoreCacheData");

		if (StringUtil.isEmpty(crcdaUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
			return;
		}

		CoreCacheData coreCacheData = new CoreCacheData();
		coreCacheData.setCrcdaUuid(crcdaUuid);
		coreCacheData = coreCacheDataService.getCoreCacheData(coreCacheData);
		CoreCacheDataVO coreCacheDataVO = new CoreCacheDataVO();
		if(null != coreCacheData) {
			coreCacheDataVO.convertPOToVO(coreCacheData);
		}

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreCacheDataVO), response);
		logger.info("[CoreCacheDataController]:end viewsCoreCacheData");

	}

}