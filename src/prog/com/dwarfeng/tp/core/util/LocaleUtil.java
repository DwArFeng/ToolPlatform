package com.dwarfeng.tp.core.util;

import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * 地区常用方法。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class LocaleUtil {

	/**
	 * 将指定的字符串转化为地区。
	 * @param s 指定的字符串。
	 * @return 由指定的字符串转换而成的地区。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public final static Locale parseLocale(String s){
		Objects.requireNonNull(s, "入口参数 s 不能为 null。");
		
		if(s.equals("")) return null;
		
		StringTokenizer tokenizer = new StringTokenizer(s, "_");
		String language = tokenizer.hasMoreTokens()? tokenizer.nextToken() : "";
		String country = tokenizer.hasMoreTokens()? tokenizer.nextToken() : "";
		String variant  = tokenizer.hasMoreTokens()? tokenizer.nextToken() : "";
		
		return new Locale(language, country, variant);
	}
	
	//禁止外部实例化
	private LocaleUtil() {}

}
