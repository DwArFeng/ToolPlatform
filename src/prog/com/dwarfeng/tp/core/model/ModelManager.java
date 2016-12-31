package com.dwarfeng.tp.core.model;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.PlatformLogger;

/**
 * 程序的模型管理器。
 * @author DwArFeng
 * @since 1.8
 */
public interface ModelManager {
	
	/**
	 * 获取平台的日志记录器。
	 * @return 平台的日志记录器。
	 */
	public PlatformLogger getLogger();
	
	/**
	 * 获取记录器的多语言接口。
	 * @return 记录器的多语言接口。
	 */
	public Mutilang<LoggerStringKey> getLoggerMutilang();
	
	/**
	 * 获取标签的多语言接口。
	 * @return 标签的多语言接口。
	 */
	public Mutilang<LabelStringKey> getLabelMutilang();
	
	/**
	 * 获取程序的核心配置。
	 * @return 程序的核心配置。
	 */
	public ConfigModel getCoreConfigModel();
	
	/**
	 * 获取程序的不可见配置。
	 * @return 程序的不可见配置。
	 */
	public ConfigModel getInvisibleConfigModel();
}
