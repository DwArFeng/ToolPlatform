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
 * 过程类。
 * 代表着一个可以实现的过程。
 * <p> 过程类可以被调用，拥有进度属性，并且可以向注册的观察器广播进度信息。
 * <br> 过程根据是否知道进度可分为确定过程和不确定过程，该属性由 {@link #isDeterminate()} 确定。
 * <p> 观察器是线程安全的。
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class Process implements Callable<ProcessResult>, ObverserSet<ProcessObverser>, ExternalReadWriteThreadSafe{
	
	/**观察器集合*/
	protected final Set<ProcessObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	/**同步读写锁*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	/**当前的进度*/
	private int progress = 0;
	/**总进度*/
	private int totleProgress = 100;
	/**当前的进度是确定的还是不确定的*/
	private boolean determinateFlag = true;

	/**
	 * 返回过程的进度。
	 * @return 过程的进度。
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
	 * 设置过程的进度。
	 * <p> 进度应该遵循如下规范： <code> 0 &lt= progress &lt= totleProgress </code>
	 * <p> 该过程将自动的将不符合规范的入口参数转换为最接近的合理值。
	 * @param progress 指定的进度。
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
	 * 返回过程的总进度。
	 * @return 过程的总进度。
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
	 * 设置过程总的进度。
	 * <p> 进度应该遵循如下规范： <code> 0 &lt= progress &lt= totleProgress </code>
	 * <p> 该过程将自动的将不符合规范的入口参数转换为最接近的合理值。
	 * @param totleProgress 指定的总进度。
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
	 * 返回该过程是确定过程还是不确定过程。
	 * @return 该过程是否为确定过程。
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
	 * 设置该过程是否为确定过程。
	 * @param determinateFlag 该过程是否为确定过程。
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
