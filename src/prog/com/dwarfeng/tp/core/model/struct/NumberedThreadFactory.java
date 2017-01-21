package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 编号线程工厂。
 * <p> 该线程工厂提供流水编号的线程，其内部维护着一个编号。
 * 当新的线程被请求时，该工厂返回的线程的名称由编号和前缀组成。
 * @author  DwArFeng
 * @since 0.0.3-beta
 */
public class NumberedThreadFactory implements ThreadFactory {
	
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	
	private final ThreadGroup group;
	
	private final String prefix;
	private final boolean daemonFlag;
	private final int priority;
	
	/**
	 * 生成一个默认的编号线程。
	 * <p> 线程不是守护线程，且具有标准的优先级。
	 * @param prefix 指定的前缀。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public NumberedThreadFactory(String prefix) {
		this(prefix, false, Thread.NORM_PRIORITY);
	}
	
	/**
	 * 生成一个具有指定前缀，指定守护线程标志，指定优先级的编号线程工厂。
	 * @param prefix 指定的前缀。
	 * @param daemonFlag 指定的守护线程标志。
	 * @param priority 指定的优先级。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public NumberedThreadFactory(String prefix, boolean daemonFlag, int priority) {
		Objects.requireNonNull(prefix, "入口参数 prefiex 不能为 null。");
		
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                              Thread.currentThread().getThreadGroup();
		
		this.prefix = prefix;
		this.daemonFlag = daemonFlag;
		this.priority = priority;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
	 */
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, prefix + "-" + threadNumber.getAndIncrement(), 0);
		t.setDaemon(daemonFlag);
		t.setPriority(priority);
		return t;
    }

}