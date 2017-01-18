package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.tp.core.model.struct.Library;

/**
 * ��۲�����������
 * @author DwArFeng
 * @since 1.8
 */
public abstract class LibraryAdapter implements LibraryObverser{

	@Override
	public void fireEntryAdded(String key, Library value) {}
	@Override
	public void fireEntryRemoved(String key) {}
	@Override
	public void fireEntryChanged(String key, Library oldValue, Library newValue) {}
	@Override
	public void fireCleared() {}

}
