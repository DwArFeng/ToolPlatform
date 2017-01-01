package com.dwarfeng.tp.core.model.struct;

/**
 * ����ƽ̨�쳣��
 * <p> ���쳣�Ǳ����������������Զ����쳣�ĸ��ࡣ
 * @author  DwArFeng
 * @since 1.8
 */
public class ToolPlatformException extends Exception {

	public ToolPlatformException() {}

	public ToolPlatformException(String message) {
		super(message);
	}

	public ToolPlatformException(Throwable cause) {
		super(cause);
	}

	public ToolPlatformException(String message, Throwable cause) {
		super(message, cause);
	}

	public ToolPlatformException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
