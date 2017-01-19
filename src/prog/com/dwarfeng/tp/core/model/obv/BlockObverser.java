package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * �赲�۲�����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface BlockObverser extends Obverser {

	/**
	 * ֪ͨ��ڱ���ӡ�
	 * @param key ָ���ļ���
	 * @param value ָ����ֵ��
	 */
	public void fireEntryAdded(String key, String value);
	
	/**
	 * ֪ͨ��ڱ��Ƴ���
	 * @param key ָ���ļ���
	 */
	public void fireEntryRemoved(String key);
	
	/**
	 * ֪ͨ��ڱ����ġ�
	 * @param key ָ���ļ���
	 * @param oldValue �ɵ�ֵ��
	 * @param newValue �µ�ֵ��
	 */
	public void fireEntryChanged(String key, String oldValue, String newValue);
	
	/**
	 * ֪ͨģ�ͱ������
	 */
	public void fireCleared();
	
	/**
	 * ֪ͨģ�ͱ����¡�
	 */
	public void fireUpdated();
	
}
