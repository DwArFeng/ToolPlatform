package com.dwarfeng.tp.core.model.struct;

/**
 * �������
 * <p> ���ָ����ֵ�Ƿ�Ϸ���
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface Checker<T> {

	/**
	 * �ж�һ�������Ƿ���Ч��
	 * @param obj ָ���Ķ���
	 * @return �ö����Ƿ���Ч��
	 */
	public boolean isValid(T obj);
	
	/**
	 * �ж�һ�������Ƿ���Ч��
	 * @param obj ָ���Ķ���
	 * @return �ö����Ƿ���Ч��
	 */
	public default boolean nonValid(T obj){
		return ! isValid(obj);
	}
	
}
