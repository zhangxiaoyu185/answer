package com.xiaoyu.lingdian.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreAccount;
import com.xiaoyu.lingdian.entity.CoreRole;
import com.xiaoyu.lingdian.service.CoreAccountService;
import com.xiaoyu.lingdian.service.CoreRoleService;
import com.xiaoyu.lingdian.tool.encrypt.MD5Util;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreAccountVO;

@Controller
@RequestMapping(value="/coreAccount")
public class CoreAccountController extends BaseController {
	
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
	 * 新增账户
	 * 
	 * @param cractName
	 * @param cractRoles
	 * @param cractTel
	 * @param cractEmail
	 * @param cractRemarks
	 * @param response
	 */
	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
	public void addAccount(String cractName, String cractPassword, String cractRoles, String cractTel, String cractEmail,
			String cractRemarks, HttpServletResponse response) {
		logger.info("addAccount start");
		if (StringUtil.isEmpty(cractName)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[帐户名称]不能为空!"), response);
			return;
		}
		
		if(StringUtil.isEmpty(cractPassword)){
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[账户密码]不能为空!"), response);
			return;
		}
		
		if(StringUtil.isEmpty(cractRoles)){
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[账户角色]不能为空!"), response);
			return;
		}		
		CoreAccount coreAccount=new CoreAccount();
		coreAccount.setCractName(cractName);
		CoreAccount account =coreAccountService.getCoreAccount(coreAccount);
		if(account!=null){
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "此账户已存在!"), response);
			return;
		}
		coreAccount.setCractUuid(RandomUtil.generateString(16));
		coreAccount.setCractRoles(cractRoles);		
		String md5PWD = MD5Util.md5(cractPassword);
		coreAccount.setCractPassword(md5PWD);
		coreAccount.setCractStatus(1);
		coreAccount.setCractTel(cractTel);
		coreAccount.setCractEmail(cractEmail);
		coreAccount.setCractRemarks(cractRemarks);		
		coreAccount.setCractCdate(new Date());
		coreAccount.setCractUdate(new Date());
		coreAccountService.insertCoreAccount(coreAccount);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增账户成功!"),response);
		logger.info("addAccount end");
	}
	
	/**
	 * 删除账户
	 * 
	 * @param cractUuid
	 * @param response
	 */
	@RequestMapping(value = "/delete/one", method = RequestMethod.POST)
	public void deleteOne(String cractUuid, HttpServletResponse response) {
		logger.info("deleteOne start"); 
		if (StringUtil.isEmpty(cractUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[账户UUID]不能为空!"), response);
			return;
		}
		CoreAccount coreAccount = new CoreAccount();
		coreAccount.setCractUuid(cractUuid);
		coreAccount = coreAccountService.getCoreAccount(coreAccount);
		if (coreAccount != null) {
			coreAccountService.deleteCoreAccount(coreAccount);
		}
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除人员成功!"),response);
		logger.info("deleteOne end"); 
	}
	
	/**
	 * 批量删除
	 * 
	 * @param cractUuids
	 * @param response
	 */
	@RequestMapping(value="/delete/batch", method=RequestMethod.POST)
	public void deleteBatch(String cractUuids, HttpServletResponse response) {
		logger.info("deleteBatch begin");
		if (StringUtils.isBlank(cractUuids)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
			return;
		}
		String[] array = cractUuids.split("\\|");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		if (list.size() == 0) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
			return;
		}
		coreAccountService.deleteCoreAccountByCnd(list);		
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功！"), response);
		logger.info("deleteBatch end ");
	}
	
	/**
	 * 重置密码
	 * 
	 * @param cractUuid
	 * @param oldPwd
	 * @param response
	 */
	@RequestMapping(value = "/reset/pwd", method = RequestMethod.POST)
	public void resetPwd(String cractUuid, String oldPwd, HttpServletResponse response) {
		logger.info("[CoreAccountController.resetPwd]:begin resetPwd.");
		if (StringUtils.isBlank(cractUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入用户UUID！"), response);
			return;
		}		
		if (StringUtils.isBlank(oldPwd)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入旧密码！"), response);
			return;
		}		
		CoreAccount coreAccount = new CoreAccount();
		coreAccount.setCractUuid(cractUuid);
		coreAccount = coreAccountService.getCoreAccount(coreAccount);		
		if(coreAccount == null || !coreAccount.getCractPassword().equals(oldPwd)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "原密码错误！"), response);
			return;
		}
		String newMd5PWD = MD5Util.md5("123456");
		coreAccount.setCractPassword(newMd5PWD);
		coreAccount.setCractUdate(new Date());
		coreAccountService.updateCoreAccount(coreAccount);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "重置密码成功！"), response);
		logger.info("[CoreAccountController.resetPwd]:end resetPwd.");
	}
	
	/**
	 * 修改密码
	 * 
	 * @param cractUuid
	 * @param oldPwd
	 * @param newPwd
	 * @param confirmPwd
	 * @param response
	 */
	@RequestMapping(value = "/update/pwd", method = RequestMethod.POST)
	public void updatePwd(String cractUuid, String oldPwd, String newPwd, String confirmPwd, HttpServletResponse response) {
		logger.info("[CoreAccountController.updatePwd]:begin updatePwd.");
		if (StringUtils.isBlank(cractUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入用户UUID！"), response);
			return;
		}		
		if (StringUtils.isBlank(oldPwd)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入旧密码！"), response);
			return;
		}		
		if (StringUtils.isBlank(newPwd)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入新密码！"), response);
			return;
		}		
		if (StringUtils.isBlank(confirmPwd)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请输入确认密码！"), response);
			return;
		}		
		if (!newPwd.equals(confirmPwd)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "密码输入不一致！"), response);
			return;
		}		
		CoreAccount coreAccount = new CoreAccount();
		coreAccount.setCractUuid(cractUuid);
		coreAccount = coreAccountService.getCoreAccount(coreAccount);		
		String oldMd5PWD = MD5Util.md5(oldPwd);
		if(coreAccount == null || !coreAccount.getCractPassword().equalsIgnoreCase(oldMd5PWD)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "原密码错误！"), response);
			return;
		}
		String newMd5PWD = MD5Util.md5(newPwd);
		coreAccount.setCractPassword(newMd5PWD);
		coreAccount.setCractUdate(new Date());
		coreAccountService.updateCoreAccount(coreAccount);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改密码成功！"), response);
		logger.info("[CoreAccountController.updatePwd]:end updatePwd.");
	}
	
	/**
	 * 修改账户
	 * 
	 * @param cractUuid
	 * @param cractRoles
	 * @param cractTel
	 * @param cractEmail
	 * @param cractRemarks
	 * @param response
	 */
	@RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
	public void updateAccount(String cractUuid, String cractRoles, String cractTel, String cractEmail, String cractRemarks,
			HttpServletResponse response) {
		logger.info("updateAccount start");
		if (StringUtil.isEmpty(cractUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[账户UUID]不能为空!"), response);
			return;
		}
		if (StringUtil.isEmpty(cractRoles)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[角色]不能为空!"), response);
			return;
		}
		CoreAccount coreAccount = new CoreAccount();
		coreAccount.setCractUuid(cractUuid);
		coreAccount.setCractRoles(cractRoles);
		coreAccount.setCractTel(cractTel);
		coreAccount.setCractEmail(cractEmail);
		coreAccount.setCractRemarks(cractRemarks);	
		coreAccountService.updateCoreAccount(coreAccount);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "账户修改成功!"),response);
		logger.info("updateAccount end");
	}

	/**
	 * 根据cractUuid获取账户信息
	 * 
	 * @param cractUuid
	 * @param response
	 */
	@RequestMapping(value = "/getAccountByUNID", method = RequestMethod.POST)
	public void getAccountByUNID(String cractUuid, HttpServletResponse response) {
		logger.info("getAccountByUNID start");
		if (StringUtil.isEmpty(cractUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[账户UNID]不能为空!"), response);
			return;
		}
		CoreAccount coreAccount=new CoreAccount();
		coreAccount.setCractUuid(cractUuid);
		CoreAccount account = coreAccountService.getCoreAccount(coreAccount);
		if(account!=null){
			CoreAccountVO coreAccountVO = new CoreAccountVO();
			String role = account.getCractRoles();
			String [] roles=role.split("\\|");
			List<String> ids=new ArrayList<String>();
			Collections.addAll(ids, roles);
			List<CoreRole> coreRoles =coreRoleService.findCoreRoleByIds(ids);
			if(coreRoles.size()>0){
				StringBuffer rolesName=new StringBuffer();
				for (CoreRole coreRole : coreRoles) {
					rolesName.append(coreRole.getCrrolName()+"|");
				}
				coreAccountVO.setCractRolesName(rolesName.toString());
			}else{
				coreAccountVO.setCractRolesName("");
			}
			coreAccountVO.convertPOToVO(account);
			writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "查询单个账户信息成功!", coreAccountVO),response);
		}else{
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "查询单个账户信息失败!"),response);
		}
		logger.info("getAccountByUNID end");
	}

	/**
	 * 获取账户列表信息
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/getCoreAccounts", method = RequestMethod.POST)
	public void getCoreAccounts(HttpServletResponse response) {
		logger.info("getCoreAccounts start");
		List<CoreAccountVO> vos=new ArrayList<CoreAccountVO>();
		List<CoreAccount> list = coreAccountService.getCoreAccounts();
		if(list.size()>0){
			CoreAccountVO coreAccountVO;
			for (CoreAccount coreAccount : list) {
				coreAccountVO = new CoreAccountVO();
				String role=coreAccount.getCractRoles();
				String [] roles=role.split("\\|");
				List<String> ids=new ArrayList<String>();
				Collections.addAll(ids, roles);
				List<CoreRole> coreRoles =coreRoleService.findCoreRoleByIds(ids);
				if(coreRoles.size()>0){
					StringBuffer rolesName=new StringBuffer();
					for (CoreRole coreRole : coreRoles) {
						rolesName.append(coreRole.getCrrolName()+"|");
					}
					coreAccountVO.setCractRolesName(rolesName.toString());
				}else{
					coreAccountVO.setCractRolesName("");
				}
				coreAccountVO.convertPOToVO(coreAccount);
				vos.add(coreAccountVO);
			}
			writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "查询账户信息列表成功!", vos),response);
		}else{
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "查询账户信息列表失败!"),response);
		}
		logger.info("getCoreAccounts end");
	}
	
	/**
	 * 账户信息分页列表
	 * 
	 * @param pageNum 页码
	 * @param pageSize 页数
	 * @param response
	 */
	@RequestMapping(value = "/accountPage", method = RequestMethod.POST)
	public void accountPage(Integer pageNum, Integer pageSize, HttpServletResponse response) {
		logger.info("accountPage start");
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		Page<CoreAccount> page = coreAccountService.findCoreAccountPage(pageNum, pageSize);
		Page<CoreAccountVO> pageVO = new Page<CoreAccountVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());		
		List<CoreAccountVO> vos = new ArrayList<CoreAccountVO>();
		CoreAccountVO vo;
		for (CoreAccount coreAccount : page.getResult()) {
			vo = new CoreAccountVO();
			vo.convertPOToVO(coreAccount);			
			String role=coreAccount.getCractRoles();
			String [] roles=role.split("\\|");
			List<String> ids=new ArrayList<String>();
			Collections.addAll(ids, roles);
			List<CoreRole> coreRoles =coreRoleService.findCoreRoleByIds(ids);
			if(coreRoles.size()>0){
				StringBuffer rolesName=new StringBuffer();
				for (CoreRole coreRole : coreRoles) {
					rolesName.append(coreRole.getCrrolName()+"|");
				}
				vo.setCractRolesName(rolesName.toString());
			}else{
				vo.setCractRolesName("");
			}		
			vos.add(vo);
		}
		pageVO.setResult(vos);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取账户信息分页列表成功！", pageVO), response);
		logger.info("accountPage end");
	}

}