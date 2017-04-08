package com.dwarfeng.tp.core.model.obv;

import java.util.Set;

/**
 * 阻挡模型观察器适配器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class BlockAdapter implements BlockObverser{

	@Override
	public void fireEntryAdded(String key, Set<String> value) {}
	@Override
	public void fireEntryRemoved(String key) {}
	@Override
	public void fireEntryChanged(String key, Set<String> oldValue, Set<String> newValue) {}
	@Override
	public void fireCleared() {}
	@Override
	public void fireUpdated() {}
	
}
