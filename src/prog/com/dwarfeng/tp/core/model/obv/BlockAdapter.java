package com.dwarfeng.tp.core.model.obv;

/**
 * ×èµ²Ä£ÐÍ¹Û²ìÆ÷ÊÊÅäÆ÷¡£
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class BlockAdapter implements BlockObverser{

	@Override
	public void fireEntryAdded(String key, String value) {}
	@Override
	public void fireEntryRemoved(String key) {}
	@Override
	public void fireEntryChanged(String key, String oldValue, String newValue) {}
	@Override
	public void fireCleared() {}
	@Override
	public void fireUpdated() {}
	
}
