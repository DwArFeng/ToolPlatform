package com.dwarfeng.tp.core.model.struct;

/**
 * 过程异常。
 * <p> 该异常用于指示程序在处理过程中发生异常。
 * @author  DwArFeng
 * @since 1.8
 */
public class ProcessException extends Exception{

	private static final long serialVersionUID = 3395463093993235210L;

	public ProcessException() {
		// TODO Auto-generated constructor stub
	}

	public ProcessException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ProcessException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ProcessException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
