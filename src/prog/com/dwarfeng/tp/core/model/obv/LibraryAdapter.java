package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.tp.core.model.struct.Library;

/**
 * 库观察器适配器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class LibraryAdapter implements LibraryObverser{
	
	@Override
	public void fireLibraryAdded(Library library) {}
	@Override
	public void fireLibraryRemoved(Library library) {}
	@Override
	public void fireCleared() {}

}
