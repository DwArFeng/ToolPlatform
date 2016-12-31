package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 记录器文本键。
 * <p> 该键枚举用于实现记录器的多语言。
 * @author  DwArFeng
 * @since 1.8
 */
public enum LoggerStringKey implements Name{
	
	ProgramAttributes_0("ProgramAttributes.0"),
	ProgramAttributes_1("ProgramAttributes.1"),
	ProgramAttributes_2("ProgramAttributes.2"),
	ProgramAttributes_3("ProgramAttributes.3"),
	ProgramAttributes_4("ProgramAttributes.4"),
	ProgramAttributes_5("ProgramAttributes.5"),
	ProgramAttributes_6("ProgramAttributes.6"),
	ProgramAttributes_7("ProgramAttributes.7"),
	ProgramAttributes_8("ProgramAttributes.8"),
	ProgramAttributes_9("ProgramAttributes.9"),
	ProgramAttributes_10("ProgramAttributes.10"),
	ProgramAttributes_11("ProgramAttributes.11"),
	ProgramAttributes_12("ProgramAttributes.12"),
	ProgramAttributes_13("ProgramAttributes.13"),
	ProgramAttributes_14("ProgramAttributes.14"),
	ProgramAttributes_15("ProgramAttributes.15"),
	ProgramAttributes_16("ProgramAttributes.16"),
	ProgramAttributes_17("ProgramAttributes.17"),
	ProgramAttributes_18("ProgramAttributes.18"),
	
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
