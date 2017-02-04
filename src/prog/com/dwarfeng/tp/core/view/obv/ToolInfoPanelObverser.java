package com.dwarfeng.tp.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 工具信息面板观察器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolInfoPanelObverser extends Obverser {

	/**
	 * 通知需要运行指定的工具。
	 * @param name 工具的名称。
	 */
	public void fireRunTool(String name);
	
}
