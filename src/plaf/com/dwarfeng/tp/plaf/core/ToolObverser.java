package com.dwarfeng.tp.plaf.core;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.plaf.Tool;

public interface ToolObverser extends Obverser {
	
	/**
	 * ����ֹͣʱ��֪ͨ��
	 * @param tool ָ���Ĺ��ߡ�
	 */
	public void fireToolStoped(Tool tool);

}
