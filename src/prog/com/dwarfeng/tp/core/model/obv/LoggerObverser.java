package com.dwarfeng.tp.core.model.obv;

import org.apache.logging.log4j.Logger;
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
	public void fireLoggerAdded(Logger logger);
	
	/**
	 * 通知记录器的名称被移除。
	 * @param name 移除的名称。
	 */
	public void fireLoggerRemoved(Logger logger);
	
	/**
	 * 通知记录器被清除。
	 */
	public void fireLoggerCleared();

}
