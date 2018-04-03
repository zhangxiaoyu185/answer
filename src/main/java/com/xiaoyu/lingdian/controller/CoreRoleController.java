package com.xiaoyu.lingdian.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.xiaoyu.lingdian.controller.BaseController;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreFunction;
import com.xiaoyu.lingdian.entity.CoreRole;
import com.xiaoyu.lingdian.service.CoreFunctionService;
import com.xiaoyu.lingdian.service.CoreRoleService;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreRoleVO;

@Controller
@RequestMapping(value="/coreRole")
public class CoreRoleController extends BaseController {
	
	/**
	 * 角色表
	 */
	@Autowired
	private CoreRoleService coreRoleService;
	
	/**
	 * 权限表
	 */
	@Autowired
	private CoreFunctionService coreFunctionService;

	/**
	 * 添加角色(功能菜单用"|"隔开)
	 * 
	 * @param crrolName
	 * @param crrolFuns
	 * @param crrolDesc
	 * @param response
	 */
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public void addRole(String crrolName, String crrolFuns, String crrolDesc, HttpServletResponse response) {
		logger.info("addRole start");
		if (StringUtil.isEmpty(crrolName)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[角色名称]不能为空！"), response);
			return;
		}
		if (StringUtil.isEmpty(crrolFuns)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[功能菜单集合]不能为空！"), response);
			return;
		}
		CoreRole coreRole = new CoreRole();
		coreRole.setCrrolName(crrolName);
		CoreRole role=coreRoleService.getCoreRoleByName(coreRole);
		if(role!=null){
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[角色已存在，不能新增!]"), response);
			return;
		}
		
		coreRole.setCrrolUuid(RandomUtil.generateString(16));
		coreRole.setCrrolFuns(crrolFuns);
		coreRole.setCrrolDesc(crrolDesc);
		coreRoleService.insertCoreRole(coreRole);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "添加角色成功！"), response);	
		logger.info("addRole end");
	}

	/**
	 * 修改角色(功能菜单用"|"隔开)
	 * 
	 * @param crrolUuid
	 * @param crrolName
	 * @param crrolFuns
	 * @param crrolDesc
	 * @param response
	 */
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public void updateRole(String crrolUuid, String crrolName, String crrolFuns, String crrolDesc, HttpServletResponse response) {
		logger.info("updateRole start");
		if (StringUtil.isEmpty(crrolUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[角色UUID]不能为空！"), response);
			return;
		}
		if (StringUtil.isEmpty(crrolName)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[角色名称]不能为空！"), response);
			return;
		}
		if (StringUtil.isEmpty(crrolFuns)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[功能菜单]不能为空！"), response);
			return;
		}		
		CoreRole coreRole = new CoreRole();
		coreRole.setCrrolName(crrolName);
		coreRole.setCrrolUuid(crrolUuid);
		CoreRole role=coreRoleService.getCoreRoleByName(coreRole);
		if(role!=null){
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[角色已存在，不能修改!]"), response);
			return;
		}
		
		coreRole.setCrrolUuid(crrolUuid);
		coreRole.setCrrolFuns(crrolFuns);
		coreRole.setCrrolDesc(crrolDesc);
		coreRoleService.updateCoreRole(coreRole);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改角色成功！"), response);
		logger.info("updateRole end");
	}

	/**
	 * 删除角色
	 * 
	 * @param crrolUuid
	 * @param response
	 */
	@RequestMapping(value = "/delete/one", method = RequestMethod.POST)
	public void deleteOne(String crrolUuid, HttpServletResponse response) {
		logger.info("deleteOne start");
		if (StringUtil.isEmpty(crrolUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[角色UUID]不能为空！"), response);
			return;
		}
		CoreRole coreRole = new CoreRole();
		coreRole.setCrrolUuid(crrolUuid);
		coreRoleService.deleteCoreRole(coreRole);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除角色成功！"), response);
		logger.info("deleteOne end");
	}
	
	/**
	 * 批量删除
	 * 
	 * @param crrolUuids
	 * @param response
	 */
	@RequestMapping(value="/delete/batch", method=RequestMethod.POST)
	public void deleteBatch(String crrolUuids, HttpServletResponse response) {
		logger.info("deleteBatch begin");
		if (StringUtils.isBlank(crrolUuids)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
			return;
		}
		String[] array = crrolUuids.split("\\|");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		if (list.size() == 0) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
			return;
		}
		coreRoleService.deleteCoreRoleByCnd(list);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功！"), response);
		logger.info("deleteBatch end ");
	}
	
	/**
	 * 获取单个角色
	 * 
	 * @param crrolUuid
	 * @param response
	 */
	@RequestMapping(value = "/getRoleByUUID", method = RequestMethod.POST)
	public void getMenuByUUID(String crrolUuid, HttpServletResponse response) {
		logger.info("getRoleByUUID start");
		if (StringUtil.isEmpty(crrolUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "角色UUID不能为空!"), response);
			return;
		}
		CoreRole coreRole = new CoreRole();
		coreRole.setCrrolUuid(crrolUuid);
		coreRole = coreRoleService.getCoreRole(coreRole);
		CoreRoleVO vo;
		if (coreRole != null) {
			vo = new CoreRoleVO();
			vo.convertPOToVO(coreRole);
			//获取权限
			List<String> roleList = new ArrayList<>();
			String[] func = coreRole.getCrrolFuns().split("\\|");
			for (int i = 0; i < func.length; i++) {
				roleList.add(func[i]);
			}
			String functionNames = "";
			List<CoreFunction> coreFunctions = coreFunctionService.findCoreFunctionsByIds(roleList);
			for (CoreFunction coreFunction : coreFunctions) {
				functionNames += coreFunction.getCrfntFunName() + "|";
			}
			vo.setCrrolFunsName(functionNames);			
			writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "角色获取成功！", vo), response);
		} else {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "角色获取失败！"), response);
		}
		logger.info("getRoleByUUID end");
	}
	
	/**
	 * 角色列表
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/roleList", method = RequestMethod.POST)
	public void roleList(HttpServletResponse response) {
		logger.info("roleList start");
		List<CoreRole> list = coreRoleService.findCoreRoleList();	
		List<CoreRoleVO> voList = new ArrayList<CoreRoleVO>();
		CoreRoleVO vo;
		for (CoreRole role : list) {				
			vo = new CoreRoleVO();
			vo.convertPOToVO(role);			
			//获取权限
			List<String> roleList = new ArrayList<>();
			String[] func = role.getCrrolFuns().split("\\|");
			for (int i = 0; i < func.length; i++) {
				roleList.add(func[i]);
			}
			String functionNames = "";
			List<CoreFunction> coreFunctions = coreFunctionService.findCoreFunctionsByIds(roleList);
			for (CoreFunction coreFunction : coreFunctions) {
				functionNames += coreFunction.getCrfntFunName() + "|";
			}
			vo.setCrrolFunsName(functionNames);
			voList.add(vo);
		}
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取角色列表成功！", voList), response);
		logger.info("roleList end");
	}

	/**
	 * 角色分页列表
	 * 
	 * @param pageNum 页码
	 * @param pageSize 页数
	 * @return
	 */
	@RequestMapping(value = "/rolePage", method = RequestMethod.POST)
	public void rolePage(Integer pageNum, Integer pageSize, HttpServletResponse response) {
		logger.info("rolePage start");
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		Page<CoreRole> page = coreRoleService.findCoreRolePage(pageNum, pageSize);
		Page<CoreRoleVO> pageVO = new Page<CoreRoleVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
		
		List<CoreRoleVO> vos = new ArrayList<CoreRoleVO>();
		CoreRoleVO vo;
		for (CoreRole coreRole : page.getResult()) {
			vo = new CoreRoleVO();
			vo.convertPOToVO(coreRole);
			//获取权限
			List<String> roleList = new ArrayList<>();
			String[] func = coreRole.getCrrolFuns().split("\\|");
			for (int i = 0; i < func.length; i++) {
				roleList.add(func[i]);
			}
			String functionNames = "";
			List<CoreFunction> coreFunctions = coreFunctionService.findCoreFunctionsByIds(roleList);
			for (CoreFunction coreFunction : coreFunctions) {
				functionNames += coreFunction.getCrfntFunName() + "|";
			}
			vo.setCrrolFunsName(functionNames);
			vos.add(vo);
		}
		pageVO.setResult(vos);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取角色分页列表成功！", pageVO), response);
		logger.info("rolePage end");
	}
	
}