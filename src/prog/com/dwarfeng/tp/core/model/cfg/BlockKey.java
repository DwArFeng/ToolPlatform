package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.struct.DefaultName;

/**
 * ×èµ²¼ü¡£
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public enum BlockKey implements Name{
	
	INITIALIZE(new DefaultName("initialize")),
	LOAD_LIB(new DefaultName("load_lib")),
	CHECK_LIB(new DefaultName("check_lib")),
	LOAD_TOOLINFO(new DefaultName("load_toolinfo")),
	RUN_TOOL(new DefaultName("run_tool")),
	CLOSING(new DefaultName("closing")),
	LOG_RUNNINGTOOL(new DefaultName("log_runningtool")),
	;

	private Name name;
	
	private BlockKey(Name name) {
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
