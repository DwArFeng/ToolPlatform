package com.dwarfeng.tp.core.model.struct;

/**
 * 后台接口。
 * @author  DwArFeng
 * @since 1.8
 */
public interface Background {

	/**
	 * 向后台中提交一个过程。
	 * <p> 被提交的过程将会在后台中执行。
	 * @param process 指定的过程。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void submit(Process process);
	
}
