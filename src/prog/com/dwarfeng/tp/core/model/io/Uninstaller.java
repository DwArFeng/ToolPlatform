package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * ж������
 * <p> ж��ĳ�����������ָ����ģ��֪ͨ��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface Uninstaller<T> {
	
	/**
	 * ж��ĳ���������ָ֪ͨ����ģ�͡�
	 * @param model ָ����ģ�͡�
	 * @throws ProcessException �����쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void uninstall(T model) throws ProcessException;

}
