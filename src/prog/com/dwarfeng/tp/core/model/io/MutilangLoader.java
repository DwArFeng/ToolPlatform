package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.vim.MutilangModel;

/**
 * 多语言模型读取器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangLoader {
	
	/**
	 * 向指定的多语言模型中读取数据。
	 * @param mutilangModel 指定的多语言模型。
	 * @throws LoadFailedException 读取失败异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void load(MutilangModel mutilangModel) throws LoadFailedException;

}
