package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.tp.core.model.struct.RunningTool;

/**
 * 运行时工具观察器适配器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class RunningToolAdapter implements RunningToolObverser {

	@Override
	public void fireStarted(RunningTool runningTool) {}
	@Override
	public void fireExited(RunningTool runningTool) {}

}
