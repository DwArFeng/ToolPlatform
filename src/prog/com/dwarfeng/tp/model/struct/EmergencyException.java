package com.dwarfeng.tp.model.struct;

/**
 * �����쳣��
 * <p> ����ͨ������쳣����һ���������ڡ�
 * @author  DwArFeng
 * @since 1.8
 */
public final class EmergencyException extends Exception{
	
	private static final long serialVersionUID = -2789024088916305415L;
	
	private final String title;
	
	public EmergencyException() {
		this(null, null);
	}
	
	public EmergencyException(String title, String message){
		super(message);
		this.title = title;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
}
