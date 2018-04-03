package com.xiaoyu.lingdian.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreFunction;
import com.xiaoyu.lingdian.service.CoreFunctionService;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreFunctionVO;

@Controller
@RequestMapping(value="/coreFunction")
public class CoreFunctionController extends BaseController {

	/**
	 * 功能权限表
	 */
	@Autowired
	private CoreFunctionService coreFunctionService;
	
	/**
	 * 添加功能菜单
	 * 
	 * @param crfntFunName 功能项名称
	 * @param crfntResource 资源请求地址
	 * @param crFntFatherUuid
	 * @param response
	 */
	@RequestMapping(value = "/addMenu", method = RequestMethod.POST)
	public void addMenu(String crfntFunName, String crfntResource, String crFntFatherUuid, HttpServletResponse response) {
		logger.info("addMenu start");		
		if (StringUtil.isEmpty(crfntFunName)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "功能项名称不能为空!"), response);
			return;
		}
		CoreFunction coreFunction = new CoreFunction();
		coreFunction.setCrfntFatherUuid(crFntFatherUuid);
		coreFunction.setCrfntFunName(crfntFunName);
		coreFunction.setCrfntUuid(RandomUtil.generateString(16));
		coreFunction.setCrfntResource(crfntResource);
		coreFunction.setCrfntCdate(new Date());
		coreFunction.setCrfntUdate(new Date());
		coreFunction.setCrfntStatus(1);
		coreFunctionService.insertCoreFunction(coreFunction);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "功能项添加成功！"), response);
		logger.info("addMenu end");
	}
	
	/**
	 * 功能菜单启用禁用
	 * 
	 * @param crfntUuid
	 * @param crfntStatus
	 * @param response
	 */
	@RequestMapping(value = "/delete/one", method = RequestMethod.POST)
	public void deleteOne(String crfntUuid, Integer crfntStatus, HttpServletResponse response) {
		logger.info("deleteOne start");
		if (StringUtil.isEmpty(crfntUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[标识UUID]不能为空！"), response);
			return;
		}
		if (crfntStatus == null) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "状态不能为空！"), response);
			return;
		}
		CoreFunction coreFunction = new CoreFunction();
		coreFunction.setCrfntUuid(crfntUuid);
		coreFunction.setCrfntStatus(crfntStatus);
		coreFunctionService.updateCoreFunction(coreFunction);
		if (crfntStatus == 1) { //如果是状态为启用，即父菜单也启用，子菜单也启用
			coreFunction = coreFunctionService.getCoreFunction(coreFunction);
			if (coreFunction != null && coreFunction.getCrfntFatherUuid() != null) {
				//父菜单也启用
				CoreFunction fatherFunction = new CoreFunction();
				fatherFunction.setCrfntUuid(coreFunction.getCrfntFatherUuid());
				fatherFunction.setCrfntStatus(1);
				coreFunctionService.updateCoreFunction(fatherFunction);
				//子菜单也启用
				CoreFunction childFunction = new CoreFunction();
				childFunction.setCrfntFatherUuid(coreFunction.getCrfntUuid());
				childFunction.setCrfntStatus(1);
				coreFunctionService.updateCoreFunctionByFather(childFunction);
			}
			
		}
		if (crfntStatus == 0) { //如果是状态为禁用，即子菜单也启用
			coreFunction = coreFunctionService.getCoreFunction(coreFunction);
			if (coreFunction != null && coreFunction.getCrfntFatherUuid() != null) {
				CoreFunction childFunction = new CoreFunction();
				childFunction.setCrfntFatherUuid(coreFunction.getCrfntUuid());
				childFunction.setCrfntStatus(0);
				coreFunctionService.updateCoreFunctionByFather(childFunction);
			}
		}

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "操作成功！"), response);
		logger.info("deleteOne end");
	}
	
	/**
	 * 修改功能菜单
	 * 
	 * @param crfntUuid
	 * @param crfntFunName
	 * @param crfntResource
	 * @param crfntFatherUuid
	 * @param response
	 */
	@RequestMapping(value = "/updateMenu", method = RequestMethod.POST)
	public void updateMenu(String crfntUuid, String crfntFunName,
			String crfntResource, String crfntFatherUuid, HttpServletResponse response) {
		logger.info("updateMenu start");
		if (StringUtil.isEmpty(crfntUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "功能标识UUID不能为空!"), response);
			return;
		}
		if (StringUtil.isEmpty(crfntFunName)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "功能名称不能为空!"), response);
			return;
		}
		if (StringUtil.isEmpty(crfntResource)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "资源地址不能为空!"), response);
			return;
		}
		if (StringUtil.isEmpty(crfntFatherUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "上级菜单不能为空!"), response);
			return;
		}
		CoreFunction coreFunction = new CoreFunction();
		coreFunction.setCrfntFunName(crfntFunName);
		coreFunction.setCrfntFatherUuid(crfntFatherUuid);		
		coreFunction.setCrfntUuid(crfntUuid);
		coreFunction.setCrfntResource(crfntResource);
		coreFunction.setCrfntUdate(new Date());
		coreFunctionService.updateCoreFunction(coreFunction);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "功能项修改成功！"), response);
		logger.info("updateMenu end");
	}
	
	/**
	 * 获取单个菜单信息
	 * 
	 * @param crfntUuid
	 * @param response
	 */
	@RequestMapping(value = "/getMenuByUUID", method = RequestMethod.POST)
	public void getMenuByUUID(String crfntUuid, HttpServletResponse response) {
		logger.info("getMenuByUUID start");
		if (StringUtil.isEmpty(crfntUuid)) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "功能标识UUID不能为空!"), response);
			return;
		}
		CoreFunction coreFunction = new CoreFunction();
		CoreFunction fatherFunction = new CoreFunction();
		coreFunction.setCrfntUuid(crfntUuid);
		CoreFunction function = coreFunctionService.getCoreFunction(coreFunction);
		if (function!=null) {
			CoreFunctionVO vo = new CoreFunctionVO();
			vo.convertPOToVO(function);
			if (vo.getCrfntFatherUuid() != null) {
				fatherFunction.setCrfntUuid(vo.getCrfntFatherUuid());
				fatherFunction = coreFunctionService.getCoreFunction(fatherFunction);
				if (fatherFunction != null) {
					vo.setCrfntFatherName(fatherFunction.getCrfntFunName());
				}
			}
			writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "单个功能项获取成功！", vo), response);
		} else {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "单个功能项获取失败！"), response);
		}
		logger.info("getMenuByUUID end");
	}
	
	/**
	 * 功能菜单列表
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/menuList", method = RequestMethod.POST)
	public void menuList(HttpServletResponse response) {
		logger.info("menuList start");
		List<CoreFunction> list = coreFunctionService.findCoreFunctionList();
		List<CoreFunctionVO> functionVOs = new ArrayList<CoreFunctionVO>();
		CoreFunctionVO functionVO;
		CoreFunction fatherFunction;
		for (CoreFunction coreFunction : list) {
			functionVO = new CoreFunctionVO();
			functionVO.convertPOToVO(coreFunction);
			if (functionVO.getCrfntFatherUuid() != null) {
				fatherFunction = new CoreFunction();
				fatherFunction.setCrfntUuid(functionVO.getCrfntFatherUuid());
				fatherFunction = coreFunctionService.getCoreFunction(fatherFunction);
				if (fatherFunction != null) {
					functionVO.setCrfntFatherName(fatherFunction.getCrfntFunName());
				}
			}
			functionVOs.add(functionVO);
		}
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取功能菜单列表成功！", functionVOs), response);
		logger.info("menuList end");
	}
	
	/**
	 * 主菜单和一级菜单列表
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/main/menu", method = RequestMethod.POST)
	public void mainMenuList(HttpServletResponse response) {
		logger.info("mainMenuList start");
		List<CoreFunction> list = coreFunctionService.getCoreFunctionByCnd();
		List<CoreFunctionVO> functionVOs = new ArrayList<CoreFunctionVO>();
		CoreFunctionVO functionVO;
		CoreFunction fatherFunction;
		for (CoreFunction coreFunction : list) {
			functionVO = new CoreFunctionVO();
			functionVO.convertPOToVO(coreFunction);
			if (functionVO.getCrfntFatherUuid() != null) {
				fatherFunction = new CoreFunction();
				fatherFunction.setCrfntUuid(functionVO.getCrfntFatherUuid());
				fatherFunction = coreFunctionService.getCoreFunction(fatherFunction);
				if (fatherFunction != null) {
					functionVO.setCrfntFatherName(fatherFunction.getCrfntFunName());
				}
			}
			functionVOs.add(functionVO);
		}
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取主菜单和一级菜单列表成功！", functionVOs), response);
		logger.info("mainMenuList end");
	}
	
	/**
	 * 功能菜单分页列表
	 * 
	 * @param pageNum 页码
	 * @param pageSize 页数
	 * @param response
	 */
	@RequestMapping(value = "/menuPage", method = RequestMethod.POST)
	public void menuPage(Integer pageNum, Integer pageSize, HttpServletResponse response) {
		logger.info("menuPage start");
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		Page<CoreFunction> page = coreFunctionService.findCoreFunctionPage(pageNum, pageSize);
		Page<CoreFunctionVO> pageVO = new Page<CoreFunctionVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());		
		List<CoreFunctionVO> vos = new ArrayList<CoreFunctionVO>();
		CoreFunctionVO vo;
		CoreFunction fatherFunction;
		for (CoreFunction coreFunction : page.getResult()) {
			vo = new CoreFunctionVO();
			vo.convertPOToVO(coreFunction);
			if (vo.getCrfntFatherUuid() != null) {
				fatherFunction = new CoreFunction();
				fatherFunction.setCrfntUuid(vo.getCrfntFatherUuid());
				fatherFunction = coreFunctionService.getCoreFunction(fatherFunction);
				if (fatherFunction != null) {
					vo.setCrfntFatherName(fatherFunction.getCrfntFunName());
				}
			}
			vos.add(vo);
		}
		pageVO.setResult(vos);
		
		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取功能菜单分页列表成功！", pageVO), response);
		logger.info("menuPage end");
	}
	
}