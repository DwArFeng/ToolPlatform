package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.cm.LoggerModel;

/**
 * 记录器模型读取器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerLoader {
	
	/**
	 * 向指定的记录器模型中读取数据。
	 * @param loggerModel 指定的记录器模型
	 * @throws LoadFailedException 读取失败异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void load(LoggerModel loggerModel) throws LoadFailedException;

}
