package com.dwarfeng.tp.core.model.struct;

/**
 * �ɸ��½ӿڡ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface Updateable{
	
	/**
	 * ���¸ýӿڡ�
	 * <p> �ù����� {@link #tryUpdate()}��һ�����ǣ�����ڸ��¹����з����쳣��
	 * �÷�����ѡ�����ڲ������쳣�������Է��� <code>false</code>����ʽ��֪�û����²��ɹ���
	 * @return ���¹����Ƿ�ɹ���
	 */
	public boolean update();
	
	/**
	 * ���Ը��¸ýӿڡ�
	 * <p> ����ڸ��µĹ����з������쳣���÷����Ὣ�쳣��װ�� {@link ProcessException} �׳���
	 * �û����Բ鿴 {@link ProcessException#getCause()}����÷������쳣��
	 * @throws ProcessException �����쳣��
	 */
	public void tryUpdate() throws ProcessException;

}
