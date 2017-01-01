package com.dwarfeng.tp.core.control.proc;

import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * 动作处理器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface ActionProcessor {
	
	/**
	 * 程序的启动动作。
	 * @throws ProcessException 过程异常。
	 * @throws IllegalStateException 该动作没在正确的状态（未启动）下执行。
	 */
	public void start() throws ProcessException;

}
