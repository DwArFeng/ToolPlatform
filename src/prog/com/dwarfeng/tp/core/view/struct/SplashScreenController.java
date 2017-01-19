package com.dwarfeng.tp.core.view.struct;

import com.dwarfeng.tp.core.view.gui.SplashScreen;

/**
 * 启动窗口控制器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface SplashScreenController extends GuiController<SplashScreen>{
	
	/**
	 * 设置显示在启动窗口上的信息文本。
	 * @param text 指定的信息文本, <code>null</code>相当于 <code>""</code>。
	 * @return 该操作是否对启动窗口造成了改变。
	 */
	public boolean setMessage(String text);

}
