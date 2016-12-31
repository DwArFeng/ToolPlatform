package com.dwarfeng.tp.core.control.proc;

import com.dwarfeng.tp.core.model.ModelManager;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.struct.EmergencyException;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.ProgramLogger;
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
	 * @param preMutilang ��¼�������Ի�δ����֮ǰʹ�õĶ����ԡ�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 * @throws ���ʼ��ʧ�ܶ��׳��Ľ����쳣��
	 */
	public void init(ProgramLogger preLogger, Mutilang<LoggerStringKey> preMutilang) throws EmergencyException;
	
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
