package com.dwarfeng.tp.core.model.struct;

/**
 * �赲�ӿڡ�
 * <p> �ýӿ��̰߳�ȫ�������Ⱪ¶ͬ������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface Block{

	/**
	 * ��ָ���ļ���ָʾ�ı�׼ȥ�赲��ǰ���̡߳�
	 * <p> ����ڷ���������ʱ�ü���ָʾ�ı�׼������ִ�е���������ô���ø÷������߳���������
	 * ֱ���ü���ָʾ�ı�׼����ִ������ʱ�����̲߳ż���ִ�С�
	 * @param key ָ���ļ���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void block(String key);
	
	/**
	 * ����赲��
	 * <p> �÷��������ô˷������̴߳��赲�����Ƴ�������֪ͨ�������赲���������ж��赲״����
	 * @param key ָ���ļ���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void unblock(String key);
	
}