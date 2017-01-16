package com.dwarfeng.tp.core.view.struct;

import com.dwarfeng.tp.core.view.gui.MainFrame;

/**
 * �������������
 * @author DwArFeng
 * @since 1.8
 */
public interface MainFrameController extends MutilangSupportedGuiController<MainFrame>{
	
	/**
	 * ��ȡ������ĸ߶ȡ�
	 * <p> ��������滹δ��ʼ�����򷵻� <code>-1</code>
	 * @return ������ĸ߶ȡ�
	 */
	public int getHeight();
	
	/**
	 * ��ȡ������Ŀ�ȡ�
	 * <p> ��������滹δ��ʼ�����򷵻� <code>-1</code>��
	 * @return ������Ŀ�ȡ�
	 */
	public int getWidth();
	
	/**
	 * ����������ĸ߶ȡ�
	 * @param height ������ĸ߶ȡ�
	 * @return �ò����Ƿ������������˸ı䡣
	 */
	public boolean setHeight(int height);
	
	/**
	 * ����������Ŀ�ȡ�
	 * @param width ������Ŀ�ȡ�
	 * @return �ò����Ƿ������������˸ı䡣
	 */
	public boolean setWidth(int width);

	/**
	 * ��ȡ���������չģʽ��
	 * <p> ��������滹δʵ�������򷵻� <code>-1</code>��
	 * @return ���������չģʽ��
	 */
	public int getExtendedState();
	
	/**
	 * �������������չģʽ��
	 * @param state ���������չģʽ��
	 * @return �ò����Ƿ������������˸ı䡣
	 */
	public boolean setExtendedState(int state);
}
