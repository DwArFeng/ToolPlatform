package com.dwarfeng.tp.core.model.cfg;

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
	CONFIGURATION_CORE("configuration.core"),
	
	/**���ɼ�����*/
	CONFIGURATION_INVISIBLE("configuration.invisible"),
	
	/**��¼�������Ի�������*/
	MUTILANG_LOGGER_SETTING("mutilang.logger.setting"),
	
	/**��¼�������Ի�������*/
	MUTILANG_LABEL_SETTING("mutilang.label.setting")
	
	
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
