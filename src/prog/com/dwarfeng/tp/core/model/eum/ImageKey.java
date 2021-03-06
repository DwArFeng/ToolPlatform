package com.dwarfeng.tp.core.model.eum;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.struct.DefaultName;

public enum ImageKey implements Name {
	
	IMG_LOAD_FAILED(new DefaultName("/com/dwarfeng/tp/resource/image/img_load_failed.png")),
	MAINFRAME_ICON(new DefaultName("/com/dwarfeng/tp/resource/image/icon.png")),
	CONSOLE(new DefaultName("/com/dwarfeng/tp/resource/image/console.png")),
	SELECT_ALL(new DefaultName("/com/dwarfeng/tp/resource/image/select_all.png")),
	CLEAR_SCREEN(new DefaultName("/com/dwarfeng/tp/resource/image/clear_screen.png")),
	LINEWRAP(new DefaultName("/com/dwarfeng/tp/resource/image/line_wrap.png")),
	CANCELED(new DefaultName("/com/dwarfeng/tp/resource/image/canceled.png")),
	PROCESSING(new DefaultName("/com/dwarfeng/tp/resource/image/processing.png")),
	DONE(new DefaultName("/com/dwarfeng/tp/resource/image/done.png")),
	PROGRESS(new DefaultName("/com/dwarfeng/tp/resource/image/progress.png")),
	LIBRARY(new DefaultName("/com/dwarfeng/tp/resource/image/library.png")),
	TOOL(new DefaultName("/com/dwarfeng/tp/resource/image/tool.png")),
	RUNTIME(new DefaultName("/com/dwarfeng/tp/resource/image/runtime.png")),
	LIBRARY_ICON(new DefaultName("/com/dwarfeng/tp/resource/image/library_icon.png")),
	RUNNING(new DefaultName("/com/dwarfeng/tp/resource/image/running.png")),
	NOT_START(new DefaultName("/com/dwarfeng/tp/resource/image/not_start.png")),
	EXITED(new DefaultName("/com/dwarfeng/tp/resource/image/exited.png")),
	HISTORY(new DefaultName("/com/dwarfeng/tp/resource/image/history.png")),
	UNKNOWN(new DefaultName("/com/dwarfeng/tp/resource/image/unknown.png")),
	REMOVE(new DefaultName("/com/dwarfeng/tp/resource/image/remove.png")),
	CLEAR(new DefaultName("/com/dwarfeng/tp/resource/image/clear.png")),

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
