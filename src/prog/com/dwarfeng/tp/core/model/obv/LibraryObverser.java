package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.Library;

/**
 * ��۲�����
 * @author DwArFeng
 * @since 1.8
 */
public interface LibraryObverser extends Obverser {

	/**
	 * ֪ͨ��ڱ���ӡ�
	 * @param key ָ���ļ���
	 * @param value ָ����ֵ��
	 */
	public void fireEntryAdded(String key, Library value);
	
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
	public void fireEntryChanged(String key, Library oldValue, Library newValue);
	
	/**
	 * ֪ͨģ�ͱ������
	 */
	public void fireCleared();
	
}
