package com.dwarfeng.tp.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * ������۲�����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface MainFrameObverser extends Obverser{
	
	/**
	 * ֪ͨ����Ĺرհ�ť�������
	 */
	public void fireWindowClosing();

	/**
	 * ֪ͨ���汻���
	 */
	public void fireFireWindowActivated();
	
}
