package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.util.Locale;
import java.util.Map;
import com.dwarfeng.dutil.basic.prog.Version;

/**
 * ������Ϣ��
 * <p> ע�⣺���ص�����ֵ������Ϊ <code>null</code>��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolInfo {
	
	/**
	 * ���ع�����ָ�����͵�ͼƬ��
	 * @return ������ָ�����͵�ͼƬ��
	 * @throws ProcessException �����쳣��
	 */
	public Image getImage() throws ProcessException;
	
	/**
	 * ���ع��ߵİ汾��
	 * @return ���ߵİ汾��
	 * @throws ProcessException �����쳣��
	 */
	public Version getVersion() throws ProcessException;
	
	/**
	 * ��ȡ�����е�����-����ӳ�䡣
	 * @return �����е�����-����ӳ�䡣
	 * @throws ProcessException �����쳣��
	 */
	public Map<Locale, String> getDescriptionMap() throws ProcessException;
	
	/**
	 * TODO �Ƿ�Ҫ�� Doucument ����ʽ��������������
	 * ��ȡ���ߵ�������
	 * @param ָ�������ԣ�����Ϊ <code>null</code>������Ĭ�����ԡ�
	 * @return ָ���������¹��ߵ�������
	 * @throws ProcessException �����쳣��
	 */
	public String getDescription(Locale locale) throws ProcessException;
	
	/**
	 * ��ȡ���ߵ��������顣
	 * <p> ����Ӧ�ð��չ��׳̶ȵĴ�С������Ϊ���ص����������У�
	 * ��ǰ�����߸��п�����ʾ�ڸ�Ҫ�����ϡ�
	 * @return ���ߵ��������顣
	 * @throws ProcessException �����쳣��
	 */
	public String[] getAuthors() throws ProcessException;
	
	/**
	 * ��ȡ���ߵĿ��б�
	 * <p> ���ص��ַ���Ӧ��������Ҫʹ�õĿ��ļ����ļ��������ɴ�·����
	 * @return ���ߵĿ��б�
	 * @throws ProcessException �����쳣��
	 */
	public String[] getToolLibs() throws ProcessException;
	
	/**
	 * ��ȡ���ߵ�����������
	 * @return ���ߵ�����������
	 * @throws ProcessException �����쳣��
	 */
	public String getToolClass() throws ProcessException;
	
	/**
	 * ��ȡ���������Ϣ������
	 * @return ���������Ϣ������
	 * @throws ProcessException �����쳣��
	 */
	public String getInfoClass() throws ProcessException;
	
	/**
	 * ��ȡ���ߵ����ļ���jar�������ơ�
	 * <p> �ļ�����Ϊjar�����ļ��������ɴ���·����
	 * @return ���ߵ����ļ���jar�������ơ�
	 * @throws ProcessException �����쳣��
	 */
	public String getToolFile() throws ProcessException;
	
}
