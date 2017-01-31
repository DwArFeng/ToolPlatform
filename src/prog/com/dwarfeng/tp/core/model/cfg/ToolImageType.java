package com.dwarfeng.tp.core.model.cfg;

/**
 * 工具的图片类型。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public enum ToolImageType {

	/**小图标*/
	ICON_SMALL(32,32),
	
	/**中等图标*/
	ICON_MEDIUM(64,64),
	
	/**大图标*/
	ICON_LARGE(128,128),
	
	;
	
	private final int height;
	private final int width;
	
	private ToolImageType(int height, int width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * 图片的高度。
	 * @return 图片的高度。
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 图片的宽度。
	 * @return 图片的宽度。
	 */
	public int getWidth() {
		return width;
	}
}
