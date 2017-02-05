package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.tp.core.model.struct.RunningTool;

/**
 * ����ʱ���߹۲�����������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class RunningToolAdapter implements RunningToolObverser {

	@Override
	public void fireStarted(RunningTool runningTool) {}
	@Override
	public void fireExited(RunningTool runningTool) {}

}
