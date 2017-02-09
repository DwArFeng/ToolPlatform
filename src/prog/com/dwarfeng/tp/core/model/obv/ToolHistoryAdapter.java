package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.tp.core.model.struct.ToolHistory;

/**
 * ������ʷ�۲�����������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class ToolHistoryAdapter implements ToolHistoryObverser{

	@Override
	public void fireToolHistoryAdded(int index, ToolHistory toolHistory) {}
	@Override
	public void fireToolHistoryRemoved(int index, ToolHistory toolHistory) {}
	@Override
	public void fireCleared() {}
	@Override
	public void fireMaxSizeChanged(int oldValue, int newValue) {}

}
