package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.ToolHistory;

/**
 * 工具历史模型观察器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolHistoryObverser extends Obverser {

	/**
	 * 通知模型在指定位置添加了指定的工具历史。
	 * @param index 指定的位置。
	 * @param toolHistory 指定的工具历史。
	 */
	public void fireToolHistoryAdded(int index, ToolHistory toolHistory);
	
	/**
	 * 通知模型在指定的位置移除了工具历史。
	 * @param index 指定的位置。
	 * @param toolHistory 指定的工具历史。
	 */
	public void fireToolHistoryRemoved(int index, ToolHistory toolHistory);
	
	/**
	 * 通知模型被清空。
	 */
	public void fireCleared();
	
	/**
	 * 通知模型的最大容量改变。
	 * @param oldValue 旧的最大容量。
	 * @param newValue 新的最大容量。
	 */
	public void fireMaxSizeChanged(int oldValue, int newValue);
	
}
