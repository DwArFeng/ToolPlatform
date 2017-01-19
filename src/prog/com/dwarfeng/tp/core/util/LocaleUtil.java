package com.dwarfeng.tp.core.util;

import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * �������÷�����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class LocaleUtil {

	/**
	 * ��ָ�����ַ���ת��Ϊ������
	 * @param s ָ�����ַ�����
	 * @return ��ָ�����ַ���ת�����ɵĵ�����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public final static Locale parseLocale(String s){
		Objects.requireNonNull(s, "��ڲ��� s ����Ϊ null��");
		
		if(s.equals("")) return null;
		
		StringTokenizer tokenizer = new StringTokenizer(s, "_");
		String language = tokenizer.hasMoreTokens()? tokenizer.nextToken() : "";
		String country = tokenizer.hasMoreTokens()? tokenizer.nextToken() : "";
		String variant  = tokenizer.hasMoreTokens()? tokenizer.nextToken() : "";
		
		return new Locale(language, country, variant);
	}
	
	//��ֹ�ⲿʵ����
	private LocaleUtil() {}

}
