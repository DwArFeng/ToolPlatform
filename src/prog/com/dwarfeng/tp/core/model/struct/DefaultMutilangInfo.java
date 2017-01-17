package com.dwarfeng.tp.core.model.struct;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 默认多语言信息。
 * <p> 多语言信息的默认实现。
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultMutilangInfo implements MutilangInfo {
	
	private final String label;
	private final Map<String, String> mutilangMap;
	
	/**
	 * 新实例。
	 * @param label 指定的标签。
	 * @param filePath 指定的文件的路径。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultMutilangInfo(String label, Map<String, String> mutilangMap) {
		Objects.requireNonNull(label, "入口参数 label 不能为 null。");
		Objects.requireNonNull(mutilangMap, "入口参数 mutilangMap 不能为 null。");
		
		this.label = label;
		this.mutilangMap = mutilangMap;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangInfo#getLabel()
	 */
	@Override
	public String getLabel() {
		return this.label;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangAttribute#getMutilangMap()
	 */
	@Override
	public Map<String, String> getMutilangMap() {
		return Collections.unmodifiableMap(mutilangMap);
	}

}
