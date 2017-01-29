package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.struct.DefaultName;

public enum ImageKey implements Name {
	
	ImaLoadFailed(new DefaultName("/com/dwarfeng/tp/resource/image/img-load-failed.png")),
	MainFrame_Icon(new DefaultName("/com/dwarfeng/tp/resource/image/icon.png")),
	Console(new DefaultName("/com/dwarfeng/tp/resource/image/console.png")),
	SelectAll(new DefaultName("/com/dwarfeng/tp/resource/image/select-all.png")),
	ClearScreen(new DefaultName("/com/dwarfeng/tp/resource/image/clear-screen.png")),
	;

	private Name name;
	
	private ImageKey(Name name) {
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
