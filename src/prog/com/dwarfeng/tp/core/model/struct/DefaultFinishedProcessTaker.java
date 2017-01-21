package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.cm.BackgroundModel;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

/**
 * 默认完成过程取出器。
 * <p> 完成过程取出器的默认实现。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultFinishedProcessTaker implements FinishedProcessTaker {
	
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("finished_process_taker");
	
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final BackgroundModel backgroundModel;
	private final Thread thread = THREAD_FACTORY.newThread(new Runnable() {
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			while(runFlag){
				try {
					Process process = backgroundModel.takeFinished();
					String message = process.getMessage();
					Throwable throwable = process.getThrowable();
					String format = "%s_%s";
					String str = null;
					if(Objects.isNull(throwable)){
						lock.readLock().lock();
						try{
							str = mutilang.getString(LoggerStringKey.FinishedProcessTaker_1.getName());
						}finally {
							lock.readLock().unlock();
						}
						logger.info(String.format(format, str, message));
					}else{
						lock.readLock().lock();
						try{
							str = mutilang.getString(LoggerStringKey.FinishedProcessTaker_2.getName());
						}finally {
							lock.readLock().unlock();
						}
						logger.warn(String.format(format, str, message), throwable);
					}
				}catch (Exception e) {
					if(!(e instanceof InterruptedException)){
						String str = null;
						lock.readLock().lock();
						try{
							str = mutilang.getString(LoggerStringKey.FinishedProcessTaker_3.getName());
						}catch (Exception e1) {
							Mutilang tempMutilang = ToolPlatformUtil.newDefaultLoggerMutilang();
							str = tempMutilang.getString(LoggerStringKey.FinishedProcessTaker_4.getName());
							logger.warn(str, e1);
							str = tempMutilang.getString(LoggerStringKey.FinishedProcessTaker_3.getName());
						}
						logger.warn(str, e);
					}
				}
			}
		}
	});
	
	
	private Logger logger;
	private Mutilang mutilang;
	
	private boolean runFlag = true;
	
	/**
	 * 新实例。
	 * @param backgroundModel 指定的后台模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultFinishedProcessTaker(BackgroundModel backgroundModel) {
		this(backgroundModel, ToolPlatformUtil.newDefaultLogger(), ToolPlatformUtil.newDefaultLoggerMutilang());
	}
	
	/**
	 * 新实例。
	 * @param backgroundModel 指定的后台模型。
	 * @param logger 指定的记录器。
	 * @param mutilang 指定的多语言接口。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultFinishedProcessTaker(BackgroundModel backgroundModel, Logger logger, Mutilang mutilang) {
		Objects.requireNonNull(backgroundModel, "入口参数 backgroundModel 不能为 null。");
		Objects.requireNonNull(logger, "入口参数 logger 不能为 null。");
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.backgroundModel = backgroundModel;
		this.logger = logger;
		this.mutilang = mutilang;
		
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
	 * @see com.dwarfeng.tp.core.model.struct.FinishedProcessTaker#getLogger()
	 */
	@Override
	public Logger getLogger() {
		lock.readLock().lock();
		try{
			return this.logger;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.FinishedProcessTaker#setLogger(com.dwarfeng.tp.core.model.struct.Logger)
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
	 * @see com.dwarfeng.tp.core.model.struct.FinishedProcessTaker#getBackground()
	 */
	@Override
	public BackgroundModel getBackgroundModel() {
		lock.readLock().lock();
		try{
			return this.backgroundModel;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.FinishedProcessTaker#shutdown()
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

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangSupported#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		lock.readLock().lock();
		try{
			return this.mutilang;
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

}
