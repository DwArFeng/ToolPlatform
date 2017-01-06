package com.dwarfeng.tp.core.model.obv;

import org.apache.logging.log4j.Logger;
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
	public void fireLoggerAdded(Logger logger);
	
	/**
	 * ֪ͨ��¼�������Ʊ��Ƴ���
	 * @param name �Ƴ������ơ�
	 */
	public void fireLoggerRemoved(Logger logger);
	
	/**
	 * ֪ͨ��¼���������
	 */
	public void fireLoggerCleared();

}
