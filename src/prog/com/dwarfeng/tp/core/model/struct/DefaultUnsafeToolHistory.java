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
	private final String ranDateStr;
	private final String exitedDateStr;
	private final String exitedCodeStr;
	
	/**
	 * ��ʵ����
	 * @param name ָ�������ơ�
	 * @param ranDateStr ָ�������������ַ�����
	 * @param exitedDateStr ָ�����˳������ַ�����
	 * @param exitedCodeStr ָ�����˳������ַ�����
	 */
	public DefaultUnsafeToolHistory(String name, String ranDateStr, String exitedDateStr, String exitedCodeStr) {
		this.name = name;
		this.ranDateStr = ranDateStr;
		this.exitedDateStr = exitedDateStr;
		this.exitedCodeStr = exitedCodeStr;
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
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolHistory#getRanDate()
	 */
	@Override
	public Date getRanDate() throws ProcessException {
		try{
			if(Objects.isNull(ranDateStr)) throw new NullPointerException();
			long l = Long.parseLong(ranDateStr);
			return new Date(l);
		}catch (Exception e) {
			throw new ProcessException("������Ϣ-��ȡ��ʼ��������ʧ��", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolHistory#getExitedDate()
	 */
	@Override
	public Date getExitedDate() throws ProcessException {
		try{
			if(Objects.isNull(exitedDateStr)) throw new NullPointerException();
			long l = Long.parseLong(exitedDateStr);
			return new Date(l);
		}catch (Exception e) {
			throw new ProcessException("������Ϣ-��ȡ������������ʧ��", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolHistory#getExitedCode()
	 */
	@Override
	public int getExitedCode() throws ProcessException {
		try{
			if(Objects.isNull(exitedCodeStr)) throw new NullPointerException();
			int exitCode = Integer.parseInt(exitedCodeStr);
			return exitCode;
		}catch (Exception e) {
			throw new ProcessException("������Ϣ-��ȡ������������ʧ��", e);
		}
	}

}
