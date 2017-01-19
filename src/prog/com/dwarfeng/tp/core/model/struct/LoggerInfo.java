package com.dwarfeng.tp.core.model.struct;

/**
 * 记录器信息。
 * @author DwArFeng
 * @since 1.8
 */
public interface LoggerInfo {

	/**
	 * 返回记录器信息中的记录器名称。
	 * @return 记录器名称。
	 * @throws ProcessException 过程异常。
	 */
	public String getName() throws ProcessException;
	
}
