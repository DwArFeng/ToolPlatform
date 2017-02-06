package com.dwarfeng.tp.core.model.io;

import java.io.File;

import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * ·���������
 * <p> ��ָ��������ת����·����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface PathGetter {

	/**
	 * ��ȡָ�����ƵĿ�ľ���·����
	 * @param name ָ�������ơ�
	 * @return ָ�������ƵĿ�ľ���·����
	 */
	public String getLibraryPath(String name);
	
	/**
	 * ��ȡָ���Ĺ��ߵ�Jar���ľ���·����
	 * @param name Jar�������ơ�
	 * @return Jar�������ƶ�Ӧ�ľ���·����
	 */
	public String getToolFilePath(String name);
	
	/**
	 * ��ȡָ�����ƵĹ��ߵĹ���Ŀ¼��
	 * @param name ָ���Ĺ��ߡ�
	 * @return ָ�����ߵĹ���Ŀ¼��
	 */
	public File getToolDirectory(ToolInfo toolInfo);
}
