package com.dwarfeng.tp.core.model.struct;

/**
 * ���̽����
 * <p> ���̽����һ��������ִ����Ϻ󷵻صĽ����Ϣ��
 * @author DwArFeng
 * @since 1.8
 */
public interface ProcessResult {
	
	/**
	 * ���ش������е���Ϣ��
	 * @return �������е���Ϣ��
	 */
	public String getMessage();
	
	/**
	 * ���ش������еĿ��׳�����
	 * <p> ����������������쳣����÷���ͨ��Ӧ�÷��ظ��쳣���������û�з����쳣����Ӧ�÷��� <code>null</code>��
	 * @return �������еĿ��׳�����
	 */
	public Throwable getThrowable();

}
