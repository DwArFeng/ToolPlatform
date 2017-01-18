package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.dutil.basic.io.LoadFailedException;

/**
 * ��ȡ����
 * @author DwArFeng
 * @since 1.8
 */
public interface Loader<T> {

	/**
	 * ��ָ����ģ���ж�ȡ���ݡ�
	 * @param loggerModel ָ����ģ��
	 * @throws LoadFailedException ��ȡʧ���쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void load(T model) throws LoadFailedException;
	
}
