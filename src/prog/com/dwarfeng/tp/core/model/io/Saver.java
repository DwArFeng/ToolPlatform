package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.dutil.basic.io.SaveFailedException;

/**
 * 保存器。
 * @author DwArFeng
 * @since 1.8
 */
public interface Saver<T> {

	/**
	 * 从指定的多语言模型中保存数据。
	 * @param mutilangModel 指定的多语言模型。
	 * @throws SaveFailedException 保存失败异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void save(T model) throws SaveFailedException;
	
}
