package com.dwarfeng.tp.core.control.proc;

import com.dwarfeng.tp.core.model.ModelManager;
import com.dwarfeng.tp.core.model.struct.InitializeFailedException;
import com.dwarfeng.tp.core.view.ViewManager;

/**
 * ��ʼ������
 * <p> ����������ʼ���Ĺ��̡�
 * @author DwArFeng
 * @since 1.8
 */
public interface Initializer {

	/**
	 * ���г�ʼ����
	 * @param preLogger ��¼����δ����֮ǰʹ�õļ�¼����
	 * @throws ���ʼ��ʧ�ܶ��׳��Ľ����쳣��
	 */
	public void init() throws InitializeFailedException;
	
	/**
	 * ��ȡ��ʼ�����е�ģ�͹�������
	 * @return ģ�͹�������
	 */
	public ModelManager getModelManager();
	
	/**
	 * ��ȡ��ʼ�����е���ͼ��������
	 * @return ��ͼ��������
	 */
	public ViewManager getViewManager();

}
