package com.dwarfeng.tp.core.model.struct;

import java.net.URL;
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
 * @since 1.8
 */
public final class DefaultLibrary implements Library {

	private final Set<LibraryClassLoader> references = new HashSet<>();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private final URL url; 
	
	/**
	 * 新实例。
	 * @param url 指定的 url。
	 * @throws NullPointerException 入口参数为 null。
	 */
	public DefaultLibrary(URL url) {
		Objects.requireNonNull(url, "入口参数 url 不能为 null。");
		this.url = url;
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

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Library#getURL()
	 */
	@Override
	public URL getURL() throws ProcessException {
		return url;
	}


}
