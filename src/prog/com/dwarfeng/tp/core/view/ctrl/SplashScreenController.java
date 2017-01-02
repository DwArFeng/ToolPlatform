package com.dwarfeng.tp.core.view.ctrl;

/**
 * 启动窗口控制器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface SplashScreenController {
	
	/**
	 * 关闭此启动窗口。
	 */
	public void dispose();
	
	/**
	 * 设置显示在启动窗口上的文本。
	 * @param text 指定的文本, <code>null</code>相当于 <code>""</code>。
	 */
	public void setText(String text);

}
