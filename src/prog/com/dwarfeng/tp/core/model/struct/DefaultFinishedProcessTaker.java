package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.cm.BackgroundModel;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

/**
 * Ĭ����ɹ���ȡ������
 * <p> ��ɹ���ȡ������Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 1.8
 */
public class DefaultFinishedProcessTaker implements FinishedProcessTaker {
	
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final BackgroundModel backgroundModel;
	private final Thread thread = new Thread(new Runnable() {
		
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
							str = mutilang.getString(LoggerStringKey.FinishedProcessTaker_1);
						}finally {
							lock.readLock().unlock();
						}
						logger.info(String.format(format, str, message));
					}else{
						lock.readLock().lock();
						try{
							str = mutilang.getString(LoggerStringKey.FinishedProcessTaker_2);
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
							str = mutilang.getString(LoggerStringKey.FinishedProcessTaker_3);
						}catch (Exception e1) {
							Mutilang tempMutilang = ToolPlatformUtil.newInitialLoggerMutilang();
							str = tempMutilang.getString(LoggerStringKey.FinishedProcessTaker_4);
							logger.warn(str, e1);
							str = tempMutilang.getString(LoggerStringKey.FinishedProcessTaker_3);
						}
						logger.warn(str, e);
					}
				}
			}
		}
		
	}, "Finished-Process-Taker");
	
	
	private Logger logger;
	private Mutilang mutilang;
	
	private boolean runFlag = true;
	
	/**
	 * ��ʵ����
	 * @param backgroundModel ָ���ĺ�̨ģ�͡�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultFinishedProcessTaker(BackgroundModel backgroundModel) {
		this(backgroundModel, ToolPlatformUtil.newInitialLogger(), ToolPlatformUtil.newInitialLoggerMutilang());
		thread.start();
	}
	
	/**
	 * ��ʵ����
	 * @param backgroundModel ָ���ĺ�̨ģ�͡�
	 * @param logger ָ���ļ�¼����
	 * @param mutilang ָ���Ķ����Խӿڡ�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultFinishedProcessTaker(BackgroundModel backgroundModel, Logger logger, Mutilang mutilang) {
		Objects.requireNonNull(backgroundModel, "��ڲ��� backgroundModel ����Ϊ null��");
		Objects.requireNonNull(logger, "��ڲ��� logger ����Ϊ null��");
		Objects.requireNonNull(mutilang, "��ڲ��� mutilang ����Ϊ null��");
		
		this.backgroundModel = backgroundModel;
		this.logger = logger;
		this.mutilang = mutilang;
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

}
