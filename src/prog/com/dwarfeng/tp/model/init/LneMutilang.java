package com.dwarfeng.tp.model.init;

import com.dwarfeng.tp.model.cfg.LneStringKey;

/**
 * һ����֧�ּ�¼�ͽ������ڵĶ����Խӿڡ�
 * <p> �ýӿ��������ù�ϵ����֮ǰ��ʱ���¼���ͽ��������ṩ������֧�ֵĽӿڣ�
 * ��Ӧ������ʱ����á�
 * @author DwArFeng
 * @since 1.8
 */
public interface LneMutilang {
	
	/**
	 * ��ȡָ�����ı�������Ӧ���ı���
	 * @param key ָ���ļ���
	 * @return ָ���ļ���Ӧ���ı���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public String getString(LneStringKey key);

}
