package com.dwarfeng.tp.model.io;

/**
 * ����ƽ̨�ü�¼����
 * <p> �����������¼�йصķ�����
 * @author  DwArFeng
 * @since 1.8
 */
public interface ToolPlatformLogger {

	/**
	 * ���ü�¼վ���trace������
	 * @param message ָ������Ϣ��
	 */
	public void trace(String message);
	
	/**
	 * ���ü�¼վ���debug������
	 * @param message ָ������Ϣ��
	 */
	public void debug(String message);
	
	/**
	 * ���ü�¼վ���info������
	 * @param message ָ������Ϣ��
	 */
	public void info(String message);
	
	/**
	 * ���ü�¼վ���warn������
	 * @param message ָ������Ϣ��
	 */
	public void warn(String message);
	
	/**
	 * ���ü�¼վ���warn������
	 * @param message ָ������Ϣ��
	 * @param t ָ���Ŀ��׳�����һ�����̻߳��쳣�ĸ��ٶ�ջ��
	 */
	public void warn(String message, Throwable t);
	
	/**
	 * ���ü�¼վ���error������
	 * @param message ָ������Ϣ��
	 * @param t ָ���Ŀ��׳�����һ�����̻߳��쳣�ĸ��ٶ�ջ��
	 */
	public void error(String message, Throwable t);
	
	/**
	 * ���ü�¼վ���fatal������
	 * @param message ָ������Ϣ��
	 * @param t ָ���Ŀ��׳�����һ�����̻߳��쳣�ĸ��ٶ�ջ��
	 */
	public void fatal(String message, Throwable t);
	
}
