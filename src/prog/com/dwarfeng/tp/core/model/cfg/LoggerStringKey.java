package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 记录器文本键。
 * <p> 该键枚举用于实现记录器的多语言。
 * @author  DwArFeng
 * @since 1.8
 */
public enum LoggerStringKey implements Name{
	
	ActionProcessor_start_1("ActionProcessor.start.1"),
	ActionProcessor_start_2("ActionProcessor.start.2"),
	ActionProcessor_start_3("ActionProcessor.start.3"),
	ActionProcessor_start_4("ActionProcessor.start.4"),
	ActionProcessor_start_5("ActionProcessor.start.5"),
	ActionProcessor_start_6("ActionProcessor.start.6"),
	ActionProcessor_start_7("ActionProcessor.start.7"),
	ActionProcessor_start_8("ActionProcessor.start.8"),
	ActionProcessor_start_9("ActionProcessor.start.9"),
	ActionProcessor_start_10("ActionProcessor.start.10"),
	ActionProcessor_start_11("ActionProcessor.start.11"),
	ActionProcessor_start_12("ActionProcessor.start.12"),
	ActionProcessor_start_13("ActionProcessor.start.13"),
	ActionProcessor_start_14("ActionProcessor.start.14"),

	
	;

	private String name;
	
	private LoggerStringKey(String name) {
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
