package com.dwarfeng.tp.core.model.eum;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.checker.BooleanConfigChecker;
import com.dwarfeng.dutil.develop.cfg.checker.IntegerConfigChecker;
import com.dwarfeng.dutil.develop.cfg.checker.NonNullConfigChecker;
import com.dwarfeng.tp.core.model.struct.DefaultConfigEntry;

/**
 * 程序配置枚举。
 * <p> 此枚举记录程序运行时所需要的所有的配置。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public enum CoreConfig implements ConfigEntry{
	
	/**记录器的使用语言*/
	MUTILANG_LOGGER("mutilang.logger", "", new NonNullConfigChecker()),
	
	/**标签的使用语言*/
	MUTILANG_LABEL("mutilang.label", "", new NonNullConfigChecker()),
	
	/**在启动的时候是否需要显示闪现窗格*/
	STARTUP_SPLASH_ISSHOW("startup.splash.isshow", "TRUE", new BooleanConfigChecker()),
	
	/**在启动的时候显示闪现窗格的时间*/
	STARTUP_SHPLASH_DURATION("startup.splash.duration", "2000", new IntegerConfigChecker(0, Integer.MAX_VALUE)),
	
	/**是否自动取出已经结束的工具*/
	RUNNINGTOOL_AUTOTAKE("runningtool.autotake", "FALSE", new BooleanConfigChecker()),
	
	/**工具历史的最大记录数量*/
	TOOLHISTORY_MAXSIZE("toolhistory.maxsize", "100", new IntegerConfigChecker(0, Integer.MAX_VALUE));
	
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
