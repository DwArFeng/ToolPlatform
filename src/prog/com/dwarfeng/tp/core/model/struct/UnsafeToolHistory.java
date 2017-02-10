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
	 * ��ȡ���ߵ�����ʱ�䡣
	 * @return ���ߵ�����ʱ�䡣
	 * @throws ProcessException �����쳣��
	 */
	public Date getRanTime() throws ProcessException;
	
	/**
	 * ��ȡ���ߵĽ���ʱ�䡣
	 * @return ���ߵĽ���ʱ�䡣
	 * @throws ProcessException �����쳣��
	 */
	public Date getExitedTime() throws ProcessException;
	
}
