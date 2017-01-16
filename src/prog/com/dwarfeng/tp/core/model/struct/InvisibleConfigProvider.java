package com.dwarfeng.tp.core.model.struct;

import com.dwarfeng.tp.core.model.cm.SyncConfigModel;

/**
 * ���ɼ������ṩ����
 * @author  DwArFeng
 * @since 1.8
 */
public interface InvisibleConfigProvider {
	
	/**
	 * ��ȡ���ṩ�������õ�����ģ�͡�
	 * @return ���ṩ�������õ�����ģ�͡�
	 */
	public SyncConfigModel getConfigModel();
	
	/**
	 * ��ȡ������ĳ�ʼ���߶ȡ�
	 * @return ������ĳ�ʼ���߶ȡ�
	 * @throws IllegalStateException ���ģ����û����Ӧ�ļ���
	 */
	public int getMainFrameStartupHeight();
	
	/**
	 * ��ȡ������ĳ�ʼ����ȡ�
	 * @return ������ĳ�ʼ����ȡ�
	 * @throws IllegalStateException ���ģ����û����Ӧ�ļ���
	 */
	public int getMainFrameStartupWidth();
	
	/**
	 * ��ȡ������ĳ�ʼ����չ״̬��
	 * @return ������ĳ�ʼ����չ״̬��
	 * @throws IllegalStateException ���ģ����û����Ӧ�ļ���
	 */
	public int getMainFrameStartupExtendedState();

}
