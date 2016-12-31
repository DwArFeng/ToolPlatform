package com.dwarfeng.tp.core.model.struct;

/**
 * 紧急异常。
 * <p> 可以通过这个异常生成一个紧急窗口。
 * @author  DwArFeng
 * @since 1.8
 */
public final class EmergencyException extends Exception{
	
	private static final long serialVersionUID = -4598335355602228579L;
	
	private final String dialogTitle;
	private final String dialogMessage;
	private final int exitCode;
	
	/**
	 * 生成一个默认的紧急异常。
	 */
	public EmergencyException() {
		this(null, null, 100, null);
	}
	
	/**
	 * 生成新的紧急异常。
	 * @param message 异常信息。
	 */
	public EmergencyException(String message){
		this(null, null, 100, message);
	}
	
	/**
	 * 生成新的紧急异常。
	 * @param dialogTitle 紧急窗口的标题。
	 * @param dialogMessage 紧急窗口的内容。
	 * @param exitCode 紧急窗口的退出代码。
	 * @param message 紧急异常的信息。
	 */
	public EmergencyException(String dialogTitle, String dialogMessage,int exitCode, String message){
		super(message);
		this.dialogTitle = dialogTitle;
		this.dialogMessage = dialogMessage;
		this.exitCode = exitCode;
	}

	/**
	 * @return the dialogTitle
	 */
	public String getDialogTitle() {
		return dialogTitle;
	}

	/**
	 * @return the dialogMessage
	 */
	public String getDialogMessage() {
		return dialogMessage;
	}

	/**
	 * @return the exitCode
	 */
	public int getExitCode() {
		return exitCode;
	}

	
	
}
