package com.dwarfeng.tp.plaf.core;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.plaf.Tool;

public interface ToolObverser extends Obverser {
	
	/**
	 * 工具启动时的通知。
	 * @param tool 指定的工具入口。
	 */
	public void fireToolStarted(Tool tool);
	
	/**
	 * 工具停止时的通知。
	 * @param tool 指定的工具入口。
	 * @param stopMode 
	 */
	public void fireToolStoped(Tool tool, ToolStopMode stopMode);

}
