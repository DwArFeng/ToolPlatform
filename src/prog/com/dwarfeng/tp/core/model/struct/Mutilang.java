package com.dwarfeng.tp.core.model.struct;

import java.util.Locale;

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
	
	/**
	 * 将多语言化接口中的语言设置为指定的语言。
	 * <p> 该语言允许为 <code>null</code>，代表默认的语言。
	 * @param locale 指定的语言。
	 * @throws ConfigChangeFailedException 配置变更失败异常。
	 */
	public void setLocale(Locale locale) throws ConfigChangeFailedException;
	
	/**
	 * 设置语言为默认值。
	 */
	public void setLocale2Default();

}
