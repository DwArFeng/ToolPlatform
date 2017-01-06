package com.dwarfeng.tp.core.model.cm;

import java.util.Set;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.obv.LoggerObverser;
import com.dwarfeng.tp.core.model.struct.ReadWriteThreadSafe;

/**
 * �йؼ�¼��������ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerModel extends Set<Logger>, ObverserSet<LoggerObverser>, ReadWriteThreadSafe{

	/**
	 * ��ȡ��¼��ģ�����ڼ�¼�ļ�¼�������ġ�
	 * <p> �ü��ϰ���ģ�������м�¼���ڼ�¼�ļ�¼�������ģ��п���һ����¼�������Ķ�Ӧ�Ŷ����¼����
	 * <p> �ü����ǲ��ɸ��ĵģ���ͼ�������еı༭�������׳� {@link UnsupportedOperationException}��
	 * <p> �ü����еļ�¼��ֻ����Ϊ�鿴ʹ�ã����ɶ����еļ�¼�������޸ķ���������ᵼ��ģ���Բ���Ԥ�ϵķ�ʽ������
	 * @return ģ�������м�¼���ڼ�¼�������ġ�
	 */
	public Set<LoggerContext> getLoggerContexts();
	
	/**
	 * ֹͣ��¼�������ģ����Ƴ�ģ��������������йص����м�¼����
	 * <p> �÷�����ر�ָ���ļ�¼�������۸ü�¼���Ƿ���ģ���С�
	 * �����¼������ģ���У��򷵻� <code>false</code>�����򷵻� <code>true</code>��
	 * @param context ָ���ļ�¼�������ġ�
	 * @return �ò����Ƿ��ģ������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean stopLoggerContext(LoggerContext context);
	
	/**
	 * ֹͣģ�ͼ�¼���е�ȫ����¼�������ģ�ͬʱ��ռ�¼��ģ���е����м�¼����
	 */
	public void stopAllLoggerContexts();
	
}
