package com.dwarfeng.tp.core.model.struct;

import java.util.Map;

/**
 * 多语言的属性。
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangInfo {
	
	/**
	 * 获取属性中的标签。
	 * @return 信息中的标签。
	 */
	public String getLabel();
	
	/**
	 * 获取属性中的键值映射。
	 * @return 属性中的键值映射。
	 */
	public Map<String, String> getMutilangMap();

}
