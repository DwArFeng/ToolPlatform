package com.dwarfeng.tp.core.model.struct;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.tp.core.model.cm.LoggerModel;

/**
 * Ĭ�ϼ�¼���ṩ����
 * <p> ��¼���ṩ���ӿڵ�Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 1.8
 */
public class DefaultLoggerProvider implements LoggerProvider {
	
	private final LoggerModel loggerModel;
	
	private final InnerLogger innerLogger = new InnerLogger();

	
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
	 * @see com.dwarfeng.tp.core.model.struct.LoggerProvider#getLoggerModel()
	 */
	@Override
	public LoggerModel getLoggerModel() {
		return this.loggerModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.LoggerProvider#getLogger()
	 */
	@Override
	public Logger getLogger() {
		return this.innerLogger;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.LoggerProvider#update()
	 */
	@Override
	public void update() throws ProcessException {
		this.innerLogger.loggers.clear();
		for(String name : loggerModel){
			this.innerLogger.loggers.add(loggerModel.getLoggerContext().getLogger(name));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.LoggerProvider#update2Default()
	 */
	@Override
	public void update2Default() {
		this.innerLogger.loggers.clear();
	}
	
	private final class InnerLogger implements Logger{
		
		private final Set<org.apache.logging.log4j.core.Logger> loggers = new HashSet<>();

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#trace(java.lang.String)
		 */
		@Override
		public void trace(String message) {
			for(org.apache.logging.log4j.core.Logger logger : loggers){
				logger.trace(message);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#debug(java.lang.String)
		 */
		@Override
		public void debug(String message) {
			for(org.apache.logging.log4j.core.Logger logger : loggers){
				logger.debug(message);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#info(java.lang.String)
		 */
		@Override
		public void info(String message) {
			for(org.apache.logging.log4j.core.Logger logger : loggers){
				logger.info(message);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#warn(java.lang.String)
		 */
		@Override
		public void warn(String message) {
			for(org.apache.logging.log4j.core.Logger logger : loggers){
				logger.warn(message);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#warn(java.lang.String, java.lang.Throwable)
		 */
		@Override
		public void warn(String message, Throwable t) {
			for(org.apache.logging.log4j.core.Logger logger : loggers){
				logger.warn(message, t);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#error(java.lang.String, java.lang.Throwable)
		 */
		@Override
		public void error(String message, Throwable t) {
			for(org.apache.logging.log4j.core.Logger logger : loggers){
				logger.error(message, t);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Logger#fatal(java.lang.String, java.lang.Throwable)
		 */
		@Override
		public void fatal(String message, Throwable t) {
			for(org.apache.logging.log4j.core.Logger logger : loggers){
				logger.fatal(message, t);
			}
		}
		
	}

}
