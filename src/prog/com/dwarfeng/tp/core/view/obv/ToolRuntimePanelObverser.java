package com.dwarfeng.tp.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.RunningTool;

/**
 * ��������ʱ���۲�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolRuntimePanelObverser extends Obverser {
	
	/**
	 * ��¼ָ�������й��ߵ�������־��
	 * @param runningTool ָ���������й���
	 */
	public void fireLogRunningTool(RunningTool runningTool);

}
