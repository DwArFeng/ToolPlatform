package com.dwarfeng.tp.core.model.struct;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ĭ���赲��
 * <p> �赲�ӿڵ�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 1.8
 */
public final class DefaultBlock implements Block{
	
	/**ͬ����*/
	private final Lock lock = new ReentrantLock();
	/**ͬ������*/
	private final Condition condition = lock.newCondition();
	/**�赲��*/
	private final BlockPool blockPool = new BlockPool();
	
//	/**�赲�ֵ䣬���ڲ�ѯ�赲��ϵ*/
//	private final Map<String, Set<String>> dictionary;
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Block#block(java.lang.String)
	 */
	@Override
	public void block(String key) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Block#unblock(java.lang.String)
	 */
	@Override
	public void unblock(String key) {
		// TODO Auto-generated method stub
		
	}

	
	
	private final  class BlockPool {
	
	}

}
