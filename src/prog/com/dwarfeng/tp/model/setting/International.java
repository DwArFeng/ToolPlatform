package com.dwarfeng.tp.model.setting;

import com.dwarfeng.dutil.basic.prog.ObverserSet;

/**
 * ���ʻ��ӿڡ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface International extends ObverserSet<InternationalObverser>{
		/**
	 * ��ȡ��ǰ������ָ���ַ�������Ӧ���ַ�����
	 * @param stringField ָ�����ַ���
	 * @return �ڵ�ǰ������ָ�����ַ�������Ӧ���ַ�����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public String getString(StringField stringField);
	
}
