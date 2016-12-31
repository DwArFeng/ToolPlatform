package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 有关于路径的键。
 * @author  DwArFeng
 * @since 1.8
 */
public enum PathKey implements Name{
	
	/**记录器设置*/
	LOGGER_SETTING("logger.setting"),
	
	/**主程序的配置*/
	CONFIGURATION_CORE("configuration.core"),
	
	/**记录器多语言化的设置*/
	MUTILANG_LOGGER_SETTING("mutilang.logger.setting")
	
	
	
	;

	private final String name;
	
	private PathKey(String name) {
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
}
