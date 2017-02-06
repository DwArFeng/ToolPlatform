package com.dwarfeng.tp.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * ������Ϣ���۲�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolInfoPanelObverser extends Obverser {

	/**
	 * ֪ͨ��Ҫ����ָ���Ĺ��ߡ�
	 * @param toolInfo ָ���Ĺ��ߡ�
	 */
	public void fireRunTool(ToolInfo toolInfo);
	
}
