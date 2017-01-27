package com.dwarfeng.tp.core.control.act;

import com.dwarfeng.tp.core.model.struct.Flow;

/**
 * 过程提供器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface FlowProvider {
	
	/**
	 * 获取一个新的程序初始化时使用的过程。
	 * @return 新的程序初始化时使用的后台过程。
	 */
	public Flow newInitializeFlow();
	
	/**
	 * 获取一个新的读取库的过程。
	 * @return 新的读取库的过程。
	 */
	public Flow newLoadLibFlow();
	
	/**
	 * 获取一个新的检查库的过程。
	 * @return 新的检查库的过程。
	 */
	public Flow newCheckLibFlow();

}
