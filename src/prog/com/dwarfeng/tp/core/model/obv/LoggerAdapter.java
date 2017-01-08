package com.dwarfeng.tp.core.model.obv;

import java.util.Set;

import org.apache.logging.log4j.core.LoggerContext;

/**
 * ��¼��ģ�͹۲�����������
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class LoggerAdapter implements LoggerObverser{

	@Override
	public void fireLoggerContextChanged(LoggerContext oldOne, LoggerContext newOne) {}
	@Override
	public void fireLoggerNamesChanged(Set<String> oldOne, Set<String> newOne) {}
	@Override
	public void fireUpdated() {}

}
