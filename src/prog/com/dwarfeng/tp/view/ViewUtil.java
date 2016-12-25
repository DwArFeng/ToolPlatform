package com.dwarfeng.tp.view;

import java.util.Objects;

import javax.swing.JOptionPane;

import com.dwarfeng.tp.model.setting.MutilangObverser;
import com.dwarfeng.tp.view.gui.MutilangAper;

/**
 * 视图的一些功能。
 * @author DwArFeng
 * @since 1.8
 */
public final class ViewUtil {

	/**
	 * 获取一个通过指定国际化外观生成的国际化观察器。
	 * @param aper 指定的国际化外观。
	 * @return 通过指定的国际化外观生成的国际化观察器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static MutilangObverser newMutilangObverser(MutilangAper aper){
		Objects.requireNonNull(aper, "入口参数 aper 不能为 null");
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
