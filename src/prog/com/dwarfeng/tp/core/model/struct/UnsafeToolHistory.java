package com.dwarfeng.tp.core.model.struct;

import java.util.Date;

/**
 * 不安全工具历史。
 * <p> 该工具历史返回工具信息的属性，但是有可能抛出异常，速度也比工具历史要慢。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface UnsafeToolHistory {

	/**
	 * 获取工具历史的名称。
	 * @return 工具历史的名称。
	 * @throws ProcessException 过程异常。
	 */
	public String getName() throws ProcessException;
	
	/**
	 * 获取工具的运行日期。
	 * @return 工具的运行日期。
	 * @throws ProcessException 过程异常。
	 */
	public Date getRanDate() throws ProcessException;
	
	/**
	 * 获取工具的结束日期。
	 * @return 工具的结束日期。
	 * @throws ProcessException 过程异常。
	 */
	public Date getExitedDate() throws ProcessException;
	
	/**
	 * 获取工具的退出代码。
	 * @return 工具的退出代码。
	 * @throws ProcessException 过程异常。
	 */
	public int getExitedCode() throws ProcessException;
	
}
