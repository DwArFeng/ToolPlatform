package com.dwarfeng.tp.view;

import java.util.Objects;

import javax.swing.JOptionPane;

import com.dwarfeng.tp.model.setting.MutilangObverser;
import com.dwarfeng.tp.view.gui.MutilangAper;

/**
 * ��ͼ��һЩ���ܡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class ViewUtil {

	/**
	 * ��ȡһ��ͨ��ָ�����ʻ�������ɵĹ��ʻ��۲�����
	 * @param aper ָ���Ĺ��ʻ���ۡ�
	 * @return ͨ��ָ���Ĺ��ʻ�������ɵĹ��ʻ��۲�����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static MutilangObverser newMutilangObverser(MutilangAper aper){
		Objects.requireNonNull(aper, "��ڲ��� aper ����Ϊ null");
		return new MutilangAperObverser(aper);
	}
	
	private static final class MutilangAperObverser implements MutilangObverser{

		private final MutilangAper aper;
		
		public MutilangAperObverser(MutilangAper aper) {
			this.aper = aper;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.setting.MutilangObverser#fireLanguageChanged()
		 */
		@Override
		public void fireLanguageChanged() {
			aper.refreshLabels();
		}
		
	}
	
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
