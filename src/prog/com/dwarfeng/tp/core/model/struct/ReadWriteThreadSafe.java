package com.dwarfeng.tp.core.model.struct;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * ��д�̰߳�ȫ��
 * @author DwArFeng
 * @since 1.8
 */
public interface ReadWriteThreadSafe {

	/**
	 * ��ȡ�ýӿڵĶ�д����
	 * @return ��д����
	 */
	public ReadWriteLock getLock();
}
