package com.dwarfeng.tp.core.model.struct;

/**
 * 初始化失败异常。
 * <p> 可以通过这个异常生成一个紧急窗口。
 * @author  DwArFeng
 * @since 1.8
 */
public final class InitializeFailedException extends Exception{
	
	private final String dialogTitle;
	private final String dialogMessage;
	
	/**
	 * 生成一个默认的紧急异常。
	 */
	public InitializeFailedException() {
		this(null, null, null, null);
	}
	
	/**
	 * 生成新的紧急异常。
	 * @param message 异常信息。
	 */
	public InitializeFailedException(String message){
		this(null, null, message, null);
	}
	
	/**
	 * 生成新的紧急异常。
	 * @param message 异常信息。
	 * @param cause 异常的发生原因。
	 */
	public InitializeFailedException(String message, Throwable cause){
		this(null, null, message, cause);
	}
	
	/**
	 * 生成新的紧急异常。
	 * @param dialogTitle 紧急窗口的标题。
	 * @param dialogMessage 紧急窗口的内容。
	 * @param exitCode 紧急窗口的退出代码。
	 * @param message 紧急异常的信息。
	 * @param cause 异常的发生原因。
	 */
	public InitializeFailedException(String dialogTitle, String dialogMessage, String message, Throwable cause){
		super(message, cause);
		this.dialogTitle = dialogTitle;
		this.dialogMessage = dialogMessage;
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
	
	
}
