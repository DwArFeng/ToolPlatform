package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.dutil.basic.io.SaveFailedException;
import com.dwarfeng.tp.core.model.vim.MutilangModel;

public interface MutilangSaver {

	/**
	 * 从指定的多语言模型中保存数据。
	 * @param mutilangModel 指定的多语言模型。
	 * @throws SaveFailedException 保存失败异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void save(MutilangModel mutilangModel) throws SaveFailedException;
	
}
