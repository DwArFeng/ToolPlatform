package com.dwarfeng.tp.core.model.cm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Condition;

import com.dwarfeng.dutil.basic.prog.RuntimeState;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.basic.threads.NumberedThreadFactory;
import com.dwarfeng.tp.core.model.obv.RunningToolObverser;
import com.dwarfeng.tp.core.model.obv.ToolRuntimeObverser;
import com.dwarfeng.tp.core.model.struct.RunningTool;

public final class DefaultToolRuntimeModel extends AbstractToolRuntimeModel{
	
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("tool_runtime");

	
	private final Condition condition = lock.writeLock().newCondition();
	private final List<RunningTool> runningTools = new ArrayList<>();
	private final List<String> runningToolNames = new ArrayList<>();
	private final Queue<RunningTool> exitedRunningTools = new ArrayDeque<>();
	private final RunningToolObverser runningToolObverser = new RunningToolObverser() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.RunningToolObverser#fireStarted(com.dwarfeng.tp.core.model.struct.RunningTool)
		 */
		@Override
		public void fireStarted(RunningTool runningTool) {
			lock.writeLock().lock();
			try{
				for(ToolRuntimeObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireRunningToolStarted(runningTool);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.RunningToolObverser#fireExited(com.dwarfeng.tp.core.model.struct.RunningTool)
		 */
		@Override
		public void fireExited(RunningTool runningTool) {
			lock.writeLock().lock();
			try{
				exitedRunningTools.offer(runningTool);
				condition.signalAll();
				for(ToolRuntimeObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireRunningToolExited(runningTool);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
	};
	
	private boolean addRejectFlag = false;
	
	/**
	 * 新实例。
	 */
	public DefaultToolRuntimeModel() {
		this(new ArrayList<>());
	}
	
	/**
	 * 新实例。
	 * @param c 指定的初始值。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultToolRuntimeModel(Collection<RunningTool> c){
		super(Executors.newCachedThreadPool(THREAD_FACTORY));
		Objects.requireNonNull(c, "入口参数 c 不能为 null。");
		runningTools.addAll(c);
		for(RunningTool runningTool : runningTools){
			runningToolNames.add(runningTool.getName());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#add(com.dwarfeng.tp.core.model.struct.RunningTool)
	 */
	@Override
	public boolean add(RunningTool runningTool) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(runningTool)) return false;
			if(addRejectFlag) return false;
			if(! runningTool.getRuntimeState().equals(RuntimeState.NOT_START)) return false;
			
			runningTool.addObverser(runningToolObverser);
			runningTools.add(runningTool);
			runningToolNames.add(runningTool.getName());
			fireFlowAdded(runningTool);
			
			es.submit(new InnerRunnable(runningTool));
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireFlowAdded(RunningTool runningTool) {
		for(ToolRuntimeObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireRunningToolAdded(runningTool);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#contains(com.dwarfeng.dutil.basic.str.Name)
	 */
	@Override
	public boolean contains(Name name) {
		lock.readLock().lock();
		try{
			return runningToolNames.contains(name.getName());
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#numberOf(com.dwarfeng.dutil.basic.str.Name)
	 */
	@Override
	public int numberOf(Name name) {
		if(name == null) return 0;
		
		lock.readLock().lock();
		try{
			int count = 0;
			for(String str : runningToolNames){
				if(str.equals(name.getName())) count ++;
			}
			return count;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#size()
	 */
	@Override
	public int size() {
		lock.readLock().lock();
		try{
			return runningTools.size();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		lock.readLock().lock();
		try{
			return runningTools.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#hasNotExited()
	 */
	@Override
	public boolean hasNotExited() {
		lock.readLock().lock();
		try{
			return runningTools.size() - exitedRunningTools.size() > 0;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#hasExited()
	 */
	@Override
	public boolean hasExited() {
		lock.readLock().lock();
		try{
			return !exitedRunningTools.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#takeExited()
	 */
	@Override
	public RunningTool takeExited() throws InterruptedException {
		lock.writeLock().lock();
		try{
			while(exitedRunningTools.isEmpty()){
				condition.await();
			}
			RunningTool runningTool = exitedRunningTools.peek();
			innerRemove(runningTool);
			return runningTool;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#remove(com.dwarfeng.tp.core.model.struct.RunningTool)
	 */
	@Override
	public boolean remove(RunningTool runningTool) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(runningTool)) return false;
			if(! runningTool.getRuntimeState().equals(RuntimeState.ENDED)) return false;
			if(! contains(runningTool)) return false;
			innerRemove(runningTool);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void innerRemove(RunningTool runningTool) {
		exitedRunningTools.remove(runningTool);
		int index = runningTools.indexOf(runningTool);
		runningTools.remove(runningTool);
		runningToolNames.remove(index);
		fireFlowRemoved(runningTool);
	}

	private void fireFlowRemoved(RunningTool runningTool) {
		for(ToolRuntimeObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireRunningToolRemoved(runningTool);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#clearExited()
	 */
	@Override
	public boolean clearExited() {
		lock.writeLock().lock();
		try{
			if(exitedRunningTools.isEmpty()) return false;
			
			for(RunningTool runningTool : exitedRunningTools){
				innerRemove(runningTool);
			}
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 返回该工具运行时模型的过程迭代器。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 该工具运行时模型的过程迭代器。
	 */
	@Override
	public Iterator<RunningTool> iterator() {
		lock.readLock().lock();
		try{
			return runningTools.iterator();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#isAddRejected()
	 */
	@Override
	public boolean isAddRejected() {
		lock.readLock().lock();
		try{
			return addRejectFlag;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#setAddRejected(boolean)
	 */
	@Override
	public boolean setAddRejected(boolean aFlag) {
		lock.writeLock().lock();
		try{
			if(addRejectFlag == aFlag) return false;
			addRejectFlag = aFlag;
			fireAddRejectChanged(aFlag);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireAddRejectChanged(boolean newValue) {
		for(ToolRuntimeObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireAddRejectChanged(newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolRuntimeModel#shutdown()
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
	
	private static final class InnerRunnable implements Runnable{

		private final RunningTool runningTool;
		
		public InnerRunnable(RunningTool runningTool) {
			this.runningTool = runningTool;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				runningTool.start();
			} catch (InterruptedException ignore) {
				//中断即退出。
				return;
			}
		}
		
	}

}
