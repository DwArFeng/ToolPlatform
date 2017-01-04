package com.dwarfeng.tp.core.model.cm;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.tp.core.model.obv.MutilangObverser;
import com.dwarfeng.tp.core.model.struct.MutilangInfo;

/**
 * Ĭ�϶�����ģ�͡�
 * <p> ������ģ�ͽӿڵ�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultMutilangModel extends AbstractMutilangModel {
	
	private final Map<Locale, MutilangInfo> delegate = new HashMap<>();
	
	private File direction;
	
	/**
	 * ��ʵ����
	 */
	public DefaultMutilangModel(){}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.vim.MutilangModel#getDir()
	 */
	@Override
	public File getDirection() {
		lock.readLock().lock();
		try{
			return direction;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.vim.MutilangModel#setDir(java.io.File)
	 */
	@Override
	public boolean setDircetion(File direction) {
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.direction, direction)) return false;
			File oldOne = this.direction;
			this.direction = direction;
			fireDirectionChanged(oldOne, direction);
			return true;
		}finally{
			lock.writeLock().unlock();
		}
	}
	
	private void fireDirectionChanged(File oldOne, File newOne){
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDirectionChanged(oldOne, newOne);
		}
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
	public MutilangInfo get(Object key) {
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
	public MutilangInfo put(Locale key, MutilangInfo value) {
		Objects.requireNonNull(key, "��ڲ��� key ����Ϊ null��");
		Objects.requireNonNull(value, "��ڲ��� value ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			if(containsKey(key)){
				MutilangInfo oldOne = get(key);
				fireEntryChanged(key, oldOne, value);
			}else{
				fireEntryAdded(key, value);
			}
			
			return delegate.put(key, value);
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireEntryAdded(Locale locale, MutilangInfo info) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryAdded(locale, info);
		}
	}

	private void fireEntryChanged(Locale locale, MutilangInfo oldOne, MutilangInfo newOne) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryChanged(locale, oldOne, newOne);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public MutilangInfo remove(Object key) {
		lock.writeLock().lock();
		try{
			if(containsKey(key)){
				fireEntryRemoved((Locale) key);
			}
			return delegate.remove(key);
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireEntryRemoved(Locale locale) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryRemoved(locale);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends Locale, ? extends MutilangInfo> m) {
		Objects.requireNonNull(m, "��ڲ��� m ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			for(Map.Entry<? extends Locale, ? extends MutilangInfo> entry : m.entrySet()){
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
			this.direction = null;
			delegate.clear();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCleared() {
		for(MutilangObverser obverser : obversers){
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
	public Set<Locale> keySet() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(delegate.keySet());
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
	public Collection<MutilangInfo> values() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableCollection(delegate.values());
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
	public Set<java.util.Map.Entry<Locale, MutilangInfo>> entrySet() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(delegate.entrySet());
		}finally {
			lock.readLock().unlock();
		}
	}

}
