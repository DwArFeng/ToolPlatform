package com.dwarfeng.tp.model.cfg;

import java.util.Objects;
import java.util.Properties;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 属性多语言。
 * <p> 根据某个具体的 Properties 文件构成的 多语言接口。
 * @author DwArFeng
 * @since 1.8
 */
public final class PropertiesMutilang<T extends Name> implements Mutilang<T> {
	
	private final Properties properties;
	private final String defaultString;
	
	/**
	 * 生成一个新的实例。
	 * @param properties 指定的 Properties 实例。
	 * @param defaultString 当搜索不到字段时使用的默认文本。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public PropertiesMutilang(Properties properties, String defaultString) {
		Objects.requireNonNull(properties, "入口参数 properties 不能为 null。");
		Objects.requireNonNull(defaultString, "入口参数 defaultString 不能为 null。");
		
		this.properties = properties;
		this.defaultString = defaultString;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.model.cfg.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
	 */
	@Override
	public String getString(T key) {
		return properties.getProperty(key.getName(), defaultString);
	}

}
