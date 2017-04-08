package com.dwarfeng.tp.core.model.eum;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.checker.IntegerConfigChecker;
import com.dwarfeng.tp.core.model.struct.DefaultConfigEntry;

/**
 * 模态配置。
 * <p> 模态配置是用户无法
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public enum ModalConfig implements ConfigEntry {
	
	/**界面在启动时的宽度*/
	STARTUP_MAINFRAME_WIDTH("startup.mainframe.width", "800", new IntegerConfigChecker(1, Integer.MAX_VALUE)),
	
	/**界面在启动时的高度*/
	STARTUP_MAINFRAME_HEIGHT("startup.mainframe.height", "600", new IntegerConfigChecker(1, Integer.MAX_VALUE)),
	
	/**界面在启动时候的拓展状态*/
	STARTUP_MAINFRAME_EXTENDEDSTATE("startup.mainframe.extendedstate", "0", new IntegerConfigChecker()),
	
	/**界面在踢动时的南方面板高度*/
	STARTUP_MAINFRAME_SOUTHHEIGHT("startup.mainframe.southhtight", "100", new IntegerConfigChecker(1, Integer.MAX_VALUE)),
	
	;

	private final ConfigEntry configEntry;
	
	private ModalConfig(String keyName, String defaultValue, ConfigChecker checker) {
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
