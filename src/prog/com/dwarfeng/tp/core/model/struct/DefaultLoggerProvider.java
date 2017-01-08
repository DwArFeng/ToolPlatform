package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;

import com.dwarfeng.tp.core.model.cm.LoggerModel;

/**
 * 默认记录器提供器。
 * <p> 记录器提供器接口的默认实现。
 * @author  DwArFeng
 * @since 1.8
 */
public class DefaultLoggerProvider implements LoggerProvider {
	
	private final LoggerModel loggerModel;
	private final Logger logger = new Logger() {
		
		private Iterable<org.apache.logging.log4j.core.Logger> getLoggers(){
			ReadWriteLock lock = loggerModel.getLock();
			lock.readLock().lock();
			try{
				return loggerModel.getLoggers();
			}finally {
				lock.readLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#warn(java.lang.String, java.lang.Throwable)
		 */
		@Override
		public void warn(String message, Throwable t) {
			for(org.apache.logging.log4j.core.Logger logger : getLoggers()){
				logger.warn(message, t);
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#warn(java.lang.String)
		 */
		@Override
		public void warn(String message) {
			for(org.apache.logging.log4j.core.Logger logger : getLoggers()){
				logger.warn(message);
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#trace(java.lang.String)
		 */
		@Override
		public void trace(String message) {
			for(org.apache.logging.log4j.core.Logger logger : getLoggers()){
				logger.trace(message);
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#info(java.lang.String)
		 */
		@Override
		public void info(String message) {
			for(org.apache.logging.log4j.core.Logger logger : getLoggers()){
				logger.info(message);
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#fatal(java.lang.String, java.lang.Throwable)
		 */
		@Override
		public void fatal(String message, Throwable t) {
			for(org.apache.logging.log4j.core.Logger logger : getLoggers()){
				logger.fatal(message, t);
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#error(java.lang.String, java.lang.Throwable)
		 */
		@Override
		public void error(String message, Throwable t) {
			for(org.apache.logging.log4j.core.Logger logger : getLoggers()){
				logger.error(message, t);
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#debug(java.lang.String)
		 */
		@Override
		public void debug(String message) {
			for(org.apache.logging.log4j.core.Logger logger : getLoggers()){
				logger.debug(message);
			}
		}
	};
	
	/**
	 * 新实例。
	 * @param loggerModel 指定的记录器模型。
	 * @param defaultLogger 指定的默认记录器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultLoggerProvider(LoggerModel loggerModel) {
		Objects.requireNonNull(loggerModel, "入口参数 loggerModel 不能为 null。");
		
		this.loggerModel = loggerModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.LoggerProvider#getLogger()
	 */
	@Override
	public Logger getLogger() {
		return this.logger;
	}
	
}
