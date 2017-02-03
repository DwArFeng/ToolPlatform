package com.dwarfeng.tp.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.ToolRuntimeObverser;
import com.dwarfeng.tp.core.model.struct.RunningTool;

/**
 * 工具运行时模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolRuntimeModel extends ExternalReadWriteThreadSafe, ObverserSet<ToolRuntimeObverser>, Iterable<RunningTool>{

	/**
	 * 向模型中添加指定的运行中工具。
	 * @param runningTool 指定的运行中工具。
	 * @return 该操作是否对模型产生了变更。
	 */
	public boolean add(RunningTool runningTool);
	
	/**
	 * 返回模型中是否存在名称为指定值的运行中工具。
	 * @param name 指定的名称。
	 * @return 是否存在名称为指定值的运行中工具。
	 */
	public boolean contains(String name);
	
	/**
	 * 返回模型中指定名称的运行中工具存在的数量。
	 * @param name 指定的名称。
	 * @return 名称为指定值的运行中工具存在的数量。
	 */
	public int numberOf(String name);
	
	/**
	 * 返回模型中的运行中工具数量。
	 * @return 模型中的运行中工具的数量。
	 */
	public int size();
	
	/**
	 * 返回该模型是否为空。
	 * @return 该模型是否为空。
	 */
	public boolean isEmpty();
	
	/**
	 * 返回该模型中是否包含已经退出的运行中工具。
	 * @return 是否包含已经完成的运行中工具。
	 */
	public boolean hasExited();
	
	/**
	 * 取出并返回最早的已经退出的运行中工具，如果没有，则等待。
	 * @return 最早的已经退出的运行中工具。
	 * @throws InterruptedException 等待过程中线程被中断。
	 */
	public RunningTool takeExited() throws InterruptedException;
	
	/**
	 * 清除模型中所有的已经退出的运行中工具。
	 * @return 该方法是否改变了模型本身。
	 */
	public boolean clearExited();
}
