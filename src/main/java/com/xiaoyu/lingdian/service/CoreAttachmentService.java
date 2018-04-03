package com.xiaoyu.lingdian.service;

import java.util.List;
import com.xiaoyu.lingdian.entity.CoreAttachment;

public interface CoreAttachmentService {

	/**
	* 添加
	* @param coreAttachment
	* @return
	*/
	public boolean insertCoreAttachment(CoreAttachment coreAttachment);

	/**
	* 修改
	* @param coreAttachment
	* @return
	*/
	public boolean updateCoreAttachment(CoreAttachment coreAttachment);
	
	/**
	* 删除
	* @param coreAttachment
	* @return
	*/
	public boolean deleteCoreAttachment(CoreAttachment coreAttachment);

	/**
	* 查询
	* @param coreAttachment
	* @return
	*/
	public CoreAttachment getCoreAttachment(CoreAttachment coreAttachment);

	/**
	 * 根据业务UUID查询业务里的全部附件
	 *
	 * @param cratmBusUuid
	 * @return
	 */
	public List<CoreAttachment> findCoreAttachmentByCnd(String cratmBusUuid);

	/**
	* 删除
	* 
	* @param coreAttachment
	* @return
	*/
	public boolean deleteCoreAttachmentByBusi(CoreAttachment coreAttachment);

	/**
	* 根据业务UUID修改
	* 
	* @param coreAttachment
	* @return
	*/
	public boolean updateCoreAttachmentByBus(CoreAttachment coreAttachment);
	
}