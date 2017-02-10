package com.dwarfeng.tp.core.model.struct;

import java.util.Date;
import java.util.Objects;

/**
 * Ĭ�ϲ���ȫ������ʷ��
 * <p> ����ȫ������ʷ��Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultUnsafeToolHistory implements UnsafeToolHistory {
	
	private final String name;
	private final String ranTimeStr;
	private final String exitedTimeStr;
	
	/**
	 * ��ʵ����
	 * @param name ָ�������ơ�
	 * @param ranTimeStr ָ��������ʱ���ַ�����
	 * @param exitedTimeStr ָ�����˳�ʱ���ַ�����
	 */
	public DefaultUnsafeToolHistory(String name, String ranTimeStr, String exitedTimeStr) {
		this.name = name;
		this.ranTimeStr = ranTimeStr;
		this.exitedTimeStr = exitedTimeStr;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolHistory#getName()
	 */
	@Override
	public String getName() throws ProcessException {
		try{
			if(Objects.isNull(name)) throw new NullPointerException();
			return name;
		}catch (Exception e) {
			throw new ProcessException("������Ϣ-��ȡ��������ʧ��", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolHistory#getRanTime()
	 */
	@Override
	public Date getRanTime() throws ProcessException {
		try{
			if(Objects.isNull(ranTimeStr)) throw new NullPointerException();
			long l = Long.parseLong(ranTimeStr);
			return new Date(l);
		}catch (Exception e) {
			throw new ProcessException("������Ϣ-��ȡ��ʼ��������ʧ��", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolHistory#getExitedTime()
	 */
	@Override
	public Date getExitedTime() throws ProcessException {
		try{
			if(Objects.isNull(ranTimeStr)) throw new NullPointerException();
			long l = Long.parseLong(exitedTimeStr);
			return new Date(l);
		}catch (Exception e) {
			throw new ProcessException("������Ϣ-��ȡ������������ʧ��", e);
		}
	}

}
