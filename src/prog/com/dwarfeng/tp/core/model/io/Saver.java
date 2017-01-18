package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.dutil.basic.io.SaveFailedException;

/**
 * ��������
 * @author DwArFeng
 * @since 1.8
 */
public interface Saver<T> {

	/**
	 * ��ָ���Ķ�����ģ���б������ݡ�
	 * @param mutilangModel ָ���Ķ�����ģ�͡�
	 * @throws SaveFailedException ����ʧ���쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void save(T model) throws SaveFailedException;
	
}
