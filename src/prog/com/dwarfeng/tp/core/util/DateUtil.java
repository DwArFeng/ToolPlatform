package com.dwarfeng.tp.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * ���ڳ��ù��ߡ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DateUtil {
	
	private final static SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * ��ʽ��������ڡ�
	 * @param date ָ�������ڡ�
	 * @return ָ�����ڵĸ�ʽ���ַ�����
	 */
	public final static String formatDate(Date date){
		Objects.requireNonNull(date, "��ڲ��� date ����Ϊ null��");
		return dateFormatter.format(date);
	}
	
	//��ֹ�ⲿʵ����
	private DateUtil() {}
	
}
