package com.dwarfeng.tp.core.model.struct;

/**
 * �����쳣��
 * <p> ����ͨ������쳣����һ���������ڡ�
 * @author  DwArFeng
 * @since 1.8
 */
public final class EmergencyException extends Exception{
	
	private static final long serialVersionUID = -4598335355602228579L;
	
	private final String dialogTitle;
	private final String dialogMessage;
	private final int exitCode;
	
	/**
	 * ����һ��Ĭ�ϵĽ����쳣��
	 */
	public EmergencyException() {
		this(null, null, 100, null);
	}
	
	/**
	 * �����µĽ����쳣��
	 * @param message �쳣��Ϣ��
	 */
	public EmergencyException(String message){
		this(null, null, 100, message);
	}
	
	/**
	 * �����µĽ����쳣��
	 * @param dialogTitle �������ڵı��⡣
	 * @param dialogMessage �������ڵ����ݡ�
	 * @param exitCode �������ڵ��˳����롣
	 * @param message �����쳣����Ϣ��
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
