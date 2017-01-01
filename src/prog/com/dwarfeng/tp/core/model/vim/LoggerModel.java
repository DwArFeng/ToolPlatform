package com.dwarfeng.tp.core.model.vim;

import java.util.Set;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.obv.LoggerObverser;

/**
 * 有关记录器的配置模型。
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerModel extends Set<String>, ObverserSet<LoggerObverser>{
	
	/**
	 * 获取与该模型有关的记录器上下文。
	 * @return 与该模型有关的记录器上下文。
	 */
	public LoggerContext getLoggerContext();
	
	/**
	 * 设置与该模型有关的记录器上下文。
	 * @param loggerContext 指定的记录器上下文。
	 * @return 该操作是否对该模型造成了改变。
	 */
	public boolean setLoggerContext(LoggerContext loggerContext);

}
