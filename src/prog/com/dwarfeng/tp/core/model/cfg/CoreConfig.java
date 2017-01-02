package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.checker.BooleanConfigChecker;
import com.dwarfeng.dutil.develop.cfg.checker.NonNullConfigChecker;
import com.dwarfeng.tp.core.model.struct.DefaultConfigEntry;

/**
 * 程序配置枚举。
 * <p> 此枚举记录程序运行时所需要的所有的配置。
 * @author  DwArFeng
 * @since 1.8
 */
public enum CoreConfig implements ConfigEntry{
	
	/**记录器的使用语言*/
	MUTILANG_LOGGER("mutilang.logger", "", new NonNullConfigChecker()),
	
	/**标签的使用语言*/
	MUTILANG_LABEL("mutilang.label", "", new NonNullConfigChecker()),
	
	/**在启动的时候是否需要显示闪现窗格*/
	STARTUP_SPLASH_ISSHOW("startup.splash.isshow", "TRUE", new BooleanConfigChecker()),
	
	;
	
	private final ConfigEntry configEntry;
	
	private CoreConfig(String keyName, String defaultValue, ConfigChecker checker) {
		this.configEntry = new DefaultConfigEntry(keyName, defaultValue, checker);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigEntry#getConfigKey()
	 */
	@Override
	public ConfigKey getConfigKey() {
		return configEntry.getConfigKey();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigEntry#getConfigFirmProps()
	 */
	@Override
	public ConfigFirmProps getConfigFirmProps() {
		return configEntry.getConfigFirmProps();
	}

}
