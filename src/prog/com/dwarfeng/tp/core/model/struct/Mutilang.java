package com.dwarfeng.tp.core.model.struct;

/**
 * �����Խӿڡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface Mutilang {
	
	/**
	 * ��ȡ��ǰ������ָ���ַ�������Ӧ���ַ�����
	 * @param key ָ�����ַ�����
	 * @return ָ�����ַ�������Ӧ���ַ���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 * @throws IllegalArgumentException ��ڲ������ܸö����Խӿڵ�֧�֡�
	 */
	public String getString(String key);

}
