package com.dwarfeng.tp.core.model.struct;

import java.io.File;
import java.util.Objects;

/**
 * Ĭ�϶�������Ϣ��
 * <p> ��������Ϣ��Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultMutilangInfo implements MutilangInfo {
	
	private final String label;
	private final File file;
	
	/**
	 * ��ʵ����
	 * @param label ָ���ı�ǩ��
	 * @param file ָ�����ļ���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultMutilangInfo(String label, File file) {
		Objects.requireNonNull(label, "��ڲ��� label ����Ϊ null��");
		Objects.requireNonNull(file, "��ڲ��� file ����Ϊ null��");
		
		this.label = label;
		this.file = file;
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
	public File getFile() {
		return this.file;
	}

}
