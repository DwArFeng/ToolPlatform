package com.dwarfeng.tp.plaf.core;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.plaf.Tool;

public interface ToolObverser extends Obverser {
	
	/**
	 * 工具停止时的通知。
	 * @param tool 指定的工具。
	 */
	public void fireToolStoped(Tool tool);

}
