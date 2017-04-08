package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * 工具模型观察器适配器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class ToolInfoAdapter implements ToolInfoObverser {

	@Override
	public void fireToolInfoAdded(ToolInfo toolInfo) {}
	@Override
	public void fireToolInfoRemoved(ToolInfo toolInfo) {}
	@Override
	public void fireCleared() {}
	
}
