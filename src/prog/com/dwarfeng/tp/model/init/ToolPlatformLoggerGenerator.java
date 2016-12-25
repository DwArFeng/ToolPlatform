package com.dwarfeng.tp.model.init;

import java.net.URL;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.model.io.ToolPlatformLogger;

/**
 * ƽ̨�ü�¼����������
 * <p> ��������ƽ̨��¼����
 * @author  DwArFeng
 * @since 1.8
 */
public interface ToolPlatformLoggerGenerator {
	
	/**
	 * ����һ����ָ������ȷ�����µ�ƽ̨�ü�¼����
	 * @param path ָ���ļ�¼��·���������ڵ�·����
	 * @param forceOverride �Ƿ�ǿ�и������е����á�
	 * @return �µ�ƽ̨�ü�¼����
	 * @throws NullPointerException ��ڲ���Ϊ null��
	 * @throws LoadFailedException ��ȡʧ���쳣��
	 */
	public ToolPlatformLogger genToolPlatformLogger(URL path, boolean forceOverride) throws LoadFailedException;

}
