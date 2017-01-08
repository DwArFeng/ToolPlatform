package com.dwarfeng.tp.core.model.obv;

import java.util.Set;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 记录器观察器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerObverser extends Obverser{
	
	/**
	 * 通知模型中的记录器上下文发生了改变。
	 * @param oldOne 旧的记录器上下文。
	 * @param newOne  新的记录器上下文。
	 */
	public void fireLoggerContextChanged(LoggerContext oldOne, LoggerContext newOne);
	
	/**
	 * 通知模型中的记录器名称集合发生了改变。
	 * @param oldOne 旧的名称集合。
	 * @param newOne 新的名称集合。
	 */
	public void fireLoggerNamesChanged(Set<String> oldOne, Set<String> newOne);
	
	/**
	 * 通知模型更新。
	 */
	public void fireUpdated();
}
