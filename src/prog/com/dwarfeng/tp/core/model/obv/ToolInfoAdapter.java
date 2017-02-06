package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * ����ģ�͹۲�����������
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
