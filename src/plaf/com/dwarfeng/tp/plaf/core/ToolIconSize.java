package com.dwarfeng.tp.plaf.core;

/**
 * 表示图标大小的枚举。
 * @author DwArFeng
 * @since 1.8
 */
public enum ToolIconSize{
	
	/**小尺寸*/
	SMALL(16),
	
	/**中等尺寸*/
	MEDIUM(32),
	
	/**大尺寸*/
	BIG(64),
	;
	
	private final int size;
	
	private ToolIconSize(int size) {
		this.size = size;
	}
	
	/**
	 * 返回指定枚举下图标的大小。
	 * @return 图标的大小。
	 */
	public int getSize(){
		return this.size;
	}

}
