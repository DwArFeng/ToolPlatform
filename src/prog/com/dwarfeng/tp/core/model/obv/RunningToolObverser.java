package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.RunningTool;

public interface RunningToolObverser extends Obverser {

	/**
	 * ָ֪ͨ���������й���������
	 * @param runningTool ָ���������й��ߡ�
	 */
	public void fireStarted(RunningTool runningTool);
	
	/**
	 * ָ֪ͨ���������й��߽�����
	 * @param runningTool ָ���������й��ߡ�
	 */
	public void fireExited(RunningTool runningTool);
}
