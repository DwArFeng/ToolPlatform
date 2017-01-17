package com.dwarfeng.tp.core.model.struct;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Ĭ�϶�������Ϣ��
 * <p> ��������Ϣ��Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultMutilangInfo implements MutilangInfo {
	
	private final String label;
	private final Map<String, String> mutilangMap;
	
	/**
	 * ��ʵ����
	 * @param label ָ���ı�ǩ��
	 * @param filePath ָ�����ļ���·����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultMutilangInfo(String label, Map<String, String> mutilangMap) {
		Objects.requireNonNull(label, "��ڲ��� label ����Ϊ null��");
		Objects.requireNonNull(mutilangMap, "��ڲ��� mutilangMap ����Ϊ null��");
		
		this.label = label;
		this.mutilangMap = mutilangMap;
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
	 * @see com.dwarfeng.tp.core.model.struct.MutilangAttribute#getMutilangMap()
	 */
	@Override
	public Map<String, String> getMutilangMap() {
		return Collections.unmodifiableMap(mutilangMap);
	}

}
