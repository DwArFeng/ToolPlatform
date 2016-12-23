package com.dwarfeng.tp.model.setting.sub;

import java.util.Locale;

/**
 * ���ʻ��ӿڡ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface International {
	
	/**
	 * ��ô˹��ʻ��ӿ�����֧�ֵ��������ԡ�
	 * @return �˹��ʻ��ӿ�����֧�ֵ��������ԡ�
	 */
	public Locale[] getSupportLocales();
	
	/**
	 * ��ȡ�����е�ǰ�����Ի�����
	 * @return �����е�ǰ�����Ի�����
	 */
	public Locale getCurrentLocale();

	/**
	 * ��ȡ��ǰ������ָ���ַ�������Ӧ���ַ�����
	 * @param stringField ָ�����ַ���
	 * @return �ڵ�ǰ������ָ�����ַ�������Ӧ���ַ�����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public String getString(StringField stringField);
	
	/**
	 * ��ȡָ�������µ�ָ���ַ�������Ӧ���ַ�����
	 * @param stringField ָ�����ַ���
	 * @param locale ָ�������ԡ�
	 * @return ��ָ���������µ�ָ�����ַ�������Ӧ���ַ�����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public String getString(StringField stringField, Locale locale);
	
}
