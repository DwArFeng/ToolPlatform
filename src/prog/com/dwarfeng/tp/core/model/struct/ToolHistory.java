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
	 * 获取工具的运行日期。
	 * @return 工具的运行日期。
	 */
	public Date getRanDate();
	
	/**
	 * 获取工具的结束日期。
	 * @return 工具的结束日期。
	 */
	public Date getExitedDate();
	
	/**
	 * 获取工具的退出代码。
	 * @return 工具的退出代码。
	 */
	public int getExitedCode();
	
}
