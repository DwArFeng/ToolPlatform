package com.dwarfeng.tp.core.view.struct;

import java.awt.Component;

import com.dwarfeng.tp.core.model.struct.RunningTool;
import com.dwarfeng.tp.core.view.gui.MainFrame;

/**
 * �������������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface MainFrameController extends MutilangSupportedGuiController<MainFrame>{
	
	/**
	 * ��ȡ��������������״̬�ĸ߶ȡ�
	 * <p> ��������滹δ��ʼ�����򷵻� <code>-1</code>
	 * @return ��������������״̬�ĸ߶ȡ�
	 */
	public int getLastNormalHeight();
	
	/**
	 * ��ȡ���������������״̬�Ŀ�ȡ�
	 * <p> ��������滹δ��ʼ�����򷵻� <code>-1</code>��
	 * @return ���������������״̬�Ŀ�ȡ�
	 */
	public int getLastNormalWidth();
	
	/**
	 * �������������������״̬�ĸ߶ȡ�
	 * @param height ���������������״̬�ĸ߶ȡ�
	 * @return �ò����Ƿ������������˸ı䡣
	 */
	public boolean setLastNormalHeight(int height);
	
	/**
	 * �������������������״̬�Ŀ�ȡ�
	 * @param width ���������������״̬�Ŀ�ȡ�
	 * @return �ò����Ƿ������������˸ı䡣
	 */
	public boolean setLastNormalWidth(int width);

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
	
	/**
	 * ���ô��������ָ�������λ�á�
	 * @param component ָ���������
	 * @return �ò����Ƿ�ı��˿������е������
	 */
	public boolean setLocationRelativeTo(Component component);
	
	/**
	 * Ϊָ���������й���ָ�����������������
	 * <p> ���ҽ�����ڲ�����Ϊ <code>null</code>�������뵱ǰ�� toolRuntimeModel��ʱ�򣬲��ܹ�ָ�ɳɹ���
	 * @param runningTool ָ���������й��ߡ�
	 * @return �Ƿ���ܸ�ָ�ɡ�
	 */
	public boolean assignStream(RunningTool runningTool);
	
	/**
	 * ��ȡ�Ϸ����ĸ߶ȡ�
	 * @return �Ϸ����ĸ߶ȡ�
	 */
	public int getSouthHeight();
	
	/**
	 * �����Ϸ����ĸ߶ȡ�
	 * @param height ָ���ĸ߶ȡ�
	 * @return �ò����Ƿ�ı��˿������е������
	 */
	public boolean setSouthHeight(int height);
}
