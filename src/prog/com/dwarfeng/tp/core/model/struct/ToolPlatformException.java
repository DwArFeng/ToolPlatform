package com.dwarfeng.tp.core.model.struct;

/**
 * 工具平台异常。
 * <p> 该异常是本程序中所有其他自定义异常的父类。
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
