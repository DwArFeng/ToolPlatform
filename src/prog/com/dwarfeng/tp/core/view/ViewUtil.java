package com.dwarfeng.tp.core.view;

import javax.swing.JOptionPane;

/**
 * 视图的一些功能。
 * @author DwArFeng
 * @since 1.8
 */
public final class ViewUtil {
	
	/**
	 * 提供一个紧急的窗口，显示信息。
	 * <p> 该窗口为程序初始化，引用网络还未形成之前出现的异常提供信息展示，也可以用于其它致命错误发生时做紧急展示。
	 * <br> 调用此方法之后，程序应立刻退出。
	 * @param title 指定的标题。
	 * @param message 指定的信息。
	 */
	public static void showEmergentMessage(String title, String message){
		title = title == null ? "null" : title;
		message = message == null ? "null" : message;
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE, null);
	}
	
	//禁止外部实例化
	private ViewUtil(){}
}
