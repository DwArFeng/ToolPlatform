package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.ToolHistory;

/**
 * ������ʷģ�͹۲�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolHistoryObverser extends Obverser {

	/**
	 * ֪ͨģ����ָ��λ�������ָ���Ĺ�����ʷ��
	 * @param index ָ����λ�á�
	 * @param toolHistory ָ���Ĺ�����ʷ��
	 */
	public void fireToolHistoryAdded(int index, ToolHistory toolHistory);
	
	/**
	 * ֪ͨģ����ָ����λ���Ƴ��˹�����ʷ��
	 * @param index ָ����λ�á�
	 * @param toolHistory ָ���Ĺ�����ʷ��
	 */
	public void fireToolHistoryRemoved(int index, ToolHistory toolHistory);
	
	/**
	 * ֪ͨģ�ͱ���ա�
	 */
	public void fireCleared();
	
	/**
	 * ֪ͨģ�͵���������ı䡣
	 * @param oldValue �ɵ����������
	 * @param newValue �µ����������
	 */
	public void fireMaxSizeChanged(int oldValue, int newValue);
	
}
