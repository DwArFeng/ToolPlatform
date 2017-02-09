package com.dwarfeng.tp.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.ToolHistoryObverser;
import com.dwarfeng.tp.core.model.struct.ToolHistory;

/**
 * 工具历史模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolHistoryModel  extends ObverserSet<ToolHistoryObverser>, ExternalReadWriteThreadSafe, Iterable<ToolHistory>{

	/**
	 * 向模型的顶部添加元素。
	 * @param toolHistory 指定的工具历史。
	 * @return 该操作是否改变了模型本身。
	 */
	public boolean offer(ToolHistory toolHistory);
	
	/**
	 * 向模型的指定位置添加元素。
	 * @param index 指定的位置。
	 * @param toolHistory 指定的工具历史。
	 * @return 该操作是否改变了模型本身。
	 */
	public boolean add(int index, ToolHistory toolHistory);
	
	/**
	 * 从模型的底部取出元素。
	 * @return 该操作是否改变了模型本身。
	 */
	public boolean poll();
	
	/**
	 * 返回模型的大小。
	 * @return 模型的大小。
	 */
	public int size();
	
	/**
	 * 返回模型的最大允许大小。
	 * @return 模型的最大允许大小。
	 */
	public int maxSize();
	
	/**
	 * 设置模型的最大允许大小。
	 * @param size 指定的大小。
	 * @return 该操作是否改变了模型本身。
	 */
	public boolean setMaxSize(int size);
	
	/**
	 * 将模型指定位置处的元素移除。
	 * @param index 指定的位置。
	 * @return 该操作是否改变了模型本身。
	 */
	public boolean remove(int index);
	
	/**
	 * 清空模型。
	 */
	public void clear();
	
}
