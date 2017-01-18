package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.Library;

/**
 * 库观察器。
 * @author DwArFeng
 * @since 1.8
 */
public interface LibraryObverser extends Obverser {

	/**
	 * 通知入口被添加。
	 * @param key 指定的键。
	 * @param value 指定的值。
	 */
	public void fireEntryAdded(String key, Library value);
	
	/**
	 * 通知入口被移除。
	 * @param key 指定的键。
	 */
	public void fireEntryRemoved(String key);
	
	/**
	 * 通知入口被更改。
	 * @param key 指定的键。
	 * @param oldValue 旧的值。
	 * @param newValue 新的值。
	 */
	public void fireEntryChanged(String key, Library oldValue, Library newValue);
	
	/**
	 * 通知模型被清除。
	 */
	public void fireCleared();
	
}
