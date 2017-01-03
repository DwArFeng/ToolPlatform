package com.dwarfeng.tp.core.model.cm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.tp.core.model.obv.LoggerObverser;

/**
 * Ĭ�ϼ�¼��ģ�͡�
 * <p> ��¼��ģ�ͽӿڵ�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultLoggerModel extends AbstractLoggerModel {

	private final Set<String> delegate = new HashSet<>();
	
	private LoggerContext loggerContext;
	
	/**
	 * ��ʵ����
	 */
	public DefaultLoggerModel() {
		this(null);
	}
	
	/**
	 * ����һ��ӵ��ָ����¼�������ĵ���ʵ����
	 * @param loggerContext ָ���ļ�¼�������ġ�
	 */
	public DefaultLoggerModel(LoggerContext loggerContext){
		this.loggerContext = loggerContext;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.vim.LoggerModel#getLoggerContext()
	 */
	@Override
	public LoggerContext getLoggerContext() {
		lock.readLock().lock();
		try{
			return this.loggerContext;
		}finally{
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.vim.LoggerModel#setLoggerContext(org.apache.logging.log4j.core.LoggerContext)
	 */
	@Override
	public boolean setLoggerContext(LoggerContext loggerContext) {
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.loggerContext, loggerContext)) return false;
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

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#size()
	 */
	@Override
	public int size() {
		lock.readLock().lock();
		try{
			return delegate.size();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		lock.readLock().lock();
		try{
			return delegate.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		lock.readLock().lock();
		try{
			return delegate.contains(o);
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ��ȡģ�͵ĵ�������
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ģ�͵ĵ�������
	 */
	@Override
	public Iterator<String> iterator() {
		lock.readLock().lock();
		try{
			return delegate.iterator();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#toArray()
	 */
	@Override
	public Object[] toArray() {
		lock.readLock().lock();
		try{
			return delegate.toArray();
		}finally{
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#toArray(java.lang.Object[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		lock.readLock().lock();
		try{
			return delegate.toArray(a);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(String e) {
		Objects.requireNonNull(e, "��ڲ��� e ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			boolean aFlag = delegate.add(e);
			if(aFlag){
				fireLoggerNameAdded(e);
			}
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
		
	}
	
	private void fireLoggerNameAdded(String name) {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoggerNameAdded(name);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		lock.writeLock().lock();
		try{
			boolean aFlag = delegate.remove(o);
			if(aFlag){
				fireLoggerNameRemoved((String) o);
			}
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireLoggerNameRemoved(String name) {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoggerNameRemoved(name);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		lock.readLock().lock();
		try{
			return delegate.contains(c);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends String> c) {
		Objects.requireNonNull(c, "��ڲ��� c ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			boolean aFlag = false;
			for(String name : c){
				if(delegate.add(name)) aFlag = true;
			}
			return aFlag;
		}finally{
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("ģ���в�֧���������");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		Objects.requireNonNull(c, "��ڲ��� c ����Ϊ null��"); 
		
		lock.writeLock().lock();
		try{
			boolean aFlag = false;
			for(Object obj : c){
				if(delegate.remove(obj)) aFlag = true;
			}
			return aFlag;
		}finally{
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#clear()
	 */
	@Override
	public void clear() {
		lock.writeLock().lock();
		try{
			delegate.clear();
			fireLoggerNameCleared();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireLoggerNameCleared() {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoggerNameCleared();
		}
	}

}
