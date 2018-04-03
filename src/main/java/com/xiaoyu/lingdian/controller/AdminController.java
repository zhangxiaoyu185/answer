package com.xiaoyu.lingdian.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.xiaoyu.lingdian.entity.CoreAccount;
import com.xiaoyu.lingdian.entity.CoreFunction;
import com.xiaoyu.lingdian.entity.CoreRole;
import com.xiaoyu.lingdian.service.CoreAccountService;
import com.xiaoyu.lingdian.service.CoreFunctionService;
import com.xiaoyu.lingdian.service.CoreRoleService;
import com.xiaoyu.lingdian.tool.encrypt.MD5Util;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreAccountVO;

@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {

	/**
	 * 用户表
	 */
	@Autowired
	private CoreAccountService coreAccountService;
	
	/**
	 * 角色表
	 */
	@Autowired
	private CoreRoleService coreRoleService;
	
	/**
	 * 功能菜单表
	 */
	@Autowired
	private CoreFunctionService coreFunctionService;
	
	/**
	 * 登录
	 * 
	 * @param USERCODE  工号或用户名
	 * @param PWD 密码
	 * @param response
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(String USERCODE, String PWD, HttpServletResponse response) {
		logger.info("login start");
		if (StringUtil.isEmpty(USERCODE)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[USERCODE]不能为空!"), response);
			return;
		}
		if (StringUtil.isEmpty(PWD)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[PWD]不能为空!"), response);
			return;
		}
		CoreAccount coreAccount = new CoreAccount();
		coreAccount.setCractName(USERCODE);
		CoreAccount account = coreAccountService.getCoreAccount(coreAccount);
		String md5PWD=MD5Util.md5(PWD);
		if (account == null || !(md5PWD).equalsIgnoreCase(account.getCractPassword())) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "用户或密码错误！"), response);
			logger.info("login end");
			return;
		}
		CoreAccountVO coreAccountVO = new CoreAccountVO();
		coreAccountVO.convertPOToVO(account);
		List<CoreFunction> functionList=new ArrayList<CoreFunction>();
		// 返回菜单
		if (account.getCractRoles() != null) {
			String[] roles = account.getCractRoles().split("\\|");
			CoreRole coreRole;
			CoreFunction coreFunction;
			for (int i = 0; i < roles.length; i++) { // 获取角色
				// 根据角色查询功能菜单
				coreRole = new CoreRole();
				coreRole.setCrrolUuid((roles[i]));
				CoreRole role = coreRoleService.getCoreRole(coreRole);
				if (role != null && role.getCrrolFuns() != null) {
					String[] menus = role.getCrrolFuns().split("\\|");
					for (int y = 0; y < menus.length; y++) { // 获取权限
						coreFunction = new CoreFunction();
						coreFunction.setCrfntUuid(menus[y]);
						coreFunction.setCrfntStatus(1);
						CoreFunction function = coreFunctionService.getCoreFunction(coreFunction);
						if (function != null) {
							functionList.add(function);
						}
					}
				}
			}
		}
		coreAccountVO.setCoreFunctions(functionList);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "登录成功!", coreAccountVO), response);
		logger.info("login end");
	}
	
}