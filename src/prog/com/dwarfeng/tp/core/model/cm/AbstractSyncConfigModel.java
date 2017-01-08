package com.dwarfeng.tp.core.model.cm;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ����ͬ������ģ�͡�
 * <p> ����ģ�͵��̰߳�ȫ�ĳ���ʵ�֡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractSyncConfigModel implements SyncConfigModel {

	/**ģ�͵�ͬ����д����*/
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
