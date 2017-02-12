package com.dwarfeng.tp.core.model.struct;

import java.util.Date;

/**
 * ����ȫ������ʷ��
 * <p> �ù�����ʷ���ع�����Ϣ�����ԣ������п����׳��쳣���ٶ�Ҳ�ȹ�����ʷҪ����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface UnsafeToolHistory {

	/**
	 * ��ȡ������ʷ�����ơ�
	 * @return ������ʷ�����ơ�
	 * @throws ProcessException �����쳣��
	 */
	public String getName() throws ProcessException;
	
	/**
	 * ��ȡ���ߵ��������ڡ�
	 * @return ���ߵ��������ڡ�
	 * @throws ProcessException �����쳣��
	 */
	public Date getRanDate() throws ProcessException;
	
	/**
	 * ��ȡ���ߵĽ������ڡ�
	 * @return ���ߵĽ������ڡ�
	 * @throws ProcessException �����쳣��
	 */
	public Date getExitedDate() throws ProcessException;
	
	/**
	 * ��ȡ���ߵ��˳����롣
	 * @return ���ߵ��˳����롣
	 * @throws ProcessException �����쳣��
	 */
	public int getExitedCode() throws ProcessException;
	
}
