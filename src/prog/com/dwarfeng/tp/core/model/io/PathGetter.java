package com.dwarfeng.tp.core.model.io;

import java.io.File;

/**
 * 路径获得器。
 * <p> 将指定的名字转换成路径。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface PathGetter {

	/**
	 * 获取指定名称的库的绝对路径。
	 * @param name 指定的名称。
	 * @return 指定的名称的库的绝对路径。
	 */
	public String getLibraryPath(String name);
	
	/**
	 * 获取指定的工具的Jar包的绝对路径。
	 * @param name Jar包的名称。
	 * @return Jar包的名称对应的绝对路径。
	 */
	public String getToolFilePath(String name);
	
	/**
	 * 获取指定名称的工具的工作目录。
	 * @param name 指定的工具名称。
	 * @return 指定工具名称的工具的工作目录。
	 */
	public File getToolDirectory(String name);
}
