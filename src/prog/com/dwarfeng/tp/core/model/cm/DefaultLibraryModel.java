package com.dwarfeng.tp.core.model.cm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.tp.core.model.obv.LibraryObverser;
import com.dwarfeng.tp.core.model.struct.Library;

/**
 * Ĭ�Ͽ�ģ�͡�
 * <p> ��ģ�ͽӿڵ�Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultLibraryModel extends AbstractLibraryModel {
	
	private final Map<String, Library> delegate;

	/**
	 * ��ʵ����
	 */
	public DefaultLibraryModel() {
		this(new HashMap<>());
	}
	
	/**
	 * ��ʵ����
	 * @param map ָ���ĳ�ʼֵ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultLibraryModel(Map<String, Library> map) {
		Objects.requireNonNull(map, "��ڲ��� map ����Ϊ null");
		delegate = map;
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
	public Library get(Object key) {
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
	public Library put(String key, Library value) {
		Objects.requireNonNull(key, "��ڲ��� key ����Ϊ null��");
		Objects.requireNonNull(value, "��ڲ��� value ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			boolean changeFlag = containsKey(key);
			Library oldValue = get(key);	//Maybe null
			Library dejavu = delegate.put(key, value);
			
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

	private void fireEntryAdded(String key, Library value) {
		for(LibraryObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryAdded(key, value);
		}
	}

	private void fireEntryChanged(String key, Library oldValue, Library newValue) {
		for(LibraryObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryChanged(key, oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public Library remove(Object key) {
		lock.writeLock().lock();
		try{
			boolean removeFlag = containsKey(key);
			Library dejavu = delegate.remove(key);
			if(removeFlag){
				fireEntryRemoved((String) key);
			}
			return dejavu;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireEntryRemoved(String key) {
		for(LibraryObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryRemoved(key);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends String, ? extends Library> m) {
		Objects.requireNonNull(m, "��ڲ��� m ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			for(Map.Entry<? extends String, ? extends Library> entry : m.entrySet()){
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
		for(LibraryObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCleared();
		}
	}

	/**
	 * ���ظ�ģ�͵ļ����ϡ�
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ģ�͵ļ����ϡ�
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
	 * ���ظ�ģ�͵�ֵ���ϡ�
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ģ�͵�ֵ���ϡ�
	 */
	@Override
	public Collection<Library> values() {
		lock.readLock().lock();
		try{
			return delegate.values();
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ظ�ģ�͵���ڼ��ϡ�
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ģ�͵���ڼ��ϡ�
	 */
	@Override
	public Set<java.util.Map.Entry<String, Library>> entrySet() {
		lock.readLock().lock();
		try{
			return delegate.entrySet();
		}finally {
			lock.readLock().unlock();
		}
	}

}
