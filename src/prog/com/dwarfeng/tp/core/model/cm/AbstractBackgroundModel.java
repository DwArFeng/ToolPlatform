package com.dwarfeng.tp.core.model.cm;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.tp.core.model.obv.BackgroundObverser;

/**
 * 抽象后台模型。
 * <p> 后台模型的抽象实现.
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractBackgroundModel implements BackgroundModel{

	/**模型的侦听器集合。*/
	protected final Set<BackgroundObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	/**模型的同步读写锁。*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	/**模型的执行器服务*/
	protected final ExecutorService es;
	
	/**
	 * 新实例。
	 * @param es 指定的执行器服务。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public AbstractBackgroundModel(ExecutorService es) {
		Objects.requireNonNull(es, "入口参数 es 不能为 null。");
		this.es = es;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.BackgroundModel#getExecutorService()
	 */
	@Override
	public ExecutorService getExecutorService() {
		return new ReadOnlyExecutorService(es);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<BackgroundObverser> getObversers() {
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
	public boolean addObverser(BackgroundObverser obverser) {
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
	public boolean removeObverser(BackgroundObverser obverser) {
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
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ReadWriteThreadSafe#getLock()
	 */
	@Override
	public ReadWriteLock getLock() {
		return lock;
	}
	
	private static final class ReadOnlyExecutorService implements ExecutorService{
		
		private final ExecutorService es;
		
		public ReadOnlyExecutorService(ExecutorService es) {
			this.es = es;
		}

		@Override
		public void execute(Runnable command) {
			throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
		}

		@Override
		public void shutdown() {
			throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
		}

		@Override
		public List<Runnable> shutdownNow() {
			throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
		}

		@Override
		public boolean isShutdown() {
			return es.isShutdown();
		}

		@Override
		public boolean isTerminated() {
			return es.isTerminated();
		}

		@Override
		public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
			throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
		}

		@Override
		public <T> Future<T> submit(Callable<T> task) {
			throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
		}

		@Override
		public <T> Future<T> submit(Runnable task, T result) {
			throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
		}

		@Override
		public Future<?> submit(Runnable task) {
			throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
		}

		@Override
		public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
			throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
		}

		@Override
		public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
				throws InterruptedException {
			throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
		}

		@Override
		public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
				throws InterruptedException, ExecutionException {
			throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
		}

		@Override
		public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
				throws InterruptedException, ExecutionException, TimeoutException {
			throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
		}
		
	}

}
