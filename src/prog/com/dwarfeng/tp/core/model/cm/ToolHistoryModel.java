package com.dwarfeng.tp.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.ToolHistoryObverser;
import com.dwarfeng.tp.core.model.struct.ToolHistory;

/**
 * ������ʷģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolHistoryModel  extends ObverserSet<ToolHistoryObverser>, ExternalReadWriteThreadSafe, Iterable<ToolHistory>{

	/**
	 * ��ģ�͵Ķ������Ԫ�ء�
	 * @param toolHistory ָ���Ĺ�����ʷ��
	 * @return �ò����Ƿ�ı���ģ�ͱ���
	 */
	public boolean offer(ToolHistory toolHistory);
	
	/**
	 * ��ģ�͵�ָ��λ�����Ԫ�ء�
	 * @param index ָ����λ�á�
	 * @param toolHistory ָ���Ĺ�����ʷ��
	 * @return �ò����Ƿ�ı���ģ�ͱ���
	 */
	public boolean add(int index, ToolHistory toolHistory);
	
	/**
	 * ��ģ�͵ĵײ�ȡ��Ԫ�ء�
	 * @return �ò����Ƿ�ı���ģ�ͱ���
	 */
	public boolean poll();
	
	/**
	 * ����ģ�͵Ĵ�С��
	 * @return ģ�͵Ĵ�С��
	 */
	public int size();
	
	/**
	 * ����ģ�͵���������С��
	 * @return ģ�͵���������С��
	 */
	public int maxSize();
	
	/**
	 * ����ģ�͵���������С��
	 * @param size ָ���Ĵ�С��
	 * @return �ò����Ƿ�ı���ģ�ͱ���
	 */
	public boolean setMaxSize(int size);
	
	/**
	 * ��ģ��ָ��λ�ô���Ԫ���Ƴ���
	 * @param index ָ����λ�á�
	 * @return �ò����Ƿ�ı���ģ�ͱ���
	 */
	public boolean remove(int index);
	
	/**
	 * ���ģ�͡�
	 */
	public void clear();
	
}
