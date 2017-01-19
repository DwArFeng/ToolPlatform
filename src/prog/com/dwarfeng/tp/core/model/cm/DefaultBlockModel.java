package com.dwarfeng.tp.core.model.cm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.dwarfeng.tp.core.model.obv.BlockObverser;
import com.dwarfeng.tp.core.model.struct.Block;
import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * 默认阻挡模型。
 * <p> 阻挡模型的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultBlockModel extends AbstractBlockModel {
	
	private final Map<String, String> delegate = new HashMap<>();
	
	private final InnerBlock block = new InnerBlock();
	
	/**
	 * 新实例。
	 * @param map 默认的映射关系。
	 */
	public DefaultBlockModel(Map<String, String> map) {
		Objects.requireNonNull(map, "入口参数 map 不能为 null。");
		delegate.putAll(map);
	}

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
		}finally {
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
			return delegate.containsValue(value);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public String get(Object key) {
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
	public String put(String key, String value) {
		Objects.requireNonNull(key, "入口参数 key 不能为 null。");
		Objects.requireNonNull(value, "入口参数 value 不能为 null。");
		
		lock.writeLock().lock();
		try{
			boolean changeFlag = containsKey(key);
			String oldValue = get(key);	//Maybe null
			String dejavu = delegate.put(key, value);
			
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

	private void fireEntryAdded(String key, String value) {
		for(BlockObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryAdded(key, value);
		}
	}

	private void fireEntryChanged(String key, String oldValue, String newValue) {
		for(BlockObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryChanged(key, oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public String remove(Object key) {
		lock.writeLock().lock();
		try{
			boolean removeFlag = containsKey(key);
			String dejavu = delegate.remove(key);
			if(removeFlag){
				fireEntryRemoved((String) key);
			}
			return dejavu;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireEntryRemoved(String key) {
		for(BlockObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryRemoved(key);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		Objects.requireNonNull(m, "入口参数 m 不能为 null。");
		
		lock.writeLock().lock();
		try{
			for(Map.Entry<? extends String, ? extends String> entry : m.entrySet()){
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
			fireCleared();
			delegate.clear();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCleared() {
		for(BlockObverser obverser : obversers){
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
			return delegate.keySet();
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
	public Collection<String> values() {
		lock.readLock().lock();
		try{
			return delegate.values();
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
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		lock.readLock().lock();
		try{
			return delegate.entrySet();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Updateable#update()
	 */
	@Override
	public void update() throws ProcessException {
		lock.writeLock().lock();
		block.updateLock.lock();
		try{
			try{
				
			}finally {
				fireUpdated();
			}
		}finally {
			block.updateLock.unlock();
			lock.writeLock().unlock();
		}
	}
	
	private void fireUpdated(){
		for(BlockObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireUpdated();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.BlockModel#getBlock()
	 */
	@Override
	public Block getBlock() {
		return block;
	}
	
	
	private final class InnerBlock implements Block{
		
		private final Lock blockLock = new ReentrantLock();
		private final Condition condition = blockLock.newCondition();
		
		private final Lock updateLock = new ReentrantLock();
		
		private Map<String, String> dictionary = new HashMap<>();

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Block#block(java.lang.String)
		 */
		@Override
		public void block(String key) {
			blockLock.lock();
			try{
				
				//TODO
			}finally {
				blockLock.unlock();
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Block#unblock(java.lang.String)
		 */
		@Override
		public void unblock(String key) {
			blockLock.lock();
			try{
				//TODO
				
				condition.signalAll();
			}finally {
				blockLock.unlock();
			}
		}
		
	}

}
