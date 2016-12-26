package com.dwarfeng.tp.model.io;

import java.util.Map;

import com.dwarfeng.tp.model.struct.EmergencyException;

/**
 * 
 * @author DwArFeng
 * @since 1.8
 */
public interface ProgramResourceLoader {

	/**
	 * ��ȡ������Դ��
	 * @return ��Դ�����Ӧ��Դ��ɵ�ӳ�䡣
	 * @throws EmergencyException ��Ӧ�����޷�������ɵĽ����쳣��
	 */
	public Map<String, ProgramResource> loadResources() throws EmergencyException;
	
}
