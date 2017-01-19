package com.dwarfeng.tp.core.model.cm;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.tp.core.model.obv.ToolObverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * 默认工具信息模型。
 * <p> 工具模型的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultToolInfoModel extends AbstractToolInfoModel implements ToolInfoModel {
	
	private final Map<String, ToolInfo> delegate = new HashMap<>();

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#size()
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
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		lock.readLock().lock();
		try{
			return delegate.isEmpty();
		}finally{
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		lock.readLock().lock();
		try{
			return delegate.containsKey(key);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		lock.readLock().lock();
		try{
			return delegate.containsKey(value);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public ToolInfo get(Object key) {
		lock.readLock().lock();
		try{
			return delegate.get(key);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public ToolInfo put(String key, ToolInfo value) {
		Objects.requireNonNull(key, "入口参数 key 不能为 null。");
		Objects.requireNonNull(value, "入口参数 value 不能为 null。");
		
		lock.writeLock().lock();
		try{
			boolean changeFlag = containsKey(key);
			ToolInfo oldValue = get(key);	//Maybe null
			ToolInfo dejavu = delegate.put(key, value);
			
			if(changeFlag){
				fireEntryChanged(key, oldValue, value);
			}else{
				fireEntryAdded(key, value);
			}
			
			return dejavu;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireEntryAdded(String name, ToolInfo info) {
		for(ToolObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryAdded(name, info);
		}
	}

	private void fireEntryChanged(String name, ToolInfo oldOne, ToolInfo newOne) {
		for(ToolObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryChanged(name, oldOne, newOne);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public ToolInfo remove(Object key) {
		lock.writeLock().lock();
		try{
			boolean removeFlag = containsKey(key);
			ToolInfo dejavu = delegate.remove(key);
			if(removeFlag){
				fireEntryRemoved((String) key);
			}
			return dejavu;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireEntryRemoved(String name) {
		for(ToolObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryRemoved(name);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends String, ? extends ToolInfo> m) {
		Objects.requireNonNull(m, "入口参数 m 不能为 null。");
		
		lock.writeLock().lock();
		try{
			for(Map.Entry<? extends String, ? extends ToolInfo> entry : m.entrySet()){
				put(entry.getKey(), entry.getValue());
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		lock.writeLock().lock();
		try{
			delegate.clear();
			fireCleared();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCleared() {
		for(ToolObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCleared();
		}
	}
	
	/**
	 * 返回该模型的键集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的键集合。
	 */
	@Override
	public Set<String> keySet() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(delegate.keySet());
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * 返回该模型的值集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的值集合。
	 */
	@Override
	public Collection<ToolInfo> values() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableCollection(delegate.values());
		}finally {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * 返回该模型的入口集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的入口集合。
	 */
	@Override
	public Set<java.util.Map.Entry<String, ToolInfo>> entrySet() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(delegate.entrySet());
		}finally {
			lock.readLock().unlock();
		}
	}

}
