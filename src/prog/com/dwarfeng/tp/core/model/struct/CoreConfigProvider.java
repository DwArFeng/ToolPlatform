package com.dwarfeng.tp.core.model.struct;

import java.util.Locale;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;

/**
 * 核心配置提供器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface CoreConfigProvider {
	
	/**
	 * 获取该提供器所引用的配置模型。
	 * @return 该提供器所引用的配置模型。
	 */
	public ConfigModel getConfigModel();
	
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

}
