package com.dwarfeng.tp.model.cfg;

/**
 * ���������ʱ��ۡ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface StartupAper {
	
	/**
	 * ��ȡ��������ʱ����ĸ߶ȡ�
	 * @return �߶ȡ�
	 */
	public int getHeight();
	
	/**
	 * ��ȡ��������ʱ����Ŀ�ȡ�
	 * @return ��ȡ�
	 */
	public int getWidth();
	
	/**
	 * ��ȡ��������ʱ���������״̬��
	 * @return ����״̬��
	 */
	public int getExtendedState();

}
