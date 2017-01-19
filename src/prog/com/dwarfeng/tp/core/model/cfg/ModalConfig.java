package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.checker.IntegerConfigChecker;
import com.dwarfeng.tp.core.model.struct.DefaultConfigEntry;

/**
 * ģ̬���á�
 * <p> ģ̬�������û��޷�
 * @author  DwArFeng
 * @since 1.8
 */
public enum ModalConfig implements ConfigEntry {
	
	/**����������ʱ�Ŀ��*/
	STARTUP_MAINFRAME_WIDTH("startup.mainframe.width", "800", new IntegerConfigChecker(1, Integer.MAX_VALUE)),
	
	/**����������ʱ�ĸ߶�*/
	STARTUP_MAINFRAME_HEIGHT("startup.mainframe.height", "600", new IntegerConfigChecker(1, Integer.MAX_VALUE)),
	
	/**����������ʱ�����չ״̬*/
	STARTUP_MAINFRAME_EXTENDEDSTATE("startup.mainframe.extendedstate", "0", new IntegerConfigChecker());
	
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
