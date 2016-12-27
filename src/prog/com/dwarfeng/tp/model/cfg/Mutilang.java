package com.dwarfeng.tp.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 多语言接口。
 * @author DwArFeng
 * @since 1.8
 */
public interface Mutilang<T extends Name> {
	
	/**
	 * 获取当前语言下指定字符域所对应的字符串。
	 * @param key 指定的字符键。
	 * @return 指定的字符键所对应的字符。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public String getString(T key);

}
