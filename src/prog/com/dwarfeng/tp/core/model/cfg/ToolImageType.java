package com.dwarfeng.tp.core.model.cfg;

/**
 * ���ߵ�ͼƬ���͡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public enum ToolImageType {

	/**Сͼ��*/
	ICON_SMALL(32,32),
	
	/**�е�ͼ��*/
	ICON_MEDIUM(64,64),
	
	/**��ͼ��*/
	ICON_LARGE(128,128),
	
	;
	
	private final int height;
	private final int width;
	
	private ToolImageType(int height, int width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * ͼƬ�ĸ߶ȡ�
	 * @return ͼƬ�ĸ߶ȡ�
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * ͼƬ�Ŀ�ȡ�
	 * @return ͼƬ�Ŀ�ȡ�
	 */
	public int getWidth() {
		return width;
	}
}
