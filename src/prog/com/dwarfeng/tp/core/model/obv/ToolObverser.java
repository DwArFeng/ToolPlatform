package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * ���߹۲�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolObverser extends Obverser{

	/**
	 * ֪ͨģ���������ָ���Ĺ��ߡ�
	 * @param name ���ߵ����ơ�
	 * @param info ���ߵ���Ϣ��
	 */
	public void fireEntryAdded(String name, ToolInfo info);
	
	/**
	 * ֪ͨģ�����Ƴ���ָ���Ĺ��ߡ�
	 * @param name ���ߵ����ơ�
	 */
	public void fireEntryRemoved(String name);
	
	/**
	 * ֪ͨģ���й��ߵ���Ϣ�ı䡣
	 * @param name ���ߵ����ơ�
	 * @param oldOne �ɵĹ�����Ϣ��
	 * @param newOne �µĹ�����Ϣ��
	 */
	public void fireEntryChanged(String name, ToolInfo oldOne, ToolInfo newOne);
	
	/**
	 * ֪ͨģ�ͱ���ա�
	 */
	public void fireCleared();
	
}
