package com.dwarfeng.tp.model.cfg;

import com.dwarfeng.dutil.basic.prog.ObverserSet;

/**
 * ���ʻ��ӿڡ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface Mutilang extends ObverserSet<MutilangObverser>{
		/**
	 * ��ȡ��ǰ������ָ���ַ�������Ӧ���ַ�����
	 * @param stringField ָ�����ַ���
	 * @return �ڵ�ǰ������ָ�����ַ�������Ӧ���ַ�����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public String getString(StringKey stringField);
	
}
