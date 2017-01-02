package com.dwarfeng.tp.core.model.struct;

import com.dwarfeng.tp.core.model.cm.LoggerModel;

/**
 * ��¼���ṩ����
 * <p> ���ڴ�ָ���ļ�¼��ģ�����ṩ��¼����
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerProvider {

	/**
	 * ��ȡ�ṩ���еļ�¼��ģ�͡�
	 * @return �ṩ���еļ�¼��ģ�͡�
	 */
	public LoggerModel getLoggerModel();
	
	/**
	 * ��ȡ��¼���ṩ�����ṩ�ļ�¼����
	 * @return �ü�¼���ṩ���ṩ�ļ�¼����
	 */
	public Logger getLogger();
	
	/**
	 * ���ݼ�¼��ģ���е�������������һ����¼����
	 * @throws ProcessException �����쳣��
	 */
	public void update() throws ProcessException;
	
	/**
	 * ����¼������ΪĬ�ϵļ�¼����
	 */
	public void update2Default();
}
