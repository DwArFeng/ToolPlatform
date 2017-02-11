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
 * 默认库模型。
 * <p> 库模型接口的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultLibraryModel extends AbstractLibraryModel {
	
	private final Map<String, Library> libraryMap = new HashMap<>();
	
	/**
	 * 新实例。
	 */
	public DefaultLibraryModel() {
		this(new HashSet<>());
	}
	
	/**
	 * 新实例。
	 * @param c 指定的初始值。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultLibraryModel(Set<Library> c){
		Objects.requireNonNull(c, "入口参数 c 不能为 null。");
		for(Library library : c){
			notFireAdd(library);
		}
	}
	
	/**
	 * 添加指定的库，但不通知，也不线程安全。
	 * @param library 指定的库。
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
	 * 返回该库模型的过程迭代器。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 该库模型的过程迭代器。
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
