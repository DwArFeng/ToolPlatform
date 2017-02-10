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
	private final Date ranTime;
	private final Date exitedDate;
	
	/**
	 * ��ʵ��
	 * @param name ָ�������ơ�
	 * @param ranTime ָ��������ʱ�䡣
	 * @param exitedDate ָ�����˳�ʱ�䡣
	 */
	public DefaultToolHistory(String name, Date ranTime, Date exitedDate) {
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
		Objects.requireNonNull(ranTime, "��ڲ��� ranTime ����Ϊ null��");
		Objects.requireNonNull(exitedDate, "��ڲ��� exitedDate ����Ϊ null��");

		this.name = name;
		this.ranTime = ranTime;
		this.exitedDate = exitedDate;
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
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getRanTime()
	 */
	@Override
	public Date getRanTime() {
		return ranTime;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getExitedTime()
	 */
	@Override
	public Date getExitedTime() {
		return exitedDate;
	}

}
