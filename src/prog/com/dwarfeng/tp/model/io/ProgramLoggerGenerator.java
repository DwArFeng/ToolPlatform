package com.dwarfeng.tp.model.io;

import java.util.Map;

import com.dwarfeng.tp.model.struct.EmergencyException;

/**
 * �����¼����������
 * @author  DwArFeng
 * @since 1.8
 */
public interface ProgramLoggerGenerator {
	
	/**
	 * ����һ���µĳ����¼����������
	 * @param resourceMap ָ���ĳ�����Դӳ�䡣
	 * @return �µĳ����¼��ʵ����
	 */
	public ProgramLogger newInstance(Map<String, ProgramResource> resourceMap) throws EmergencyException;

}
