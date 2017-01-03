package com.dwarfeng.tp.core.model.struct;

/**
 * 多语言的信息。
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangInfo {
	
	/**
	 * 获取信息中的标签。
	 * @return 信息中的标签。
	 */
	public String getLabel();
	
	/**
	 * 获取信息中的文件位置。
	 * @return 信息中的文件位置。
	 */
	public String getFile();

}
