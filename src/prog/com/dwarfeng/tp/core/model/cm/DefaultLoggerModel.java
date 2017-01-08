package com.dwarfeng.tp.core.model.cm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.tp.core.model.obv.LoggerObverser;
import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * Ĭ�ϼ�¼��ģ�͡�
 * <p> ��¼��ģ�ͽӿڵ�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultLoggerModel extends AbstractLoggerModel {
	
	private LoggerContext loggerContext = (LoggerContext) LogManager.getContext();
	private Set<String> loggerNames = new HashSet<>();
	private Set<Logger> loggers = new HashSet<>();

	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LoggerModel#getLoggerContext()
	 */
	@Override
	public LoggerContext getLoggerContext() {
		lock.readLock().lock();
		try{
			return this.loggerContext;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LoggerModel#setLoggerContext(org.apache.logging.log4j.core.LoggerContext)
	 */
	@Override
	public boolean setLoggerContext(LoggerContext loggerContext) {
		Objects.requireNonNull(loggerContext, "��ڲ��� loggerContext ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(loggerContext, this.loggerContext)) return false;
			LoggerContext oldOne = this.loggerContext;
			this.loggerContext = loggerContext;
			fireLoggerContextChanged(oldOne, loggerContext);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireLoggerContextChanged(LoggerContext oldOne, LoggerContext newOne) {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoggerContextChanged(oldOne, newOne);
		}
	}

	/**
	 * ���ظ�ģ�ͼ�¼�����Ƽ��ϡ�
	 * <p> ע�⣬�ü��ϲ����̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ģ�͵ļ�¼�����Ƽ��ϡ�
	 */
	@Override
	public Set<String> getLoggerNames() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(this.loggerNames);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LoggerModel#setLoggerNames(java.util.Set)
	 */
	@Override
	public boolean setLoggerNames(Set<String> loggerNames) {
		Objects.requireNonNull(loggerNames, "��ڲ��� loggerNames ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(loggerNames, this.loggerNames)) return false;
			Set<String> oldOne = this.loggerNames;
			this.loggerNames = loggerNames;
			fireLoggerNamesChanged(oldOne, loggerNames);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireLoggerNamesChanged(Set<String> oldOne, Set<String> newOne) {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoggerNamesChanged(oldOne, newOne);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Updateable#update()
	 */
	@Override
	public boolean update() {
		lock.writeLock().lock();
		try{
			try{
				return innerUpdate();
			}catch (ProcessException e) {
				return false;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Updateable#tryUpdate()
	 */
	@Override
	public void tryUpdate() throws ProcessException {
		lock.writeLock().lock();
		try{
			innerUpdate();
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private boolean innerUpdate() throws ProcessException{
		Set<Logger> loggers = new HashSet<>();
		for(String loggerName : loggerNames){
			loggers.add(loggerContext.getLogger(loggerName));
		}
		this.loggers = loggers;
		fireUpdated();
		return true;
	}

	private void fireUpdated() {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireUpdated();
		}
	}

	/**
	 * ���ظ�ģ�ͼ�¼�����ϡ�
	 * <p> ע�⣬�ü��ϲ����̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ģ�͵ļ�¼�����ϡ�
	 */
	@Override
	public Set<Logger> getLoggers() {
		lock.readLock().lock();
		try{
			return this.loggers;
		}finally {
			lock.readLock().unlock();
		}
	}
	
}
