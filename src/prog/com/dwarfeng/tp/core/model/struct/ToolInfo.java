package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.util.Locale;
import java.util.Map;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.str.Name;

/**
 * ������Ϣ��
 * <p> ע�⣺���ص�����ֵ������Ϊ <code>null</code>��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolInfo extends Name {
	
	/**
	 * ���ع����е�ͼƬ��
	 * @return �����е�ͼƬ��
	 */
	public Image getImage();
	
	/**
	 * ���ع��ߵİ汾��
	 * @return ���ߵİ汾��
	 */
	public Version getVersion();
	
	/**
	 * ��ȡ�����е�����-����ӳ�䡣
	 * @return �����е�����-����ӳ�䡣
	 */
	public Map<Locale, String> getDescriptionMap();
	
	/**
	 * TODO �Ƿ�Ҫ�� Doucument ����ʽ��������������
	 * ��ȡ���ߵ�������
	 * @param locale ָ�������ԣ�����Ϊ <code>null</code>������Ĭ�����ԡ�
	 * @return ָ���������¹��ߵ�������
	 */
	public String getDescription(Locale locale);
	
	/**
	 * ��ȡ���ߵ��������顣
	 * <p> ����Ӧ�ð��չ��׳̶ȵĴ�С������Ϊ���ص����������У�
	 * ��ǰ�����߸��п�����ʾ�ڸ�Ҫ�����ϡ�
	 * @return ���ߵ��������顣
	 */
	public String[] getAuthors();
	
	/**
	 * ��ȡ���ߵĿ��б���
	 * <p> ���ص��ַ���Ӧ��������Ҫʹ�õĿ��ļ����ļ��������ɴ�·����
	 * @return ���ߵĿ��б���
	 */
	public String[] getToolLibs();
	
	/**
	 * ��ȡ���ߵ�����������
	 * @return ���ߵ�����������
	 */
	public String getToolClass();
	
	/**
	 * ��ȡ���������Ϣ������
	 * @return ���������Ϣ������
	 */
	public String getInfoClass();
	
	/**
	 * ��ȡ���ߵ����ļ���jar�������ơ�
	 * <p> �ļ�����Ϊjar�����ļ��������ɴ���·��������Ҫ����׺����
	 * @return ���ߵ����ļ���jar�������ơ�
	 */
	public String getToolFile();
	
}