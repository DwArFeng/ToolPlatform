package com.dwarfeng.tp.core.model.cm;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 抽象资源模型。
 * <p> 资源模型的抽象实现。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class AbstractResourceModel implements ResourceModel{
	
	/**模型的同步读写锁。*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ReadWriteThreadSafe#getLock()
	 */
	@Override
	public ReadWriteLock getLock() {
		return lock;
	}

}
