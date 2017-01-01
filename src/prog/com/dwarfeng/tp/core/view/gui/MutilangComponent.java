package com.dwarfeng.tp.core.view.gui;

import com.dwarfeng.tp.core.model.struct.Mutilang;

/**
 * ���ж����Թ��ܵĽ��档
 * @author DwArFeng
 * @since 1.8
 */
public interface MutilangComponent {
	
	/**
	 * �������еı�ǩ��
	 * <p> �ö�������ʹ�����е������ı��ֶθ���Ϊ��ڶ����Խӿ���ָ�����ı���
	 * @param labelMutilang ������µı�ǩ�����Խӿڡ�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void refreshLabels(Mutilang mutilang);

}
