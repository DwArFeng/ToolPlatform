package com.dwarfeng.tp.plaf.core;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.plaf.Tool;

public interface ToolObverser extends Obverser {
	
	/**
	 * ��������ʱ��֪ͨ��
	 * @param tool ָ���Ĺ�����ڡ�
	 */
	public void fireToolStarted(Tool tool);
	
	/**
	 * ����ֹͣʱ��֪ͨ��
	 * @param tool ָ���Ĺ�����ڡ�
	 * @param stopMode 
	 */
	public void fireToolStoped(Tool tool, ToolStopMode stopMode);

}
