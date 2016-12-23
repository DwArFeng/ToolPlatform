package com.dwarfeng.tp.model.setting.cfg;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.checker.IntegerConfigChecker;

/**
 * 程序的外观配置。
 * @author  DwArFeng
 * @since 1.8
 */
public enum AppearanceConfig implements ConfigEntry {
	
	/**界面在启动时的宽度*/
	FRAME_STARTUP_WIDTH("frame.startup.width", "800", new IntegerConfigChecker(1, Integer.MAX_VALUE)),
	
	/**界面在启动时的高度*/
	FRAME_STARTUP_HEIGHT("frame.startup.height", "600", new IntegerConfigChecker(1, Integer.MAX_VALUE)),
	
	/**界面在启动时候的拓展状态*/
	FRAME_STARTUP_EXTENDEDSTATE("frame.startup.extendedstate", "0", new IntegerConfigChecker());
	
	;

	private final ConfigEntry configEntry;
	
	private AppearanceConfig(String keyName, String defaultValue, ConfigChecker checker) {
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
