package com.dwarfeng.tp.core.model.struct;

import java.util.Date;
import java.util.Objects;

/**
 * Ĭ�Ϲ�����ʷ��
 * <p> ������ʷ��Ĭ��ʵ�֡�
 * <p> �����̰߳�ȫ��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultToolHistory implements ToolHistory{
	
	private final String name;
	private final Date ranDate;
	private final Date exitedDate;
	private final int exitedCode;
	
	/**
	 * ��ʵ��
	 * @param name ָ�������ơ�
	 * @param ranDate ָ�����������ڡ�
	 * @param exitedDate ָ�����˳����ڡ�
	 * @param exitedCode �˳����롣
	 */
	public DefaultToolHistory(String name, Date ranDate, Date exitedDate, int exitedCode) {
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
		Objects.requireNonNull(ranDate, "��ڲ��� ranDate ����Ϊ null��");
		Objects.requireNonNull(exitedDate, "��ڲ��� exitedDate ����Ϊ null��");

		this.name = name;
		this.ranDate = ranDate;
		this.exitedDate = exitedDate;
		this.exitedCode = exitedCode;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getRanDate()
	 */
	@Override
	public Date getRanDate() {
		return ranDate;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getExitedDate()
	 */
	@Override
	public Date getExitedDate() {
		return exitedDate;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getExitedCode()
	 */
	@Override
	public int getExitedCode() {
		return exitedCode;
	}

}
