package com.dwarfeng.tp.model.setting.sub;

import java.util.Locale;

/**
 * 国际化接口。
 * @author  DwArFeng
 * @since 1.8
 */
public interface International {
	
	/**
	 * 获得此国际化接口中所支持的所有语言。
	 * @return 此国际化接口中所支持的所有语言。
	 */
	public Locale[] getSupportLocales();
	
	/**
	 * 获取程序中当前的语言环境。
	 * @return 程序中当前的语言环境。
	 */
	public Locale getCurrentLocale();

	/**
	 * 获取当前语言下指定字符域所对应的字符串。
	 * @param stringField 指定的字符域。
	 * @return 在当前语言下指定的字符域所对应的字符串。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public String getString(StringField stringField);
	
	/**
	 * 获取指定语言下的指定字符域所对应的字符串。
	 * @param stringField 指定的字符域。
	 * @param locale 指定的语言。
	 * @return 在指定的语言下的指定的字符域所对应的字符串。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public String getString(StringField stringField, Locale locale);
	
}
