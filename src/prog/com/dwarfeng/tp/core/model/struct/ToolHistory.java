package com.dwarfeng.tp.core.model.struct;

import java.util.Date;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 工具历史。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolHistory extends Name{
	
	/**
	 * 获取工具的运行时间。
	 * @return 工具的运行时间。
	 */
	public Date getRanTime();
	
	/**
	 * 获取工具的结束时间。
	 * @return 工具的结束时间。
	 */
	public Date getExitedTime();
	
}
