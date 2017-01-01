package com.dwarfeng.tp.core.control.proc;

import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * ������������
 * @author  DwArFeng
 * @since 1.8
 */
public interface ActionProcessor {
	
	/**
	 * ���������������
	 * @throws ProcessException �����쳣��
	 * @throws IllegalStateException �ö���û����ȷ��״̬��δ��������ִ�С�
	 */
	public void start() throws ProcessException;

}
