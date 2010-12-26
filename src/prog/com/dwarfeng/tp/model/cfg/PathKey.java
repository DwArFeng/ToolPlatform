package com.dwarfeng.tp.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * �й���·���ļ���
 * @author  DwArFeng
 * @since 1.8
 */
public enum PathKey implements Name{
	
	/**��¼�����ü�*/
	LOGGER_CFG("logger-cfg"),
	
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
