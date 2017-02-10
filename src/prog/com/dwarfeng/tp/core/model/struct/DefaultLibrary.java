package com.dwarfeng.tp.core.model.struct;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.tp.core.model.io.LibraryClassLoader;

/**
 * Ĭ�Ͽ⡣
 * <p> ��ӿڵ�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultLibrary implements Library {

	private final Set<LibraryClassLoader> references = new HashSet<>();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private final String name;
	
	/**
	 * ��ʵ����
	 * @param name ָ�������ơ�
	 * @throws NullPointerException ��ڲ���Ϊ null��
	 */
	public DefaultLibrary(String name) {
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
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
		Objects.requireNonNull(libraryClassLoader, "��ڲ��� libraryClassLoader ����Ϊ null��");
		
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
		Objects.requireNonNull(libraryClassLoader, "��ڲ��� libraryClassLoader ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			references.remove(libraryClassLoader);
		}finally {
			lock.writeLock().unlock();
		}
	}

}