package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.Library;

/**
 * 库观察器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface LibraryObverser extends Obverser {

	/**
	 * 通知模型中添加了指定的库。
	 * @param library 指定的库。
	 */
	public void fireLibraryAdded(Library library);
	
	/**
	 * 通知模型中移除了指定的库。
	 * @param library 指定的库。
	 */
	public void fireLibraryRemoved(Library library);
	
	/**
	 * 通知模型被清除。
	 */
	public void fireCleared();
	
}
