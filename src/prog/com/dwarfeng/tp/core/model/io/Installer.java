package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * ��װ����
 * <p> ��װĳ�����������ָ����ģ��֪ͨ��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface Installer<T> {
	
	/**
	 * ��װĳ���������ָ֪ͨ����ģ�͡�
	 * @param model ָ����ģ�͡�
	 * @throws ProcessException �����쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void install(T model) throws ProcessException;

}
