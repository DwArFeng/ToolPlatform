package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.dutil.basic.io.LoadFailedException;

/**
 * 读取器。
 * @author DwArFeng
 * @since 1.8
 */
public interface Loader<T> {

	/**
	 * 向指定的模型中读取数据。
	 * @param loggerModel 指定的模型
	 * @throws LoadFailedException 读取失败异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void load(T model) throws LoadFailedException;
	
}
