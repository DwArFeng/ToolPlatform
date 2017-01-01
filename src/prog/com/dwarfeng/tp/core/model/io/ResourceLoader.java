package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.vim.ResourceModel;

/**
 * 资源模型读取器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface ResourceLoader {

	/**
	 * 向指定的资源模型中读取数据。
	 * @param resourceModel 指定的资源模型。
	 * @throws LoadFailedException 读取失败异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void load(ResourceModel resourceModel) throws LoadFailedException;
	
}
