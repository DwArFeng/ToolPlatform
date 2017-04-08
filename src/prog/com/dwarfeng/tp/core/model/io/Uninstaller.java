package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * 卸载器。
 * <p> 卸载某个事物，并且向指定的模型通知。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface Uninstaller<T> {
	
	/**
	 * 卸载某个事物，并且通知指定的模型。
	 * @param model 指定的模型。
	 * @throws ProcessException 过程异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void uninstall(T model) throws ProcessException;

}
