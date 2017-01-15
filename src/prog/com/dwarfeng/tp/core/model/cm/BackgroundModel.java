package com.dwarfeng.tp.core.model.cm;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.BackgroundObverser;
import com.dwarfeng.tp.core.model.struct.Process;

/**
 * ��̨ģ�͡�
 * @author DwArFeng
 * @since 1.8
 */
public interface BackgroundModel extends ObverserSet<BackgroundObverser>, ExternalReadWriteThreadSafe, Iterable<Process>{
	
	/**
	 * ���ظú�̨ģ�������ڴ������̵�ִ��������
	 * <p> ע�⣺���ص�ִ���������Ӧ�����ڲ�ѯ״̬�������������������׳� {@link UnsupportedOperationException}��
	 * @return ��̨ģ���е�ִ��������
	 */
	public ExecutorService getExecutorService();
	
	/**
	 * ���̨ģ�����ύһ�����̡�
	 * <p> ��ָ���Ľ���Ϊ <code>null</code>������ģ�����Ѿ�������ָ���Ľ���ʱ���������κβ�����
	 * @param process ָ���Ľ��̡�
	 * @return �ò����Ƿ��ģ������˸ı䡣
	 */
	public boolean submit(Process process);
	
	/**
	 * ���̨ģ�����ύָ�������е����й��̡�
	 * <p> ��ָ���Ľ���Ϊ <code>null</code>������ģ�����Ѿ�������ָ���Ľ���ʱ���������κβ�����
	 * @param c ���й�����ɵļ��ϡ�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 * @return �ò��������Ƿ��ģ������˸ı䡣
	 */
	public boolean submitAll(Collection<? extends Process> c);
	
	/**
	 * ���ظú�̨�����Ƿ����ָ���Ĺ��̡�
	 * @param process ָ���Ķ���
	 * @return �ú�̨ģ�����Ƿ����ָ���Ķ���
	 */
	public boolean contains(Process process);
	
	/**
	 * ���غ�̨�������Ƿ����ȫ����ָ������
	 * @param c ����ָ��������ɵļ��ϡ�
	 * @return ��̨ģ�����Ƿ�������е�ָ������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean containsAll(Collection<Process> c);
	
	/**
	 * ���ظ�ģ���Ƿ�Ϊ�ա�
	 * @return ��ģ���Ƿ�Ϊ�ա�
	 */
	public boolean isEmpty();
	
	/**
	 * ���ظ�ģ�����Ƿ�����Ѿ���ɵĹ��̡�
	 * @return �Ƿ�����Ѿ���ɵĹ��̡�
	 */
	public boolean hasFinished();
	
	/**
	 * ����������Ѿ���ɵĹ��̶������û�У���ȴ���
	 * @return ������Ѿ���ɵĹ��̶���
	 * @throws InterruptedException �ȴ��������̱߳��жϡ�
	 */
	public Process takeFinished() throws InterruptedException;
	
	/**
	 * ���ģ�������е��Ѿ���ɵĹ��̡�
	 * @return �÷����Ƿ�ı���ģ�ͱ�����
	 */
	public boolean clearFinished();
	
	/**
	 * �رոú�̨ģ�͡�
	 * <p> ��̨ģ�ͱ��رպ󣬻�ܾ����й��̵��ύ�������Ѿ��ύ�Ĺ�������Ӱ�졣
	 */
	public void shutdown();
	
}