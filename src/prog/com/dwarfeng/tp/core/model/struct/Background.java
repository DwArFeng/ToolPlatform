package com.dwarfeng.tp.core.model.struct;

/**
 * ��̨�ӿڡ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface Background {

	/**
	 * ���̨���ύһ�����̡�
	 * <p> ���ύ�Ĺ��̽����ں�̨��ִ�С�
	 * @param process ָ���Ĺ��̡�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void submit(Process process);
	
}
