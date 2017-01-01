package com.dwarfeng.tp.core.control.proc;

/**
 * 动作处理器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface ActionProcessor {
	
	/**
	 * 程序的启动动作。
	 * @throws IllegalStateException 该动作没在正确的状态（未启动）下执行。
	 */
	public void start();

}
