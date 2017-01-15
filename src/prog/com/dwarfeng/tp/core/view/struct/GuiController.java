package com.dwarfeng.tp.core.view.struct;

import java.awt.Component;

/**
 * ͼ�ν��������������
 * <p> ���ڿ���ͼ�ν������档
 * @author  DwArFeng
 * @since 1.8
 */
public interface GuiController<T extends Component> {

	/**
	 * ����һ����ʵ����
	 * <p> ���ǰһ��ʵ����û�б��ͷţ��򲻽����κβ��������� <code>false</code>��
	 * @return �ò����Ƿ�������һ����ʵ����
	 */
	public boolean newInstance();
	
	/**
	 * ���ظÿ������Ƿ��Ѿ�ӵ����һ��ʵ����
	 * @return �Ƿ��Ѿ�ӵ����һ��ʵ����
	 */
	public boolean hasInstance();
	
	/**
	 * ��ȡ�ÿ�������ʵ����
	 * <p> ���û��ʵ�����򷵻� <code>null</code>��
	 * @return �ÿ�������ʵ����
	 */
	public T getInstance();
	
	/**
	 * �ͷ�ʵ����
	 * <p> �ͷ�ʵ���󣬽�ʵ������Ϊ <code>null</code>�� ͬʱ {@link #hasInstance()} ���������� <code>false</code>��
	 * <p> �����ʱ�������е�ʵ���Ѿ����ͷ��ˣ���ʲôҲ�������ҷ��� <code>false</code>��
	 * @return ʵ���Ƿ��ͷš�
	 */
	public boolean dispose();
	
	/**
	 * ���ظÿ�����ʵ���еĶ����Ƿ�Ϊ�ɼ��ġ�
	 * <p> �����ʱ��������û��ʵ�����򷵻� <code>false</code>��
	 * @return �ÿ�����ʵ���еĶ����Ƿ�Ϊ�ɼ��ġ�
	 */
	public boolean isVisible();
	
	/**
	 * ���ÿ������е�ʵ���Ƿ�ɼ���
	 * <p> �����������û��ʵ������ʲôҲ�������ҷ��� <code>false</code>��
	 * @return �Ƿ�ɹ������á�
	 */
	public boolean setVisible(boolean aFlag);
	
	/**
	 * չʾ�ÿ������е�ʵ����
	 * <p> ����ÿ�������û��ʵ�������½�һ��ʵ����Ȼ�󽫴�ʵ����Ϊ�ɼ���
	 * �������������ʵ������ֱ�ӽ�����Ϊ�ɼ���
	 */
	public void show();
	
}
