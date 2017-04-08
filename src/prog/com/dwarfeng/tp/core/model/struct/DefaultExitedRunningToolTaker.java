package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.dutil.basic.threads.NumberedThreadFactory;
import com.dwarfeng.tp.core.model.cm.ToolRuntimeModel;
import com.dwarfeng.tp.core.model.eum.LoggerStringKey;
import com.dwarfeng.tp.core.util.Constants;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

public class DefaultExitedRunningToolTaker implements ExitedRunningToolTaker {
	
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("tool_runtime_taker");
	
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Condition condition = lock.writeLock().newCondition();
	private final ToolRuntimeModel toolRuntimeModel;
	private final Thread thread = THREAD_FACTORY.newThread(new Taker());
	
	private Logger logger;
	private Mutilang mutilang;
	private boolean pauseFlag;
	
	private boolean runFlag = true;
	
	
	
	/**
	 * 新实例。
	 * @param toolRuntimeModel 指定的后台模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultExitedRunningToolTaker(ToolRuntimeModel toolRuntimeModel) {
		this(toolRuntimeModel, ToolPlatformUtil.newDefaultLogger(), Constants.getDefaultLoggerMutilang(), true);
	}

	/**
	 * 新实例。
	 * @param toolRuntimeModel 指定的后台模型。
	 * @param logger 指定的记录器。
	 * @param mutilang 指定的多语言接口。
	 * @param isPause 是否暂停
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultExitedRunningToolTaker(ToolRuntimeModel toolRuntimeModel, Logger logger, Mutilang mutilang, boolean pauseFlag) {
		Objects.requireNonNull(toolRuntimeModel, "入口参数 backgroundModel 不能为 null。");
		Objects.requireNonNull(logger, "入口参数 logger 不能为 null。");
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.toolRuntimeModel = toolRuntimeModel;
		this.logger = logger;
		this.mutilang = mutilang;
		this.pauseFlag = pauseFlag;
		
		thread.start();
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
	 * @see com.dwarfeng.tp.core.model.struct.MutilangSupported#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		lock.readLock().lock();
		try{
			return mutilang;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangSupported#setMutilang(com.dwarfeng.tp.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setMutilang(Mutilang mutilang) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.mutilang, mutilang)) return false;
			this.mutilang = mutilang;
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ExitedRunningToolTaker#getLogger()
	 */
	@Override
	public Logger getLogger() {
		lock.readLock().lock();
		try{
			return logger;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ExitedRunningToolTaker#setLogger(com.dwarfeng.tp.core.model.struct.Logger)
	 */
	@Override
	public boolean setLogger(Logger logger) {
		Objects.requireNonNull(logger, "入口参数 logger 不能为 null。");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.logger, logger)) return false;
			this.logger = logger;
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ExitedRunningToolTaker#isPause()
	 */
	@Override
	public boolean isPause() {
		lock.readLock().lock();
		try{
			return pauseFlag;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ExitedRunningToolTaker#setPause(boolean)
	 */
	@Override
	public boolean setPause(boolean aFlag) {
		lock.writeLock().lock();
		try{
			if(pauseFlag == aFlag) return false;
			pauseFlag = aFlag;
			condition.signalAll();
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ExitedRunningToolTaker#getToolRuntimeModel()
	 */
	@Override
	public ToolRuntimeModel getToolRuntimeModel() {
		return toolRuntimeModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ExitedRunningToolTaker#shutdown()
	 */
	@Override
	public void shutdown() {
		lock.writeLock().lock();
		try{
			this.runFlag = false;
			this.thread.interrupt();
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private boolean isRun(){
		lock.readLock().lock();
		try{
			return runFlag;
		}finally {
			lock.readLock().unlock();
		}
	}

	private final class Taker implements Runnable{

		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			next:
			while(isRun()){
				try {
					lock.writeLock().lock();
					try{
						while(isPause()){
							condition.await();
							//如果是因为被关闭而执行，则直接退出线程。
							if(! isRun()) return;
						}
					}finally {
						lock.writeLock().unlock();
					}
					
					RunningTool runningTool = toolRuntimeModel.takeExited();
					if(isPause()) continue next;
					
					String format = null;
					String name = null;
					int exitCode = 0;
					lock.readLock().lock();
					try{
						format = mutilang.getString(LoggerStringKey.ExitToolRuntimeTaker_1.getName());
						name = runningTool.getName();
						exitCode = runningTool.getExitCode();
					}finally {
						lock.readLock().unlock();
					}
					logger.info(String.format(format, name, exitCode));
				
				}catch (Exception e) {
					if(!(e instanceof InterruptedException)){
						String str = null;
						lock.readLock().lock();
						try{
							str = mutilang.getString(LoggerStringKey.FinishedFlowTaker_3.getName());
						}catch (Exception e1) {
							Mutilang tempMutilang = Constants.getDefaultLoggerMutilang();
							str = tempMutilang.getString(LoggerStringKey.FinishedFlowTaker_4.getName());
							logger.warn(str, e1);
							str = tempMutilang.getString(LoggerStringKey.FinishedFlowTaker_3.getName());
						}finally {
							lock.readLock().unlock();
						}
						logger.warn(str, e);
					}
				}
			}
		}
		
	}

}
