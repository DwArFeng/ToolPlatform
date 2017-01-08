package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;

import com.dwarfeng.tp.core.model.cm.LoggerModel;

/**
 * Ĭ�ϼ�¼���ṩ����
 * <p> ��¼���ṩ���ӿڵ�Ĭ��ʵ�֡�
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
	 * ��ʵ����
	 * @param loggerModel ָ���ļ�¼��ģ�͡�
	 * @param defaultLogger ָ����Ĭ�ϼ�¼����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultLoggerProvider(LoggerModel loggerModel) {
		Objects.requireNonNull(loggerModel, "��ڲ��� loggerModel ����Ϊ null��");
		
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
