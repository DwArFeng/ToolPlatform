package com.dwarfeng.tp.core.model.cm;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 抽象同步配置模型。
 * <p> 配置模型的线程安全的抽象实现。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractSyncConfigModel implements SyncConfigModel {

	/**模型的同步读写锁。*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ReadWriteThreadSafe#getLock()
	 */
	@Override
	public final ReadWriteLock getLock() {
		return lock;
	}

}
