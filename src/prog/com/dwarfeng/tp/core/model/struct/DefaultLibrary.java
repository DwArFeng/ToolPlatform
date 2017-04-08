package com.dwarfeng.tp.core.model.struct;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.tp.core.model.io.LibraryClassLoader;

/**
 * 默认库。
 * <p> 库接口的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultLibrary implements Library {

	private final Set<LibraryClassLoader> references = new HashSet<>();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private final String name;
	
	/**
	 * 新实例。
	 * @param name 指定的名称。
	 * @throws NullPointerException 入口参数为 null。
	 */
	public DefaultLibrary(String name) {
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe#getLock()
	 */
	@Override
	public ReadWriteLock getLock() {
		return lock;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Library#hasReference()
	 */
	@Override
	public boolean hasReference() {
		lock.readLock().lock();
		try{
			return ! references.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

//	/*
//	 * (non-Javadoc)
//	 * @see com.dwarfeng.tp.core.model.struct.Library#getReferences()
//	 */
//	@Override
//	public Set<LibraryClassLoader> getReferences() {
//		lock.readLock().lock();
//		try{
//			return Collections.unmodifiableSet(references);
//		}finally {
//			lock.readLock().unlock();
//		}
//	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Library#reference(com.dwarfeng.tp.core.model.io.LibraryClassLoader)
	 */
	@Override
	public void reference(LibraryClassLoader libraryClassLoader) {
		Objects.requireNonNull(libraryClassLoader, "入口参数 libraryClassLoader 不能为 null。");
		
		lock.writeLock().lock();
		try{
			references.add(libraryClassLoader);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Library#removeReference(com.dwarfeng.tp.core.model.io.LibraryClassLoader)
	 */
	@Override
	public void removeReference(LibraryClassLoader libraryClassLoader) {
		Objects.requireNonNull(libraryClassLoader, "入口参数 libraryClassLoader 不能为 null。");
		
		lock.writeLock().lock();
		try{
			references.remove(libraryClassLoader);
		}finally {
			lock.writeLock().unlock();
		}
	}

}
