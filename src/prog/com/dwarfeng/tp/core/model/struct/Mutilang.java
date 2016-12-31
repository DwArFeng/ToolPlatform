package com.dwarfeng.tp.core.model.struct;

import java.util.Locale;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * �����Խӿڡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface Mutilang<T extends Name> {
	
	/**
	 * ��ȡ��ǰ������ָ���ַ�������Ӧ���ַ�����
	 * @param key ָ�����ַ�����
	 * @return ָ�����ַ�������Ӧ���ַ���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public String getString(T key);
	
	/**
	 * �������Ի��ӿ��е���������Ϊָ�������ԡ�
	 * <p> ����������Ϊ <code>null</code>������Ĭ�ϵ����ԡ�
	 * @param locale ָ�������ԡ�
	 * @throws ConfigChangeFailedException ���ñ��ʧ���쳣��
	 */
	public void setLocale(Locale locale) throws ConfigChangeFailedException;
	
	/**
	 * ��������ΪĬ��ֵ��
	 */
	public void setLocale2Default();

}
