package com.dwarfeng.tp.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * 工具信息面板观察器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolInfoPanelObverser extends Obverser {

	/**
	 * 通知需要运行指定的工具。
	 * @param toolInfo 指定的工具。
	 */
	public void fireRunTool(ToolInfo toolInfo);
	
}
