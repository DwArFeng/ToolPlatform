package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.threads.NumberedThreadFactory;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.cm.ToolRuntimeModel;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

public class DefaultExitedRunningToolTaker implements ExitedRunningToolTaker {
	
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("tool_runtime_taker");
	
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Condition condition = lock.writeLock().newCondition();
	private final ToolRuntimeModel toolRuntimeModel;
	private final Thread thread = THREAD_FACTORY.newThread(new Runnable() {
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			next:
			while(runFlag){
				CT.trace(pauseFlag);
				lock.writeLock().lock();
				try{
					while(pauseFlag){
						try {
							condition.await();
						} catch (InterruptedException ignore) {
							//����ִ��
						}
						//�������Ϊ���رն�ִ�У���ֱ���˳��̡߳�
						if(!runFlag) return;
					}
				}finally {
					lock.writeLock().unlock();
				}
				
				try {
					RunningTool runningTool = toolRuntimeModel.takeExited();
					if(pauseFlag) continue next;
					String format = null;
					lock.readLock().lock();
					try{
						format = mutilang.getString(LoggerStringKey.ExitToolRuntimeTaker_1.getName());
					}finally {
						lock.readLock().unlock();
					}
					logger.info(String.format(format, runningTool.getName(), runningTool.getExitCode()));
				
				}catch (Exception e) {
					if(!(e instanceof InterruptedException)){
						String str = null;
						try{
							str = mutilang.getString(LoggerStringKey.FinishedFlowTaker_3.getName());
						}catch (Exception e1) {
							Mutilang tempMutilang = ToolPlatformUtil.newDefaultLoggerMutilang();
							str = tempMutilang.getString(LoggerStringKey.FinishedFlowTaker_4.getName());
							logger.warn(str, e1);
							str = tempMutilang.getString(LoggerStringKey.FinishedFlowTaker_3.getName());
						}
						logger.warn(str, e);
					}
				}
			}
		}
	});
	
	
	private Logger logger;
	private Mutilang mutilang;
	private boolean pauseFlag;
	
	private boolean runFlag = true;
	
	
	
	/**
	 * ��ʵ����
	 * @param toolRuntimeModel ָ���ĺ�̨ģ�͡�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultExitedRunningToolTaker(ToolRuntimeModel toolRuntimeModel) {
		this(toolRuntimeModel, ToolPlatformUtil.newDefaultLogger(), ToolPlatformUtil.newDefaultLoggerMutilang(), true);
	}

	/**
	 * ��ʵ����
	 * @param toolRuntimeModel ָ���ĺ�̨ģ�͡�
	 * @param logger ָ���ļ�¼����
	 * @param mutilang ָ���Ķ����Խӿڡ�
	 * @param isPause �Ƿ���ͣ
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultExitedRunningToolTaker(ToolRuntimeModel toolRuntimeModel, Logger logger, Mutilang mutilang, boolean pauseFlag) {
		Objects.requireNonNull(toolRuntimeModel, "��ڲ��� backgroundModel ����Ϊ null��");
		Objects.requireNonNull(logger, "��ڲ��� logger ����Ϊ null��");
		Objects.requireNonNull(mutilang, "��ڲ��� mutilang ����Ϊ null��");
		
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
		Objects.requireNonNull(mutilang, "��ڲ��� mutilang ����Ϊ null��");
		
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
		Objects.requireNonNull(logger, "��ڲ��� logger ����Ϊ null��");
		
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

}
