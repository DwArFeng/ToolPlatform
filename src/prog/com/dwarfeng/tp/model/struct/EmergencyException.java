package com.dwarfeng.tp.model.struct;

/**
 * 紧急异常。
 * <p> 可以通过这个异常生成一个紧急窗口。
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
