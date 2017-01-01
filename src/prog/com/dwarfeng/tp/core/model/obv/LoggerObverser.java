package com.dwarfeng.tp.core.model.obv;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * ��¼���۲�����
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerObverser extends Obverser{
	
	/**
	 * ֪ͨ��¼�����������ӡ�
	 * @param name ���ӵ������ơ�
	 */
	public void fireLoggerNameAdded(String name);
	
	/**
	 * ֪ͨ��¼�������Ʊ��Ƴ���
	 * @param name �Ƴ������ơ�
	 */
	public void fireLoggerNameRemoved(String name);
	
	/**
	 * ֪ͨ��¼�������Ʊ���ա�
	 */
	public void fireLoggerNameCleared();
	
	/**
	 * ֪ͨ��¼���������ķ����˸ı䡣
	 * @param oldOne �ɵļ�¼�������ġ�
	 * @param newOne �µļ�¼�������ġ�
	 */
	public void fireLoggerContextChanged(LoggerContext oldOne, LoggerContext newOne);

}
