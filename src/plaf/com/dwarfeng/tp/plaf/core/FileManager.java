package com.dwarfeng.tp.plaf.core;

import java.io.File;

/**
 * 文件管理器。
 * <p> 为了保证文件的可维护性，平台中的工具涉及到文件操作，均应该使用该管理器中的
 * 获得文件的方法，而不应该直接生成 File 实例。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface FileManager {

	/**
	 * 获取指定路径下的文件。
	 * @param path 指定的路径。
	 * @return 指定路径下的文件。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public File getFile(String path);
	
}
