package com.dwarfeng.tp.core.model.cm;

import java.util.concurrent.ExecutorService;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.str.Name;
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
	 * 返回该后台模型中用于处理过程的执行器服务。
	 * <p> 注意：返回的执行器服务仅应该用于查询状态，调用其其它方法会抛出 {@link UnsupportedOperationException}。
	 * @return 后台模型中的执行器服务。
	 */
	public ExecutorService getExecutorService();
	
	/**
	 * 向模型中添加指定的运行中工具。
	 * <p> 向模型中添加的运行中工具必须是还没有运行的工具。
	 * @param runningTool 指定的运行中工具。
	 * @return 该操作是否对模型产生了变更。
	 */
	public boolean add(RunningTool runningTool);
	
	/**
	 * 返回该模型是否拒绝新的运行中工具被添加。
	 * @return 该模型是否拒绝新的运行中工具被添加。
	 */
	public boolean isAddRejected();
	
	/**
	 * 设置该模型是否拒绝新的运行中工具被添加。
	 * @param aFlag 是否拒绝新的运行中工具被添加。
	 * @return 该操作是否对模型造成了改变。
	 */
	public boolean setAddRejected(boolean aFlag);
	
	/**
	 * 返回模型中是否存在名称为指定值的运行中工具。
	 * @param name 指定的名称。
	 * @return 是否存在名称为指定值的运行中工具。
	 */
	public boolean contains(Name name);
	
	/**
	 * 返回模型中指定名称的运行中工具存在的数量。
	 * @param name 指定的名称。
	 * @return 名称为指定值的运行中工具存在的数量。
	 */
	public int numberOf(Name name);
	
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
	 * 返回该模型中是否包含没有退出（没有启动和正在运行）的工具
	 * @return 是否包含没有退出的工具。
	 */
	public boolean hasNotExited();
	
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
	 * 移除指定的运行中工具。
	 * <p> 只有当工具已经结束运行时，才能从模型中移除。
	 * @param runningTool 指定的运行中工具。
	 * @return 该操作是否改变了模型本身。
	 */
	public boolean remove(RunningTool runningTool);

	/**
	 * 清除模型中所有的已经退出的运行中工具。
	 * @return 该方法是否改变了模型本身。
	 */
	public boolean clearExited();
	
	/**
	 * 关闭该工具运行时模型。
	 * <p> 工具运行时模型被关闭后，会拒绝所有运行时工具的添加；对于已经添加过的工具，则什么也不做。
	 */
	public void shutdown();
}
