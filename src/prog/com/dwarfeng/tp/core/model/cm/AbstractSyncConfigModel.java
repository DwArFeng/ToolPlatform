package com.dwarfeng.tp.core.model.cm;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.dutil.develop.cfg.ConfigModelObverser;

/**
 * ����ͬ������ģ�͡�
 * <p> ����ģ�͵��̰߳�ȫ�ĳ���ʵ�֡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractSyncConfigModel implements ConfigModel {

	/**ģ�͵����������ϡ�*/
	protected final Set<ConfigModelObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	/**ģ�͵�ͬ����д����*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<ConfigModelObverser> getObversers() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(obversers);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(ConfigModelObverser obverser) {
		lock.writeLock().lock();
		try{
			return obversers.add(obverser);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(ConfigModelObverser obverser) {
		lock.writeLock().lock();
		try{
			return obversers.remove(obverser);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#clearObverser()
	 */
	@Override
	public void clearObverser() {
		lock.writeLock().lock();
		try{
			obversers.clear();
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * ��ȡģ�͵�ͬ������
	 * @return ģ�͵�ͬ������
	 */
	public ReadWriteLock getLock() {
		return lock;
	}

}
