package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * 工具信息观察器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolInfoObverser extends Obverser{
	
	/**
	 * 通知模型中添加了指定的工具信息。
	 * @param toolInfo 指定的工具信息。
	 */
	public void fireToolInfoAdded(ToolInfo toolInfo);
	
	/**
	 * 通知模型中移除了指定的工具信息。
	 * @param toolInfo 指定的工具信息。
	 */
	public void fireToolInfoRemoved(ToolInfo toolInfo);
	
	/**
	 * 通知模型被清空。
	 */
	public void fireCleared();
	
}
