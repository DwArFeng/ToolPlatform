package com.dwarfeng.tp.plaf.core;

/**
 * ��ʾͼ���С��ö�١�
 * @author DwArFeng
 * @since 1.8
 */
public enum ToolIconSize{
	
	/**С�ߴ�*/
	SMALL(16),
	
	/**�еȳߴ�*/
	MEDIUM(32),
	
	/**��ߴ�*/
	BIG(64),
	;
	
	private final int size;
	
	private ToolIconSize(int size) {
		this.size = size;
	}
	
	/**
	 * ����ָ��ö����ͼ��Ĵ�С��
	 * @return ͼ��Ĵ�С��
	 */
	public int getSize(){
		return this.size;
	}

}
