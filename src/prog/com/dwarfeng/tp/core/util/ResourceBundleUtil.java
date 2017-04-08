package com.dwarfeng.tp.core.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * ResourceBundle中的常用工具。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class ResourceBundleUtil {
	
	/**
	 * 以映射的形式返回指定的ResourceBundle。
	 * <p> 返回的映射时不可更改的，尝试调用其编辑方法会抛出 {@link UnsupportedOperationException}。
	 * @param resourceBundle 指定的ResourceBundle。
	 * @return ResourceBundle的映射形式。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public final static Map<String, String> toMap(ResourceBundle resourceBundle){
		Objects.requireNonNull(resourceBundle, "入口参数 resourceBundle 不能为 null。");
		Map<String, String> map = new HashMap<>();
		for(String key : resourceBundle.keySet()){
			map.put(key, resourceBundle.getString(key));
		}
		return Collections.unmodifiableMap(map);
	}
	
	//禁止外部实例化。
	private ResourceBundleUtil() {}

}
