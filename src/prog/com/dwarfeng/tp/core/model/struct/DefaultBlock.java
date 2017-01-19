package com.dwarfeng.tp.core.model.struct;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 默认阻挡。
 * <p> 阻挡接口的默认实现。
 * @author DwArFeng
 * @since 1.8
 */
public final class DefaultBlock implements Block{
	
	/**同步锁*/
	private final Lock lock = new ReentrantLock();
	/**同步条件*/
	private final Condition condition = lock.newCondition();
	/**阻挡池*/
	private final BlockPool blockPool = new BlockPool();
	
//	/**阻挡字典，用于查询阻挡关系*/
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
