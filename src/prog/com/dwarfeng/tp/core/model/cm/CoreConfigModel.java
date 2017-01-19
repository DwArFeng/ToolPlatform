package com.dwarfeng.tp.core.model.cm;

import java.util.Locale;

/**
 * 核心配置模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface CoreConfigModel extends SyncConfigModel {
	
	/**
	 * 获取记录器多语言接口的当前语言。
	 * @return 记录器多语言接口当前的语言。
	 */
	public Locale getLoggerMutilangLocale();
	
	/**
	 * 获取标签多语言接口的当前语言。
	 * @return 标签多语言接口的当前语言。
	 */
	public Locale getLabelMutilangLocale();
	
	/**
	 * 获取程序启动时是否需要显示启动画面。
	 * @return 是否需要在启动时显示启动画面。
	 */
	public boolean isShowSplashScreen();
	
	/**
	 * 获取程序启动时启动窗口的最短显示时间。
	 * @return 程序启动时启动窗口的最短显示时间。
	 */
	public int getStartupSplashDuration();

}
