package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;

/**
 * Ĭ�϶�������Ϣ��
 * <p> ��������Ϣ��Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultMutilangInfo implements MutilangInfo {
	
	private final String label;
	private final String filePath;
	
	/**
	 * ��ʵ����
	 * @param label ָ���ı�ǩ��
	 * @param filePath ָ�����ļ���·����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultMutilangInfo(String label, String filePath) {
		Objects.requireNonNull(label, "��ڲ��� label ����Ϊ null��");
		Objects.requireNonNull(filePath, "��ڲ��� filePath ����Ϊ null��");
		
		this.label = label;
		this.filePath = filePath;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangInfo#getLabel()
	 */
	@Override
	public String getLabel() {
		return this.label;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangInfo#getFile()
	 */
	@Override
	public String getFile() {
		return this.filePath;
	}

}
