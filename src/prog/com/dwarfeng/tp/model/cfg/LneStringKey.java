package com.dwarfeng.tp.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * Logger与紧急窗口的文本域。
 * @author DwArFeng
 * @since 1.8
 */
public enum LneStringKey  implements Name{
	
	
	
	;

	private final String name;
	
	private LneStringKey(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

}
