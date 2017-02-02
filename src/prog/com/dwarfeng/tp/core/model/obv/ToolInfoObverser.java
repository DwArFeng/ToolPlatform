package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * ������Ϣ�۲�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolInfoObverser extends Obverser{

	/**
	 * ֪ͨģ���������ָ���Ĺ�����Ϣ��
	 * @param name ������Ϣ�����ơ�
	 * @param info ������Ϣ����Ϣ��
	 */
	public void fireEntryAdded(String name, ToolInfo info);
	
	/**
	 * ֪ͨģ�����Ƴ���ָ���Ĺ�����Ϣ��
	 * @param name ������Ϣ�����ơ�
	 */
	public void fireEntryRemoved(String name);
	
	/**
	 * ֪ͨģ���й�����Ϣ����Ϣ�ı䡣
	 * @param name ������Ϣ�����ơ�
	 * @param oldOne �ɵĹ�����Ϣ��Ϣ��
	 * @param newOne �µĹ�����Ϣ��Ϣ��
	 */
	public void fireEntryChanged(String name, ToolInfo oldOne, ToolInfo newOne);
	
	/**
	 * ֪ͨģ�ͱ���ա�
	 */
	public void fireCleared();
	
}
