package com.dwarfeng.tp.core.model.cm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.dutil.basic.str.Name;
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
	
	private final Set<Library> librarys = new HashSet<>();
	private final Set<String> libraryNames = new HashSet<>();
	
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
		librarys.addAll(c);
		for(Library library : librarys){
			libraryNames.add(library.getName());
		}
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
			if(libraryNames.contains(library.getName())) return false;
			
			librarys.add(library);
			libraryNames.add(library.getName());
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
			if(! librarys.contains(library)) return false;
			librarys.remove(library);
			libraryNames.remove(library.getName());
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
			librarys.clear();
			libraryNames.clear();
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
			return librarys.size();
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
			return librarys.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.LibraryModel#contains(com.dwarfeng.dutil.basic.str.Name)
	 */
	@Override
	public boolean contains(Name name) {
		lock.readLock().lock();
		try{
			if(Objects.isNull(name)) return false;
			return libraryNames.contains(name.getName());
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
			return librarys.iterator();
		}finally {
			lock.readLock().unlock();
		}
	}

}
