package com.dwarfeng.tp.plaf.core;

import java.io.File;

/**
 * �ļ���������
 * <p> Ϊ�˱�֤�ļ��Ŀ�ά���ԣ�ƽ̨�еĹ����漰���ļ���������Ӧ��ʹ�øù������е�
 * ����ļ��ķ���������Ӧ��ֱ������ File ʵ����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface FileManager {

	/**
	 * ��ȡָ��·���µ��ļ���
	 * @param path ָ����·����
	 * @return ָ��·���µ��ļ���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public File getFile(String path);
	
}
