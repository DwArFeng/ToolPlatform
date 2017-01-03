package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;

import com.dwarfeng.dutil.basic.prog.Version;

/**
 * ������Ϣ��
 * <p> ע�⣺���ص�����ֵ������Ϊ <code>null</code>��
 * @author DwArFeng
 * @since 1.8
 */
public interface ToolInfo {

	/**
	 * ���ع�����ָ�����͵�ͼƬ��
	 * @param type ͼƬ���͡�
	 * @return ������ָ�����͵�ͼƬ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public Image getImage(ToolImageType type);
	
	/**
	 * ���ع��ߵİ汾��
	 * @return ���ߵİ汾��
	 */
	public Version getVersion();
	
	/**
	 * TODO �Ƿ�Ҫ�� Doucument ����ʽ��������������
	 * ��ȡ���ߵ�������
	 * @return ���ߵ�������
	 */
	public String getDescription();
	
	/**
	 * ��ȡ���ߵ��������顣
	 * <p> ����Ӧ�ð��չ��׳̶ȵĴ�С������Ϊ���ص����������У�
	 * ��ǰ�����߸��п�����ʾ�ڸ�Ҫ�����ϡ�
	 * @return ���ߵ��������顣
	 */
	public String[] getAuthors();
	
	/**
	 * ��ȡ���ߵ�����������
	 * @return ���ߵ�����������
	 */
	public String getToolClass();
	
	/**
	 * ��ȡ���ߵ����ļ���jar�������ơ�
	 * <p> �ļ�����Ϊjar�����ļ��������ɴ���·����
	 * @return ���ߵ����ļ���jar�������ơ�
	 */
	public String getToolFile();
	
	/**
	 * ��ȡ���ߵĿ��б�
	 * <p> ���ص��ַ���Ӧ��������Ҫʹ�õĿ��ļ����ļ��������ɴ�·����
	 * @return ���ߵĿ��б�
	 */
	public String[] getToolLibs();
	
}
