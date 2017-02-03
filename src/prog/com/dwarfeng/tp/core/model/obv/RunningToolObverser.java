package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.RunningTool;

public interface RunningToolObverser extends Obverser {

	/**
	 * 通知指定的运行中工具启动。
	 * @param runningTool 指定的运行中工具。
	 */
	public void fireStarted(RunningTool runningTool);
	
	/**
	 * 通知指定的运行中工具结束。
	 * @param runningTool 指定的运行中工具。
	 */
	public void fireExited(RunningTool runningTool);
}
