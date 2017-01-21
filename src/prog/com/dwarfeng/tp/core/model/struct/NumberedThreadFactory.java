package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ����̹߳�����
 * <p> ���̹߳����ṩ��ˮ��ŵ��̣߳����ڲ�ά����һ����š�
 * ���µ��̱߳�����ʱ���ù������ص��̵߳������ɱ�ź�ǰ׺��ɡ�
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
	 * ����һ��Ĭ�ϵı���̡߳�
	 * <p> �̲߳����ػ��̣߳��Ҿ��б�׼�����ȼ���
	 * @param prefix ָ����ǰ׺��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public NumberedThreadFactory(String prefix) {
		this(prefix, false, Thread.NORM_PRIORITY);
	}
	
	/**
	 * ����һ������ָ��ǰ׺��ָ���ػ��̱߳�־��ָ�����ȼ��ı���̹߳�����
	 * @param prefix ָ����ǰ׺��
	 * @param daemonFlag ָ�����ػ��̱߳�־��
	 * @param priority ָ�������ȼ���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public NumberedThreadFactory(String prefix, boolean daemonFlag, int priority) {
		Objects.requireNonNull(prefix, "��ڲ��� prefiex ����Ϊ null��");
		
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