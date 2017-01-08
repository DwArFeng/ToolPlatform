package com.dwarfeng.tp.core.model.struct;

/**
 * 默认过程结果
 * <p> 过程结果的默认实现。
 * @author DwArFeng
 * @since 1.8
 */
public final class DefaultProcessResult implements ProcessResult{
	
	private final String message;
	private final Throwable throwable;
	
	/**
	 * 新实例
	 */
	public DefaultProcessResult() {
		this(null, null);
	}
	
	/**
	 * @param message 指定的消息。
	 */
	public DefaultProcessResult(String message) {
		this(message, null);
	}

	/**
	 * 新实例。
	 * @param message 指定的消息。
	 * @param throwable 指定的可抛出对象。
	 */
	public DefaultProcessResult(String message, Throwable throwable) {
		super();
		this.message = message;
		this.throwable = throwable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ProcessResult#getMessage()
	 */
	@Override
	public String getMessage() {
		return message == null ? "" : message;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ProcessResult#getThrowable()
	 */
	@Override
	public Throwable getThrowable() {
		return this.throwable;
	}

}
