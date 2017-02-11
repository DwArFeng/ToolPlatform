package com.dwarfeng.tp.core.model.cm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.tp.core.model.obv.LibraryObverser;
import com.dwarfeng.tp.core.model.struct.Library;

/**
 * Ĭ�Ͽ�ģ�͡�
 * <p> ��ģ�ͽӿڵ�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultLibraryModel extends AbstractLibraryModel {
	
	private final Map<String, Library> libraryMap = new HashMap<>();
	
	/**
	 * ��ʵ����
	 */
	public DefaultLibraryModel() {
		this(new HashSet<>());
	}
	
	/**
	 * ��ʵ����
	 * @param c ָ���ĳ�ʼֵ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultLibraryModel(Set<Library> c){
		Objects.requireNonNull(c, "��ڲ��� c ����Ϊ null��");
		for(Library library : c){
			notFireAdd(library);
		}
	}
	
	/**
	 * ���ָ���Ŀ⣬����֪ͨ��Ҳ���̰߳�ȫ��
	 * @param library ָ���Ŀ⡣
	 */
	private void notFireAdd(Library library){
		if(Objects.isNull(library)) return;
		if(libraryMap.containsKey(library.getName())) return;
		
		libraryMap.put(library.getName(), library);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LibraryModel#add(com.dwarfeng.tp.core.model.struct.Library)
	 */
	@Override
	public boolean add(Library library) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(library)) return false;
			if(libraryMap.containsKey(library.getName())) return false;
			
			libraryMap.put(library.getName(), library);
			fireLibraryAdded(library);
			
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireLibraryAdded(Library library) {
		for(LibraryObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLibraryAdded(library);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LibraryModel#remove(com.dwarfeng.tp.core.model.struct.Library)
	 */
	@Override
	public boolean remove(Library library) {
		lock.writeLock().lock();
		try{
			if(! libraryMap.containsKey(library)) return false;
			libraryMap.remove(library.getName());
			fireLibraryRemoved(library);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireLibraryRemoved(Library library) {
		for(LibraryObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLibraryRemoved(library);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LibraryModel#clear()
	 */
	@Override
	public void clear() {
		lock.writeLock().lock();
		try{
			libraryMap.clear();
			fireCleared();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCleared() {
		for(LibraryObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCleared();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LibraryModel#size()
	 */
	@Override
	public int size() {
		lock.readLock().lock();
		try{
			return libraryMap.size();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LibraryModel#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		lock.readLock().lock();
		try{
			return libraryMap.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LibraryModel#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String name) {
		lock.readLock().lock();
		try{
			if(Objects.isNull(name)) return false;
			return libraryMap.containsKey(name);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LibraryModel#get(java.lang.String)
	 */
	@Override
	public Library get(String name) {
		lock.readLock().lock();
		try{
			if(Objects.isNull(name)) return null;
			return libraryMap.get(name);
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ظÿ�ģ�͵Ĺ��̵�������
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return �ÿ�ģ�͵Ĺ��̵�������
	 */
	@Override
	public Iterator<Library> iterator() {
		lock.readLock().lock();
		try{
			return libraryMap.values().iterator();
		}finally {
			lock.readLock().unlock();
		}
	}

}
