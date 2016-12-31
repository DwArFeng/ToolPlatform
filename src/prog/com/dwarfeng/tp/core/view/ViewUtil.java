package com.dwarfeng.tp.core.view;

import javax.swing.JOptionPane;

/**
 * ��ͼ��һЩ���ܡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class ViewUtil {
	
	/**
	 * �ṩһ�������Ĵ��ڣ���ʾ��Ϣ��
	 * <p> �ô���Ϊ�����ʼ�����������绹δ�γ�֮ǰ���ֵ��쳣�ṩ��Ϣչʾ��Ҳ����������������������ʱ������չʾ��
	 * <br> ���ô˷���֮�󣬳���Ӧ�����˳���
	 * @param title ָ���ı��⡣
	 * @param message ָ������Ϣ��
	 */
	public static void showEmergentMessage(String title, String message){
		title = title == null ? "null" : title;
		message = message == null ? "null" : message;
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE, null);
	}
	
	//��ֹ�ⲿʵ����
	private ViewUtil(){}
}
