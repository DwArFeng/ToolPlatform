package com.dwarfeng.tp.core.model.struct;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.ProcessObverser;

/**
 * �����ࡣ
 * ������һ������ʵ�ֵĹ��̡�
 * <p> ��������Ա����ã�ӵ�н������ԣ����ҿ�����ע��Ĺ۲����㲥������Ϣ��
 * <br> ���̸����Ƿ�֪�����ȿɷ�Ϊȷ�����̺Ͳ�ȷ�����̣��������� {@link #isDeterminate()} ȷ����
 * <p> �۲������̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class Process implements Callable<ProcessResult>, ObverserSet<ProcessObverser>, ExternalReadWriteThreadSafe{
	
	/**�۲�������*/
	protected final Set<ProcessObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	/**ͬ����д��*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	/**��ǰ�Ľ���*/
	private int progress = 0;
	/**�ܽ���*/
	private int totleProgress = 100;
	/**��ǰ�Ľ�����ȷ���Ļ��ǲ�ȷ����*/
	private boolean determinateFlag = true;

	/**
	 * ���ع��̵Ľ��ȡ�
	 * @return ���̵Ľ��ȡ�
	 */
	public final int getProgress() {
		lock.readLock().lock();
		try{
			return progress;
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ù��̵Ľ��ȡ�
	 * <p> ����Ӧ����ѭ���¹淶�� <code> 0 &lt= progress &lt= totleProgress </code>
	 * <p> �ù��̽��Զ��Ľ������Ϲ淶����ڲ���ת��Ϊ��ӽ��ĺ���ֵ��
	 * @param progress ָ���Ľ��ȡ�
	 */
	protected final void setProgress(int progress) {
		lock.writeLock().lock();
		try{
			progress = Math.max(0, progress);
			progress = Math.min(progress, totleProgress);
			this.progress = progress;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * ���ع��̵��ܽ��ȡ�
	 * @return ���̵��ܽ��ȡ�
	 */
	public final int getTotleProgress() {
		lock.readLock().lock();
		try{
			return totleProgress;
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ù����ܵĽ��ȡ�
	 * <p> ����Ӧ����ѭ���¹淶�� <code> 0 &lt= progress &lt= totleProgress </code>
	 * <p> �ù��̽��Զ��Ľ������Ϲ淶����ڲ���ת��Ϊ��ӽ��ĺ���ֵ��
	 * @param totleProgress ָ�����ܽ��ȡ�
	 */
	protected final void setProgressTotle(int totleProgress) {
		lock.writeLock().lock();
		try{
			totleProgress = Math.max(0, totleProgress);
			totleProgress = Math.max(progress, totleProgress);
			this.totleProgress = totleProgress;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * ���ظù�����ȷ�����̻��ǲ�ȷ�����̡�
	 * @return �ù����Ƿ�Ϊȷ�����̡�
	 */
	public final boolean isDeterminate() {
		lock.readLock().lock();
		try{
			return determinateFlag;
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���øù����Ƿ�Ϊȷ�����̡�
	 * @param determinateFlag �ù����Ƿ�Ϊȷ�����̡�
	 */
	protected final void setDeterminate(boolean determinateFlag) {
		lock.writeLock().lock();
		try{
			this.determinateFlag = determinateFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ReadWriteThreadSafe#getLock()
	 */
	@Override
	public final ReadWriteLock getLock() {
		return this.lock;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public final Set<ProcessObverser> getObversers() {
		lock.readLock().lock();
		try{
			return obversers;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public final boolean addObverser(ProcessObverser obverser) {
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
	public final boolean removeObverser(ProcessObverser obverser) {
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
	public final void clearObverser() {
		lock.writeLock().lock();
		try{
			obversers.clear();
		}finally {
			lock.writeLock().unlock();
		}
	}
	
}
