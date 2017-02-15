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
 * ��������ö�١�
 * <p> ��ö�ټ�¼��������ʱ����Ҫ�����е����á�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public enum CoreConfig implements ConfigEntry{
	
	/**��¼����ʹ������*/
	MUTILANG_LOGGER("mutilang.logger", "", new NonNullConfigChecker()),
	
	/**��ǩ��ʹ������*/
	MUTILANG_LABEL("mutilang.label", "", new NonNullConfigChecker()),
	
	/**��������ʱ���Ƿ���Ҫ��ʾ���ִ���*/
	STARTUP_SPLASH_ISSHOW("startup.splash.isshow", "TRUE", new BooleanConfigChecker()),
	
	/**��������ʱ����ʾ���ִ����ʱ��*/
	STARTUP_SHPLASH_DURATION("startup.splash.duration", "2000", new IntegerConfigChecker(0, Integer.MAX_VALUE)),
	
	/**�Ƿ��Զ�ȡ���Ѿ������Ĺ���*/
	RUNNINGTOOL_AUTOTAKE("runningtool.autotake", "FALSE", new BooleanConfigChecker()),
	
	/**������ʷ������¼����*/
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
