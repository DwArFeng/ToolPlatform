package com.dwarfeng.tp.core.model.cm;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.tp.core.model.obv.LoggerObverser;

/**
 * 默认记录器模型。
 * <p> 记录器模型接口的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultLoggerModel extends AbstractLoggerModel {

	private final Set<Logger> delegate = new HashSet<>();
	private final Set<LoggerContext> loggerContexts = new HashSet<>();

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
	 * 获取模型的迭代器。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的迭代器。
	 */
	@Override
	public Iterator<Logger> iterator() {
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
	public boolean add(Logger e) {
		Objects.requireNonNull(e, "入口参数 e 不能为 null。");
		
		lock.writeLock().lock();
		try{
			boolean aFlag = delegate.add(e);
			if(aFlag){
				loggerContexts.add(e.getContext());
				fireLoggerAdded(e);
			}
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
		
	}
	
	private void fireLoggerAdded(Logger logger) {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoggerAdded(logger);
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
				Logger logger = (Logger) o;
				fireLoggerRemoved(logger);
				//检查记录器的上下文中是否已经没有任何记录器在模型中了，如果是，则关闭该记录器。
				Collection<Logger> cl = logger.getContext().getLoggers();
				boolean stopFlag = true;
				for(Logger testLogger : cl){
					if(contains(testLogger)) stopFlag = false;
				}
				if(stopFlag){
					stopLoggerContext(logger.getContext());
				}
			}
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireLoggerRemoved(Logger logger) {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoggerRemoved(logger);
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
	public boolean addAll(Collection<? extends Logger> c) {
		Objects.requireNonNull(c, "入口参数 c 不能为 null。");
		
		lock.writeLock().lock();
		try{
			boolean aFlag = false;
			for(Logger logger : c){
				if(delegate.add(logger)) aFlag = true;
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
		throw new UnsupportedOperationException("模型中不支持这个方法");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		Objects.requireNonNull(c, "入口参数 c 不能为 null。"); 
		
		lock.writeLock().lock();
		try{
			boolean aFlag = false;
			for(Object obj : c){
				if(remove(obj)) aFlag = true;
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
			innerClear();
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void innerClear(){
		for(LoggerContext loggerContext : loggerContexts){
			loggerContext.stop();
		}
		this.loggerContexts.clear();
		delegate.clear();
		fireLoggerCleared();
	}

	private void fireLoggerCleared() {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoggerCleared();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LoggerModel#getLoggerContexts()
	 */
	@Override
	public Set<LoggerContext> getLoggerContexts() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(this.loggerContexts);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LoggerModel#stopLoggerContext(org.apache.logging.log4j.core.LoggerContext)
	 */
	@Override
	public boolean stopLoggerContext(LoggerContext context) {
		Objects.requireNonNull(context, "入口参数 context 不能为 null。");
		
		lock.writeLock().lock();
		try{
			Collection<Logger> loggers = context.getLoggers();
			
			context.close();
			boolean flag0= removeAll(loggers);
			boolean flag1 = loggerContexts.remove(context);
			
			return flag0 || flag1;
			
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LoggerModel#stopAllLoggerContexts()
	 */
	@Override
	public void stopAllLoggerContexts() {
		lock.writeLock().lock();
		try{
			innerClear();
		}finally {
			lock.writeLock().unlock();
		}
	}

}
