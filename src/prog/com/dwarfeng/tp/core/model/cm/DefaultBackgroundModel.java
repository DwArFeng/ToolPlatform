package com.dwarfeng.tp.core.model.cm;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;

import com.dwarfeng.tp.core.model.obv.BackgroundObverser;
import com.dwarfeng.tp.core.model.obv.ProcessObverser;
import com.dwarfeng.tp.core.model.struct.Process;

/**
 * 默认后台模型。
 * <p> 后台模型接口的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultBackgroundModel extends AbstractBackgroundModel {
	
	private final Condition condition = lock.writeLock().newCondition();
	private final Set<Process> processes = new HashSet<>();
	private final Queue<Process> finishedProcesses = new ArrayDeque<>();
	private final ProcessObverser processObverser = new ProcessObverser() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ProcessObverser#fireTotleProgressChanged(com.dwarfeng.tp.core.model.struct.Process, int, int)
		 */
		@Override
		public void fireTotleProgressChanged(Process process, int oldValue, int newValue) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireProcessTotleProgressChanged(process, oldValue, newValue);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ProcessObverser#fireThrowableChanged(com.dwarfeng.tp.core.model.struct.Process, java.lang.Throwable, java.lang.Throwable)
		 */
		@Override
		public void fireThrowableChanged(Process process, Throwable oldValue, Throwable newValue) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireProcessThrowableChanged(process, oldValue, newValue);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ProcessObverser#fireCancelableChanged(com.dwarfeng.tp.core.model.struct.Process, boolean, boolean)
		 */
		@Override
		public void fireCancelableChanged(Process process, boolean oldValue, boolean newValue) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireProcessCancelableChanged(process, oldValue, newValue);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ProcessObverser#fireProgressChanged(com.dwarfeng.tp.core.model.struct.Process, int, int)
		 */
		@Override
		public void fireProgressChanged(Process process, int oldValue, int newValue) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireProcessProgressChanged(process, oldValue, newValue);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ProcessObverser#fireMessageChanged(com.dwarfeng.tp.core.model.struct.Process, java.lang.String, java.lang.String)
		 */
		@Override
		public void fireMessageChanged(Process process, String oldValue, String newValue) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireProcessMessageChanged(process, oldValue, newValue);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ProcessObverser#fireDone(com.dwarfeng.tp.core.model.struct.Process)
		 */
		@Override
		public void fireDone(Process process) {
			lock.writeLock().lock();
			try{
				finishedProcesses.offer(process);
				condition.signalAll();
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireProcessDone(process);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ProcessObverser#fireDeterminateChanged(com.dwarfeng.tp.core.model.struct.Process, boolean, boolean)
		 */
		@Override
		public void fireDeterminateChanged(Process process, boolean oldValue, boolean newValue) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireProcessDeterminateChanged(process, oldValue, newValue);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ProcessObverser#fireCanceled(com.dwarfeng.tp.core.model.struct.Process)
		 */
		@Override
		public void fireCanceled(Process process) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireProcessCanceled(process);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
	};
	
	/**
	 * 新实例。
	 * @param es 指定的执行器服务。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultBackgroundModel() {
		super(Executors.newCachedThreadPool());
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.BackgroundModel#submit(com.dwarfeng.tp.core.model.struct.Process)
	 */
	@Override
	public boolean submit(Process process) {
		lock.writeLock().lock();
		try{
			if(es.isShutdown()) return false;
			if(Objects.isNull(process)) return false;
			if(processes.contains(process)) return false;
			
			process.addObverser(processObverser);
			processes.add(process);
			es.submit(process);
			fireProcessAdded(process);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireProcessAdded(Process process) {
		for(BackgroundObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireProcessAdded(process);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.BackgroundModel#submitAll(java.util.Collection)
	 */
	@Override
	public boolean submitAll(Collection<? extends Process> c) {
		Objects.requireNonNull(c, "入口参数 c 不能为 nul。");
		
		lock.writeLock().lock();
		try{
			boolean aFlag = false;
			for(Process process : c){
				if(submit(process)) aFlag = true;
			}
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.BackgroundModel#contains(com.dwarfeng.tp.core.model.struct.Process)
	 */
	@Override
	public boolean contains(Process process) {
		lock.readLock().lock();
		try{
			return processes.contains(process);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.BackgroundModel#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<Process> c) {
		Objects.requireNonNull(c, "入口参数 c 不能为 null。");
		
		lock.readLock().lock();
		try{
			return processes.containsAll(c);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.BackgroundModel#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		lock.readLock().lock();
		try{
			return processes.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * 返回该后台模型的过程迭代器。
	 * <p> 注意，该集合不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 该后台模型的过程迭代器。
	 */
	@Override
	public Iterator<Process> iterator() {
		lock.readLock().lock();
		try{
			return processes.iterator();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.BackgroundModel#hasFinished()
	 */
	@Override
	public boolean hasFinished() {
		lock.readLock().lock();
		try{
			return !finishedProcesses.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.BackgroundModel#take()
	 */
	@Override
	public Process takeFinished() throws InterruptedException {
		lock.writeLock().lock();
		try{
			while(finishedProcesses.isEmpty()){
				condition.await();
			}
			Process process = finishedProcesses.peek();
			remove(process);
			return process;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void remove(Process process) {
		finishedProcesses.remove(process);
		processes.remove(process);
		fireProcessRemoved(process);
	}

	private void fireProcessRemoved(Process process) {
		for(BackgroundObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireProcessRemoved(process);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.BackgroundModel#clearFinished()
	 */
	@Override
	public boolean clearFinished() {
		lock.writeLock().lock();
		try{
			if(finishedProcesses.isEmpty()) return false;
			
			for(Process process : finishedProcesses){
				remove(process);
			}
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.BackgroundModel#shutdown()
	 */
	@Override
	public void shutdown() {
		lock.writeLock().lock();
		try{
			es.shutdown();
		}finally {
			lock.writeLock().unlock();
		}
	}
	
}
