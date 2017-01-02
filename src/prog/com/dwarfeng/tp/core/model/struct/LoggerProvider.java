package com.dwarfeng.tp.core.model.struct;

import com.dwarfeng.tp.core.model.cm.LoggerModel;

/**
 * 记录器提供器。
 * <p> 用于从指定的记录器模型中提供记录器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerProvider {

	/**
	 * 获取提供器中的记录器模型。
	 * @return 提供器中的记录器模型。
	 */
	public LoggerModel getLoggerModel();
	
	/**
	 * 获取记录器提供器中提供的记录器。
	 * @return 该记录器提供器提供的记录器。
	 */
	public Logger getLogger();
	
	/**
	 * 根据记录器模型中的数据重新生成一个记录器。
	 * @throws ProcessException 过程异常。
	 */
	public void update() throws ProcessException;
	
	/**
	 * 将记录器重置为默认的记录器。
	 */
	public void update2Default();
}
