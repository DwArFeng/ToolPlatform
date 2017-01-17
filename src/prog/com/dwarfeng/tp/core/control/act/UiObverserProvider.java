package com.dwarfeng.tp.core.control.act;

import com.dwarfeng.tp.core.view.obv.MainFrameObverser;

/**
 * 界面观察器提供器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface UiObverserProvider {

	/**
	 * 获得主界面观察器。
	 * @return 主界面观察器。
	 */
	public MainFrameObverser getMainFrameProvider();

}
