package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.RunningTool;

/**
 * 工具运行时模型观察器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolRuntimeObverser extends Obverser {
	
	/**
	 * 通知指定的运行中工具被添加。
	 * @param runningTool 指定的运行中工具。
	 */
	public void fireRunnintToolAdded(RunningTool runningTool);

	/**
	 * 通知指定的运行中工具被移除。
	 * @param runningTool 指定的运行中工具。
	 */
	public void fireRunningToolRemoved(RunningTool runningTool);
	
	/**
	 * 通知指定的运行中工具开始运行。
	 * @param runningTool 指定的运行中工具。
	 */
	public void fireRunningToolStarted(RunningTool runningTool);
	
	/**
	 * 通知指定的运行中工具结束。
	 * @param runningTool 指定的运行中工具。
	 */
	public void fireRunningToolExited(RunningTool runningTool);
	
}
