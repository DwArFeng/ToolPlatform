package com.dwarfeng.tp.core.model.struct;

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

}
