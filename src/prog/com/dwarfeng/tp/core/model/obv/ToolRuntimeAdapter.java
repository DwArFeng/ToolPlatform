package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.tp.core.model.struct.RunningTool;

/**
 * ��������ʱ�۲�����������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class ToolRuntimeAdapter implements ToolRuntimeObverser {

	@Override
	public void fireRunningToolAdded(RunningTool runningTool) {}
	@Override
	public void fireRunningToolRemoved(RunningTool runningTool) {}
	@Override
	public void fireRunningToolStarted(RunningTool runningTool) {}
	@Override
	public void fireRunningToolExited(RunningTool runningTool) {}
	@Override
	public void fireAddRejectChanged(boolean newValue) {}

}
