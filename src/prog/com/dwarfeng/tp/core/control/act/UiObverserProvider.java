package com.dwarfeng.tp.core.control.act;

import com.dwarfeng.tp.core.view.obv.MainFrameObverser;

/**
 * ����۲����ṩ����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface UiObverserProvider {

	/**
	 * ���������۲�����
	 * @return ������۲�����
	 */
	public MainFrameObverser getMainFrameProvider();

}
