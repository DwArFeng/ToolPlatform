package com.dwarfeng.tp.core.model.cm;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ������Դģ�͡�
 * <p> ��Դģ�͵ĳ���ʵ�֡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class AbstractResourceModel implements ResourceModel{
	
	/**ģ�͵�ͬ����д����*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	/**
	 * ��ȡģ�͵�ͬ������
	 * @return ģ�͵�ͬ������
	 */
	public ReadWriteLock getLock() {
		return lock;
	}

}