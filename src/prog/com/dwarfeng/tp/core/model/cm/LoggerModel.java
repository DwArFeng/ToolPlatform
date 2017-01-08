package com.dwarfeng.tp.core.model.cm;

import java.util.Set;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.obv.LoggerObverser;
import com.dwarfeng.tp.core.model.struct.ReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.struct.Updateable;

/**
 * �йؼ�¼��������ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerModel extends ObverserSet<LoggerObverser>, ReadWriteThreadSafe, Updateable{
	
	/**
	 * ��ȡ��¼�������ġ�
	 * @return ��¼�������ġ�
	 */
	public LoggerContext getLoggerContext();
	
	/**
	 * ���ü�¼��������Ϊָ�������ġ�
	 * @param loggerContext ��¼�������ġ�
	 * @return �ò����Ƿ��ģ������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean setLoggerContext(LoggerContext loggerContext);
	
	/**
	 * ��ȡģ���еļ�¼�����Ƽ��ϡ�
	 * <p> ��õļ�����ֻ���ģ����Ե������еı༭�������׳� {@link UnsupportedOperationException}��
	 * @return ��¼�����Ƽ��ϡ�
	 */
	public Set<String> getLoggerNames();
	
	/**
	 * ����ģ���еļ�¼�������Ƽ��ϡ�
	 * @param loggerNames ָ�������Ƽ��ϡ�
	 * @return �ò����Ƿ��ģ������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean setLoggerNames(Set<String> loggerNames);
	
	/**
	 * ��ȡģ���е�Logger���ϡ�
	 * <p>�ü�����ֻ���ģ����Ե�����༭�������׳� {@link UnsupportedOperationException}��
	 * @return ��ģ���е�Logger���ϡ�
	 */
	public Set<Logger> getLoggers();
		
}
