package com.dwarfeng.tp.core.model.cm;

/**
 * ģ̬����ģ�͡�
 * @author DwArFeng
 * @since 0.0.0-alpha
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

	/**
	 * ��ȡ������ĳ�ʼ���Ϸ����߶ȡ�
	 * @return ������ĳ�ʼ���Ϸ����߶ȡ�
	 */
	public int getMainFrameStartupSouthHeight();
}
