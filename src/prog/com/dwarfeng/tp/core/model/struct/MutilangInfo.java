package com.dwarfeng.tp.core.model.struct;

import java.util.Map;

/**
 * �����Ե����ԡ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangInfo {
	
	/**
	 * ��ȡ�����еı�ǩ��
	 * @return ��Ϣ�еı�ǩ��
	 * @throws ProcessException �����쳣��
	 */
	public String getLabel() throws ProcessException;
	
	/**
	 * ��ȡ�����еļ�ֵӳ�䡣
	 * @return �����еļ�ֵӳ�䡣
	 * @throws ProcessException �����쳣��
	 */
	public Map<String, String> getMutilangMap() throws ProcessException;

}
