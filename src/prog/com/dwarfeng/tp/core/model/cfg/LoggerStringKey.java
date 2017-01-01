package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 记录器文本键。
 * <p> 该键枚举用于实现记录器的多语言。
 * @author  DwArFeng
 * @since 1.8
 */
public enum LoggerStringKey implements Name{
	
	Initializer_0("Initializer.0"),
	Initializer_1("Initializer.1"),
	Initializer_2("Initializer.2"),
	Initializer_3("Initializer.3"),
	Initializer_4("Initializer.4"),
	Initializer_5("Initializer.5"),
	Initializer_6("Initializer.6"),
	Initializer_7("Initializer.7"),
	Initializer_8("Initializer.8"),
	Initializer_9("Initializer.9"),
	Initializer_10("Initializer.10"),
	Initializer_11("Initializer.11"),
	Initializer_12("Initializer.12"),
	Initializer_13("Initializer.13"),
	Initializer_14("Initializer.14"),
	Initializer_15("Initializer.15"),
	Initializer_16("Initializer.16"),
	Initializer_17("Initializer.17"),
	Initializer_18("Initializer.18"),
	Initializer_19("Initializer.19"),
	Initializer_20("Initializer.20"),
	Initializer_21("Initializer.21"),
	Initializer_24("Initializer.24"),
	Initializer_26("Initializer.26"),
	Initializer_27("Initializer.27"),
	Initializer_28("Initializer.28"),
	Initializer_29("Initializer.29"),
	Initializer_30("Initializer.30"),
	Initializer_31("Initializer.31"),
	Initializer_32("Initializer.32"),
	Initializer_33("Initializer.33"),
	Initializer_34("Initializer.34"),
	
	ActionProcessor_start_1("ActionProcessor.start.1"),
	ActionProcessor_start_2("ActionProcessor.start.2"),
	ActionProcessor_start_3("ActionProcessor.start.3"),
	ActionProcessor_start_4("ActionProcessor.start.4"),
	ActionProcessor_start_5("ActionProcessor.start.5"),
	ActionProcessor_start_6("ActionProcessor.start.6"),
	ActionProcessor_start_7("ActionProcessor.start.7"),
	ActionProcessor_start_8("ActionProcessor.start.8"),
	
	
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
