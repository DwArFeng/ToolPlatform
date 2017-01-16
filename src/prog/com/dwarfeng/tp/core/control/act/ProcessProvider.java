package com.dwarfeng.tp.core.control.act;

import com.dwarfeng.tp.core.model.struct.Process;

/**
 * 过程提供器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface ProcessProvider {
	
	/**
	 * 获取一个新的程序启动时使用的过程。
	 * @return 新的程序启动时使用的后台过程。
	 */
	public Process newStartProcess();

}
