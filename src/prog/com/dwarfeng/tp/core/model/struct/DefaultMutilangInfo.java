package com.dwarfeng.tp.core.model.struct;

import java.io.File;
import java.util.Objects;

/**
 * 默认多语言信息。
 * <p> 多语言信息的默认实现。
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultMutilangInfo implements MutilangInfo {
	
	private final String label;
	private final File file;
	
	/**
	 * 新实例。
	 * @param label 指定的标签。
	 * @param file 指定的文件。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultMutilangInfo(String label, File file) {
		Objects.requireNonNull(label, "入口参数 label 不能为 null。");
		Objects.requireNonNull(file, "入口参数 file 不能为 null。");
		
		this.label = label;
		this.file = file;
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
	 * @see com.dwarfeng.tp.core.model.struct.MutilangInfo#getFile()
	 */
	@Override
	public File getFile() {
		return this.file;
	}

}
