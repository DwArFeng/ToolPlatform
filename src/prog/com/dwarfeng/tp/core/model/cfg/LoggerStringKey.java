package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.struct.DefaultName;

/**
 * 记录器文本键。
 * <p> 该键枚举用于实现记录器的多语言。
 * @author  DwArFeng
 * @since 1.8
 */
public enum LoggerStringKey implements Name{
	
	ActionProcessor_start_1(new DefaultName("ActionProcessor.start.1")),
	ActionProcessor_start_2(new DefaultName("ActionProcessor.start.2")),
	ActionProcessor_start_3(new DefaultName("ActionProcessor.start.3")),
	ActionProcessor_start_4(new DefaultName("ActionProcessor.start.4")),
	ActionProcessor_start_5(new DefaultName("ActionProcessor.start.5")),
	ActionProcessor_start_6(new DefaultName("ActionProcessor.start.6")),
	ActionProcessor_start_7(new DefaultName("ActionProcessor.start.7")),
	ActionProcessor_start_8(new DefaultName("ActionProcessor.start.8")),
	ActionProcessor_start_9(new DefaultName("ActionProcessor.start.9")),
	ActionProcessor_start_10(new DefaultName("ActionProcessor.start.10")),
	ActionProcessor_start_11(new DefaultName("ActionProcessor.start.11")),
	ActionProcessor_start_12(new DefaultName("ActionProcessor.start.12")),
	ActionProcessor_start_13(new DefaultName("ActionProcessor.start.13")),
	ActionProcessor_start_14(new DefaultName("ActionProcessor.start.14")),

	
	;

	private Name name;
	
	private LoggerStringKey(Name name) {
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return name.getName();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return name.getName();
	}

}
