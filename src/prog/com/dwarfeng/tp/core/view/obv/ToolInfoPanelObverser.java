package com.dwarfeng.tp.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * ������Ϣ���۲�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolInfoPanelObverser extends Obverser {

	/**
	 * ֪ͨ��Ҫ����ָ���Ĺ��ߡ�
	 * @param name ���ߵ����ơ�
	 */
	public void fireRunTool(String name);
	
}
