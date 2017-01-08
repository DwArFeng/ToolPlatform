package com.dwarfeng.tp.core.model.obv;

import java.util.Set;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * ��¼���۲�����
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerObverser extends Obverser{
	
	/**
	 * ֪ͨģ���еļ�¼�������ķ����˸ı䡣
	 * @param oldOne �ɵļ�¼�������ġ�
	 * @param newOne  �µļ�¼�������ġ�
	 */
	public void fireLoggerContextChanged(LoggerContext oldOne, LoggerContext newOne);
	
	/**
	 * ֪ͨģ���еļ�¼�����Ƽ��Ϸ����˸ı䡣
	 * @param oldOne �ɵ����Ƽ��ϡ�
	 * @param newOne �µ����Ƽ��ϡ�
	 */
	public void fireLoggerNamesChanged(Set<String> oldOne, Set<String> newOne);
	
	/**
	 * ֪ͨģ�͸��¡�
	 */
	public void fireUpdated();
}
