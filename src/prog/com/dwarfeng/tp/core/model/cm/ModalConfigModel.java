package com.dwarfeng.tp.core.model.cm;

/**
 * ģ̬����ģ�͡�
 * @author DwArFeng
 * @since 1.8
 */
public interface ModalConfigModel extends SyncConfigModel {
	
	/**
	 * ��ȡ������ĳ�ʼ���߶ȡ�
	 * @return ������ĳ�ʼ���߶ȡ�
	 */
	public int getMainFrameStartupHeight();
	
	/**
	 * ��ȡ������ĳ�ʼ����ȡ�
	 * @return ������ĳ�ʼ����ȡ�
	 */
	public int getMainFrameStartupWidth();
	
	/**
	 * ��ȡ������ĳ�ʼ����չ״̬��
	 * @return ������ĳ�ʼ����չ״̬��
	 */
	public int getMainFrameStartupExtendedState();

}
