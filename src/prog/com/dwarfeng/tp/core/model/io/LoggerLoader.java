package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.cm.LoggerModel;

/**
 * ��¼��ģ�Ͷ�ȡ����
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerLoader {
	
	/**
	 * ��ָ���ļ�¼��ģ���ж�ȡ���ݡ�
	 * @param loggerModel ָ���ļ�¼��ģ��
	 * @throws LoadFailedException ��ȡʧ���쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void load(LoggerModel loggerModel) throws LoadFailedException;

}
