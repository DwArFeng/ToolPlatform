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
	 * @param name 工具信息的名称。
	 * @param info 工具信息的信息。
	 */
	public void fireEntryAdded(String name, ToolInfo info);
	
	/**
	 * 通知模型中移除了指定的工具信息。
	 * @param name 工具信息的名称。
	 */
	public void fireEntryRemoved(String name);
	
	/**
	 * 通知模型中工具信息的信息改变。
	 * @param name 工具信息的名称。
	 * @param oldOne 旧的工具信息信息。
	 * @param newOne 新的工具信息信息。
	 */
	public void fireEntryChanged(String name, ToolInfo oldOne, ToolInfo newOne);
	
	/**
	 * 通知模型被清空。
	 */
	public void fireCleared();
	
}
