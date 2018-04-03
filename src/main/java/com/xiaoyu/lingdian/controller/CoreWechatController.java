package com.xiaoyu.lingdian.controller;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import javax.servlet.http.HttpServletResponse;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import org.springframework.stereotype.Controller;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import com.xiaoyu.lingdian.entity.CoreWechat;
import com.xiaoyu.lingdian.service.CoreWechatService;
import com.xiaoyu.lingdian.vo.CoreWechatVO;

@Controller
@RequestMapping(value="/coreWechat")
public class CoreWechatController extends BaseController {

	/**
	* 公众号表
	*/
	@Autowired
	private CoreWechatService coreWechatService;
	
	/**
	 * 根据微信号UUID判断页面是否合法,前端调用
	 * 
	 * @param crwctUuid
	 * @param response
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public void validate(String crwctUuid, HttpServletResponse response) {
		logger.info("CoreWechatController.validate start");
		if (StringUtil.isEmpty(crwctUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "页面不合法!"), response);
			logger.info("CoreWechatController.validate end");
			return;
		}

		CoreWechat coreWechat = new CoreWechat();
		coreWechat.setCrwctUuid(crwctUuid);
		coreWechat = this.coreWechatService.getCoreWechat(coreWechat);
		if(null != coreWechat){
			writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "页面合法!"),response);
		} else {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "页面不合法!"),response);
		}
		logger.info("CoreWechatController.validate end");
	}
	
	/**
	* 添加
	*
	* @param crwctName 公众号名称
	* @param crwctAppid appid
	* @param crwctAppsecret appsecret
	* @param crwctPartner 商户号partner/mch_id
	* @param crwctPartnerkey 商户密钥partnerkey
	* @param crwctNotifyurl 回调URLnotifyurl
	* @param crwctRemarks 备注
	* @return
	*/
	@RequestMapping(value="/add/coreWechat", method=RequestMethod.POST)
	public void addCoreWechat (String crwctName, String crwctAppid, String crwctAppsecret, String crwctPartner, String crwctPartnerkey, String crwctNotifyurl, String crwctRemarks, HttpServletResponse response) {
		logger.info("[CoreWechatController]:begin addCoreWechat");

		CoreWechat coreWechat = new CoreWechat();
		coreWechat.setCrwctName(crwctName);
		CoreWechat wechat = this.coreWechatService.getCoreWechatByName(coreWechat);
		if (null != wechat) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "公众号名已存在!"), response);
			logger.info("[CoreWechatController]:end addCoreWechat");
			return;
		}
		
		String uuid = RandomUtil.generateString(16);
		coreWechat.setCrwctUuid(uuid);		
		coreWechat.setCrwctAppid(crwctAppid);
		coreWechat.setCrwctAppsecret(crwctAppsecret);
		coreWechat.setCrwctAccessToken(RandomUtil.generateString(32));
		coreWechat.setCrwctAccessTime(new Date());
		coreWechat.setCrwctJsapiTicket(RandomUtil.generateString(32));
		coreWechat.setCrwctJsapiTime(new Date());
		coreWechat.setCrwctPartner(crwctPartner);
		coreWechat.setCrwctPartnerkey(crwctPartnerkey);
		coreWechat.setCrwctNotifyurl(crwctNotifyurl);
		coreWechat.setCrwctStatus(1);
		coreWechat.setCrwctCdate(new Date());
		coreWechat.setCrwctUdate(new Date());
		coreWechat.setCrwctRemarks(crwctRemarks);

		coreWechatService.insertCoreWechat(coreWechat);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"),response);
		logger.info("[CoreWechatController]:end addCoreWechat");
	}

	/**
	* 修改
	*
	* @param crwctUuid 标识UUID
	* @param crwctAppid appid
	* @param crwctAppsecret appsecret
	* @param crwctPartner 商户号partner/mch_id
	* @param crwctPartnerkey 商户密钥partnerkey
	* @param crwctNotifyurl 回调URLnotifyurl
	* @param crwctRemarks 备注
	* @return
	*/
	@RequestMapping(value="/update/coreWechat", method=RequestMethod.POST)
	public void updateCoreWechat (String crwctUuid, String crwctAppid, String crwctAppsecret, String crwctPartner, String crwctPartnerkey, String crwctNotifyurl, String crwctRemarks, HttpServletResponse response) {
		logger.info("[CoreWechatController]:begin updateCoreWechat");

		if (StringUtil.isEmpty(crwctUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
			logger.info("[CoreWechatController]:end updateCoreWechat");
			return;
		}

		CoreWechat coreWechat = new CoreWechat();
		coreWechat.setCrwctUuid(crwctUuid);
		coreWechat.setCrwctAppid(crwctAppid);
		coreWechat.setCrwctAppsecret(crwctAppsecret);
		coreWechat.setCrwctPartner(crwctPartner);
		coreWechat.setCrwctPartnerkey(crwctPartnerkey);
		coreWechat.setCrwctNotifyurl(crwctNotifyurl);
		coreWechat.setCrwctUdate(new Date());
		coreWechat.setCrwctRemarks(crwctRemarks);

		coreWechatService.updateCoreWechat(coreWechat);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"),response);
		logger.info("[CoreWechatController]:end updateCoreWechat");
	}

	/**
	* 删除
	*
	* @param crwctUuid 标识UUID
	* @return
	*/
	@RequestMapping(value="/delete/one", method=RequestMethod.POST)
	public void deleteCoreWechat (String crwctUuid, HttpServletResponse response) {
		logger.info("[CoreWechatController]:begin deleteCoreWechat");

		if (StringUtil.isEmpty(crwctUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
			logger.info("[CoreWechatController]:end deleteCoreWechat");
			return;
		}

		CoreWechat coreWechat = new CoreWechat();
		coreWechat.setCrwctUuid(crwctUuid);
		coreWechat.setCrwctStatus(0);
		coreWechatService.updateCoreWechat(coreWechat);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"),response);
		logger.info("[CoreWechatController]:end deleteCoreWechat");
	}

	/**
	* 批量删除
	*
	* @param crwctUuids UUID集合
	* @return
	*/
	@RequestMapping(value="/delete/batch", method=RequestMethod.POST)
	public void deleteBatchCoreWechat (String crwctUuids, HttpServletResponse response) {
		logger.info("[CoreWechatController]:begin deleteBatchCoreWechat");

		if (StringUtil.isEmpty(crwctUuids)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[UUID集合]不能为空!"), response);
			logger.info("[CoreWechatController]:end deleteBatchCoreWechat");
			return;
		}

		String[] uuids=crwctUuids.split("\\|");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < uuids.length; i++) {
			list.add(uuids[i]);
		}
		if (list.size() == 0) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
			logger.info("[CoreWechatController]:end deleteBatchCoreWechat");
			return;
		}

		for (String strUuid : list) {
			CoreWechat coreWechat = new CoreWechat();
			coreWechat.setCrwctUuid(strUuid);
			coreWechat.setCrwctStatus(0);
			coreWechatService.updateCoreWechat(coreWechat);
		}

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"),response);
		logger.info("[CoreWechatController]:end deleteBatchCoreWechat");
	}

	/**
	* 获取单个
	*
	* @param crwctUuid 标识UUID
	* @return
	*/
	@RequestMapping(value="/views", method=RequestMethod.POST)
	public void viewsCoreWechat (String crwctUuid, HttpServletResponse response) {
		logger.info("[CoreWechatController]:begin viewsCoreWechat");

		if (StringUtil.isEmpty(crwctUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空!"), response);
			logger.info("[CoreWechatController]:end viewsCoreWechat");
			return;
		}

		CoreWechat coreWechat = new CoreWechat();
		coreWechat.setCrwctUuid(crwctUuid);

		coreWechat = coreWechatService.getCoreWechat(coreWechat);

		CoreWechatVO coreWechatVO = new CoreWechatVO();
		coreWechatVO.convertPOToVO(coreWechat);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreWechatVO), response);
		logger.info("[CoreWechatController]:end viewsCoreWechat");
	}

	/**
	* 获取列表<List>
	* 
	* @return
	*/
	@RequestMapping(value="/find/all", method=RequestMethod.POST)
	public void findCoreWechatList (HttpServletResponse response) {
		logger.info("[CoreWechatController]:begin findCoreWechatList");

		List<CoreWechat> lists = coreWechatService.findCoreWechatList(null);
		List<CoreWechatVO> vos = new ArrayList<CoreWechatVO>();
		CoreWechatVO vo;
		for (CoreWechat coreWechat : lists) {
			vo = new CoreWechatVO();

			vo.convertPOToVO(coreWechat);

			vos.add(vo);
		}
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos),response);
		logger.info("[CoreWechatController]:end findCoreWechatList");
	}

	/**
	* 获取列表<Page>
	* 
	* @param crwctName 公众号名称
	* @param pageNum 页码
	* @param pageSize 页数
	* @return
	*/
	@RequestMapping(value="/find/by/cnd", method=RequestMethod.POST)
	public void findCoreWechatPage (String crwctName, Integer pageNum, Integer pageSize, HttpServletResponse response) {
		logger.info("[CoreWechatController]:begin findCoreWechatPage");

		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 10;
		}
		Page<CoreWechat> page = coreWechatService.findCoreWechatPage(crwctName, pageNum, pageSize);
		Page<CoreWechatVO> pageVO = new Page<CoreWechatVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
		List<CoreWechatVO> vos = new ArrayList<CoreWechatVO>();
		CoreWechatVO vo;
		for (CoreWechat coreWechat : page.getResult()) {
			vo = new CoreWechatVO();

			vo.convertPOToVO(coreWechat);

			vos.add(vo);
		}
		pageVO.setResult(vos);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO),response);
		logger.info("[CoreWechatController]:end findCoreWechatPage");
	}
}