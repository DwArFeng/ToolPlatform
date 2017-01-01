package com.dwarfeng.tp.core.model.vim;

import java.util.Set;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.obv.LoggerObverser;

/**
 * �йؼ�¼��������ģ�͡�
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerModel extends Set<String>, ObverserSet<LoggerObverser>{
	
	/**
	 * ��ȡ���ģ���йصļ�¼�������ġ�
	 * @return ���ģ���йصļ�¼�������ġ�
	 */
	public LoggerContext getLoggerContext();
	
	/**
	 * �������ģ���йصļ�¼�������ġ�
	 * @param loggerContext ָ���ļ�¼�������ġ�
	 * @return �ò����Ƿ�Ը�ģ������˸ı䡣
	 */
	public boolean setLoggerContext(LoggerContext loggerContext);

}
