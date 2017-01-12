package com.dwarfeng.tp.core.model.cm;

import java.util.Collection;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.BackgrObverser;
import com.dwarfeng.tp.core.model.struct.Process;

/**
 * ��̨ģ�͡�
 * @author DwArFeng
 * @since 1.8
 */
public interface BackgroundModel extends ObverserSet<BackgrObverser>, ExternalReadWriteThreadSafe, Iterable<Process>{
	
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
	 * ���ظú�̨�����Ƿ����ָ���Ķ���
	 * <p> ֻ�е�����Ϊ {@link Process}��ʱ�����п��ܷ��� <code>true</code>��
	 * @param o ָ���Ķ���
	 * @return �ú�̨ģ�����Ƿ����ָ���Ķ���
	 */
	public boolean contains(Object o);
	
	/**
	 * ���غ�̨������
	 * @param c
	 */
	public void containsAll(Collection<?> c);
	
	public boolean isEmpty();
	
	

}
