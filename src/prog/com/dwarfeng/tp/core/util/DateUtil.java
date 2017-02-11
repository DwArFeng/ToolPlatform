package com.dwarfeng.tp.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 日期常用工具。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DateUtil {
	
	private final static SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 格式化输出日期。
	 * @param date 指定的日期。
	 * @return 指定日期的格式化字符串。
	 */
	public final static String formatDate(Date date){
		Objects.requireNonNull(date, "入口参数 date 不能为 null。");
		return dateFormatter.format(date);
	}
	
	//禁止外部实例化
	private DateUtil() {}
	
}
