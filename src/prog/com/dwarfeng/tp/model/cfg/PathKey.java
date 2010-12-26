package com.dwarfeng.tp.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 有关于路径的键。
 * @author  DwArFeng
 * @since 1.8
 */
public enum PathKey implements Name{
	
	/**记录器配置键*/
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
