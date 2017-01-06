package com.dwarfeng.tp.core.model.struct;

/**
 * 记录器提供器。
 * <p> 用于从指定的记录器模型中提供记录器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerProvider {
	
	/**
	 * 获取记录器提供器中提供的记录器。
	 * @return 该记录器提供器提供的记录器。
	 */
	public Logger getLogger();
	
}
