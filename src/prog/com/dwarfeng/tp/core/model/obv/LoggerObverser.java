package com.dwarfeng.tp.core.model.obv;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 记录器观察器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerObverser extends Obverser{
	
	/**
	 * 通知记录器的名称增加。
	 * @param name 增加的新名称。
	 */
	public void fireLoggerNameAdded(String name);
	
	/**
	 * 通知记录器的名称被移除。
	 * @param name 移除的名称。
	 */
	public void fireLoggerNameRemoved(String name);
	
	/**
	 * 通知记录器的名称被清空。
	 */
	public void fireLoggerNameCleared();
	
	/**
	 * 通知记录器的上下文发生了改变。
	 * @param oldOne 旧的记录器上下文。
	 * @param newOne 新的记录器上下文。
	 */
	public void fireLoggerContextChanged(LoggerContext oldOne, LoggerContext newOne);

}
