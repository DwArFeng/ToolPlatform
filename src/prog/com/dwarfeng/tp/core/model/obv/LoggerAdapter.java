package com.dwarfeng.tp.core.model.obv;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.tp.core.model.struct.LoggerInfo;

/**
 * ��¼��ģ�͹۲�����������
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class LoggerAdapter implements LoggerObverser{

	@Override
	public void fireLoggerContextChanged(LoggerContext oldOne, LoggerContext newOne) {}
	@Override
	public void fireLoggerInfoAdded(LoggerInfo loggerInfo) {}
	@Override
	public void fireLoggerInfoRemoved(LoggerInfo loggerInfo) {}
	@Override
	public void fireLoggerInfoCleared() {}
	@Override
	public void fireUpdated() {}

}
