package com.dwarfeng.tp.core.model.struct;

/**
 * ��¼���ṩ����
 * <p> ���ڴ�ָ���ļ�¼��ģ�����ṩ��¼����
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerProvider {
	
	/**
	 * ��ȡ��¼���ṩ�����ṩ�ļ�¼����
	 * @return �ü�¼���ṩ���ṩ�ļ�¼����
	 */
	public Logger getLogger();
	
}
