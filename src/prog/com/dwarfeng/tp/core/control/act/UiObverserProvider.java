package com.dwarfeng.tp.core.control.act;

import com.dwarfeng.tp.core.view.obv.MainFrameObverser;

/**
 * ����۲����ṩ����
 * @author  DwArFeng
 * @since 1.8
 */
public interface UiObverserProvider {

	/**
	 * ���������۲�����
	 * @return ������۲�����
	 */
	public MainFrameObverser getMainFrameProvider();

}
