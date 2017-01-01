package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.checker.IntegerConfigChecker;
import com.dwarfeng.tp.core.model.struct.DefaultConfigEntry;

/**
 * ���ɼ����á�
 * <p> ���ɼ��������û��޷�
 * @author  DwArFeng
 * @since 1.8
 */
public enum InvisibleConfig implements ConfigEntry {
	
	/**����������ʱ�Ŀ���*/
	FRAME_STARTUP_WIDTH("invisible.frame.startup.width", "800", new IntegerConfigChecker(1, Integer.MAX_VALUE)),
	
	/**����������ʱ�ĸ߶�*/
	FRAME_STARTUP_HEIGHT("invisible.frame.startup.height", "600", new IntegerConfigChecker(1, Integer.MAX_VALUE)),
	
	/**����������ʱ�����չ״̬*/
	FRAME_STARTUP_EXTENDEDSTATE("invisible.frame.startup.extendedstate", "0", new IntegerConfigChecker());
	
	;

	private final ConfigEntry configEntry;
	
	private InvisibleConfig(String keyName, String defaultValue, ConfigChecker checker) {
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