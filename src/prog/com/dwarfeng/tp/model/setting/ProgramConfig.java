package com.dwarfeng.tp.model.setting;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;

/**
 * ��������ö�١�
 * <p> ��ö�ټ�¼��������ʱ����Ҫ�����е����á�
 * @author  DwArFeng
 * @since 1.8
 */
public enum ProgramConfig implements ConfigEntry{
	
	PROGRAM_INTERNATIONAL_LANGUAGE("program.international.language", null, new TrueConfigChecker()),
	
	;
	
	private final ConfigEntry configEntry;
	
	private ProgramConfig(String keyName, String defaultValue, ConfigChecker checker) {
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
