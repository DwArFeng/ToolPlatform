package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * 工具模型观察器适配器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class ToolAdapter implements ToolObverser {

	@Override
	public void fireEntryAdded(String name, ToolInfo info) {}
	@Override
	public void fireEntryRemoved(String name) {}
	@Override
	public void fireEntryChanged(String name, ToolInfo oldOne, ToolInfo newOne) {}
	@Override
	public void fireCleared() {}
	
}
