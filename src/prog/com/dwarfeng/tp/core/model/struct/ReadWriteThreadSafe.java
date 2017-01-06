package com.dwarfeng.tp.core.model.struct;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * 读写线程安全。
 * @author DwArFeng
 * @since 1.8
 */
public interface ReadWriteThreadSafe {

	/**
	 * 获取该接口的读写锁。
	 * @return 读写锁。
	 */
	public ReadWriteLock getLock();
}
