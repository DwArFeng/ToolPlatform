package com.dwarfeng.tp.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * �й���·���ļ���
 * @author  DwArFeng
 * @since 1.8
 */
public enum PathKey implements Name{
	
	/**��¼������*/
	LOGGER_SETTING("logger.setting"),
	
	/**�����������*/
	CONFIGURATION_PROGRAM("configuration.program"),
	
	/**��¼�������Ի�������*/
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
