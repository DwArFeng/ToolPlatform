package com.dwarfeng.tp.core.model.struct;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.cm.ToolRuntimeModel;

/**
 * �������߳�ȡ������
 * <p> �����ڹ�������ʱģ����ȡ�������������й��ߣ����Ҽ�¼��ָ���� Logger ֮�С�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface ExitedRunningToolTaker extends ExternalReadWriteThreadSafe, MutilangSupported{

	/**
	 * ��ȡ��������ȡ�������еļ�¼����
	 * @return ��ɹ���ȡ�������еļ�¼����
	 */
	public Logger getLogger();
	
	/**
	 * ���ý�������ȡ�����еļ�¼����
	 * @param logger ָ���ļ�¼����
	 * @return �ò����Ƿ�Ըü�¼������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean setLogger(Logger logger);
	
	/**
	 * ��ȡ��������ȡ�����Ƿ�����ͣ״̬��
	 * @return �Ƿ�����ͣ״̬��
	 */
	public boolean isPause();
	
	/**
	 * ���øý�������ȡ��������ͣ״̬��
	 * @param aFlag �Ƿ���ͣ��
	 * @return �ò����Ƿ�Ը�ȡ��������˸ı䡣
	 */
	public boolean setPause(boolean aFlag);
	
	/**
	 * ��ȡ�ù���ȡ�����еĹ�������ʱģ�͡�
	 * @return �ù���ȡ�����еĹ�������ʱģ�͡�
	 */
	public ToolRuntimeModel getToolRuntimeModel();
	
	/**
	 * �رս�������ȡ������
	 * <p> ���ô˷�����ȡ������ֹͣ��ָ���Ĺ�������ʱģ����ȡ����ɵĹ��̡�
	 */
	public void shutdown();
	
}
