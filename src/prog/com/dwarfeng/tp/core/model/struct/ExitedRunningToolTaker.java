package com.dwarfeng.tp.core.model.struct;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.cm.ToolRuntimeModel;

/**
 * 结束工具程取出器。
 * <p> 用于在工具运行时模型中取出结束的运行中工具，并且记录在指定的 Logger 之中。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface ExitedRunningToolTaker extends ExternalReadWriteThreadSafe, MutilangSupported{

	/**
	 * 获取结束工具取出器持有的记录器。
	 * @return 完成过程取出器持有的记录器。
	 */
	public Logger getLogger();
	
	/**
	 * 设置结束工具取出器中的记录器。
	 * @param logger 指定的记录器。
	 * @return 该操作是否对该记录器造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setLogger(Logger logger);
	
	/**
	 * 获取结束工具取出器是否处于暂停状态。
	 * @return 是否处于暂停状态。
	 */
	public boolean isPause();
	
	/**
	 * 设置该结束工具取出器的暂停状态。
	 * @param aFlag 是否暂停。
	 * @return 该操作是否对该取出器造成了改变。
	 */
	public boolean setPause(boolean aFlag);
	
	/**
	 * 获取该过程取出器中的工具运行时模型。
	 * @return 该过程取出器中的工具运行时模型。
	 */
	public ToolRuntimeModel getToolRuntimeModel();
	
	/**
	 * 关闭结束工具取出器。
	 * <p> 调用此方法后，取出器将停止从指定的工具运行时模型中取出完成的过程。
	 */
	public void shutdown();
	
}
