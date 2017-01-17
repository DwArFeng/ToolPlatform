package com.dwarfeng.tp.core.model.cm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Library get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Library put(String key, Library value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Library remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Library> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Library> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<String, Library>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
