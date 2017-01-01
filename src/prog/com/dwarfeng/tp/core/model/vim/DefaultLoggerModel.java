package com.dwarfeng.tp.core.model.vim;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.tp.core.model.obv.LoggerObverser;

/**
 * 默认记录器模型。
 * <p> 记录器模型接口的默认实现。
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultLoggerModel extends AbstractLoggerModel {

	private final Set<String> delegate = new HashSet<>();
	
	private LoggerContext loggerContext;
	
	/**
	 * 新实例。
	 */
	public DefaultLoggerModel() {
		this(null);
	}
	
	/**
	 * 生成一个拥有指定记录器上下文的新实例。
	 * @param loggerContext 指定的记录器上下文。
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
		return this.loggerContext;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.vim.LoggerModel#setLoggerContext(org.apache.logging.log4j.core.LoggerContext)
	 */
	@Override
	public boolean setLoggerContext(LoggerContext loggerContext) {
		if(Objects.equals(this.loggerContext, loggerContext)) return false;
		LoggerContext oldOne = this.loggerContext;
		this.loggerContext = loggerContext;
		fireLoggerContextChanged(oldOne, loggerContext);
		return true;
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
		return delegate.size();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#iterator()
	 */
	@Override
	public Iterator<String> iterator() {
		return delegate.iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#toArray()
	 */
	@Override
	public Object[] toArray() {
		return delegate.toArray();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#toArray(java.lang.Object[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return delegate.toArray(a);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(String e) {
		Objects.requireNonNull(e, "入口参数 e 不能为 null。");
		
		boolean aFlag = delegate.add(e);
		if(aFlag){
			fireLoggerNameAdded(e);
		}
		return aFlag;
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
		boolean aFlag = delegate.remove(o);
		if(aFlag){
			fireLoggerNameRemoved((String) o);
		}
		return aFlag;
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
		return delegate.contains(c);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends String> c) {
		boolean aFlag = false;
		for(String name : c){
			if(delegate.add(name)) aFlag = true;
		}
		return aFlag;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("模型中不支持这个方法");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean aFlag = false;
		for(Object obj : c){
			if(delegate.remove(obj)) aFlag = true;;
		}
		return aFlag;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#clear()
	 */
	@Override
	public void clear() {
		delegate.clear();
		fireLoggerNameCleared();
	}

	private void fireLoggerNameCleared() {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoggerNameCleared();
		}
	}

}
