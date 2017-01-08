package com.dwarfeng.tp.core.model.struct;

/**
 * Ĭ�Ϲ��̽��
 * <p> ���̽����Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 1.8
 */
public final class DefaultProcessResult implements ProcessResult{
	
	private final String message;
	private final Throwable throwable;
	
	/**
	 * ��ʵ��
	 */
	public DefaultProcessResult() {
		this(null, null);
	}
	
	/**
	 * @param message ָ������Ϣ��
	 */
	public DefaultProcessResult(String message) {
		this(message, null);
	}

	/**
	 * ��ʵ����
	 * @param message ָ������Ϣ��
	 * @param throwable ָ���Ŀ��׳�����
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
