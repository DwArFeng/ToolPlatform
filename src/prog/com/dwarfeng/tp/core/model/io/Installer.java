package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * 安装器。
 * <p> 安装某个事物，并且向指定的模型通知。
 * @author DwArFeng
 * @since 1.8
 */
public interface Installer<T> {
	
	/**
	 * 安装某个事物，并且通知指定的模型。
	 * @param model 指定的模型。
	 * @throws ProcessException 过程异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void install(T model) throws ProcessException;

}
