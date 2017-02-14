package com.dwarfeng.tp.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.RunningTool;

/**
 * 工具运行时面板观察器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolRuntimePanelObverser extends Obverser {
	
	/**
	 * 记录指定运行中工具的运行日志。
	 * @param runningTool 指定的运行中工具
	 */
	public void fireLogRunningTool(RunningTool runningTool);
	
	/**
	 * 移除指定的已经退出的运行中工具。
	 * @param runningTool 指定的运行中工具。
	 */
	public void fireRemoveExitedRunningTool(RunningTool runningTool);

}
