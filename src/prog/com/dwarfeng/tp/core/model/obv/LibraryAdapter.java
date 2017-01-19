package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.tp.core.model.struct.Library;

/**
 * ø‚π€≤Ï∆˜  ≈‰∆˜°£
 * @author DwArFeng
 * @since 0.0.0-alpha
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
