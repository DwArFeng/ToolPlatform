package com.dwarfeng.tp.core.view.struct;

import com.dwarfeng.tp.core.view.gui.SplashScreen;

/**
 * �������ڿ�������
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface SplashScreenController extends GuiController<SplashScreen>{
	
	/**
	 * ������ʾ�����������ϵ���Ϣ�ı���
	 * @param text ָ������Ϣ�ı�, <code>null</code>�൱�� <code>""</code>��
	 * @return �ò����Ƿ��������������˸ı䡣
	 */
	public boolean setMessage(String text);

}
