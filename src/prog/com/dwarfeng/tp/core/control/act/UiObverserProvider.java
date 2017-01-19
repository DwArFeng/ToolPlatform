package com.dwarfeng.tp.core.control.act;

import com.dwarfeng.tp.core.view.obv.MainFrameObverser;

/**
 * 界面观察器提供器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface UiObverserProvider {

	/**
	 * 获得主界面观察器。
	 * @return 主界面观察器。
	 */
	public MainFrameObverser getMainFrameProvider();

}
