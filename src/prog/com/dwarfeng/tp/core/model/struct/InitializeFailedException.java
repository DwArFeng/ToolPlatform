package com.dwarfeng.tp.core.model.struct;

/**
 * ��ʼ��ʧ���쳣��
 * <p> ����ͨ������쳣����һ���������ڡ�
 * @author  DwArFeng
 * @since 1.8
 */
public final class InitializeFailedException extends Exception{
	
	private final String dialogTitle;
	private final String dialogMessage;
	
	/**
	 * ����һ��Ĭ�ϵĽ����쳣��
	 */
	public InitializeFailedException() {
		this(null, null, null, null);
	}
	
	/**
	 * �����µĽ����쳣��
	 * @param message �쳣��Ϣ��
	 */
	public InitializeFailedException(String message){
		this(null, null, message, null);
	}
	
	/**
	 * �����µĽ����쳣��
	 * @param message �쳣��Ϣ��
	 * @param cause �쳣�ķ���ԭ��
	 */
	public InitializeFailedException(String message, Throwable cause){
		this(null, null, message, cause);
	}
	
	/**
	 * �����µĽ����쳣��
	 * @param dialogTitle �������ڵı��⡣
	 * @param dialogMessage �������ڵ����ݡ�
	 * @param exitCode �������ڵ��˳����롣
	 * @param message �����쳣����Ϣ��
	 * @param cause �쳣�ķ���ԭ��
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
