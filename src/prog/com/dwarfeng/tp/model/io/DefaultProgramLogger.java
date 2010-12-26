package com.dwarfeng.tp.model.io;

import java.util.Collection;
import java.util.Objects;

import org.apache.logging.log4j.Logger;

/**
 * 默认的工具平台记录器。
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultProgramLogger implements ProgramLogger{

	private final Collection<? extends Logger> loggers;
	
	public DefaultProgramLogger(Collection<? extends Logger> loggers) {
		Objects.requireNonNull(loggers, "入口参数 loggers 不能为 null。");
		this.loggers = loggers;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.model.io.ProgramLogger#trace(java.lang.String)
	 */
	@Override
	public void trace(String message) {
		for(Logger logger : loggers){
			logger.trace(message);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.model.io.ProgramLogger#debug(java.lang.String)
	 */
	@Override
	public void debug(String message) {
		for(Logger logger : loggers){
			logger.debug(message);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.model.io.ProgramLogger#info(java.lang.String)
	 */
	@Override
	public void info(String message) {
		for(Logger logger : loggers){
			logger.info(message);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.model.io.ProgramLogger#warn(java.lang.String)
	 */
	@Override
	public void warn(String message) {
		for(Logger logger : loggers){
			logger.warn(message);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.model.io.ProgramLogger#warn(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(String message, Throwable t) {
		for(Logger logger : loggers){
			logger.warn(message, t);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.model.io.ProgramLogger#error(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(String message, Throwable t) {
		for(Logger logger : loggers){
			logger.error(message, t);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.model.io.ProgramLogger#fatal(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void fatal(String message, Throwable t) {
		for(Logger logger : loggers){
			logger.fatal(message, t);
		}
	}
	

}
