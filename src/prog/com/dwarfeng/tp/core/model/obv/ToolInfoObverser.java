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
	 * @param toolInfo ָ���Ĺ�����Ϣ��
	 */
	public void fireToolInfoAdded(ToolInfo toolInfo);
	
	/**
	 * ֪ͨģ�����Ƴ���ָ���Ĺ�����Ϣ��
	 * @param toolInfo ָ���Ĺ�����Ϣ��
	 */
	public void fireToolInfoRemoved(ToolInfo toolInfo);
	
	/**
	 * ֪ͨģ�ͱ���ա�
	 */
	public void fireCleared();
	
}
