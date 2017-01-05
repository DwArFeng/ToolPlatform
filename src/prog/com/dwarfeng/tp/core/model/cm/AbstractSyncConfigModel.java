package com.dwarfeng.tp.core.model.cm;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;

/**
 * 抽象同步配置模型。
 * <p> 配置模型的线程安全的抽象实现。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractSyncConfigModel implements ConfigModel {

	/**模型的同步读写锁。*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	/**
	 * 获取模型的同步锁。
	 * @return 模型的同步锁。
	 */
	public ReadWriteLock getLock() {
		return lock;
	}

}
