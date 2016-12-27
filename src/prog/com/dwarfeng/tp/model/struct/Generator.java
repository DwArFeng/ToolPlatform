package com.dwarfeng.tp.model.struct;

import java.util.Map;

import com.dwarfeng.tp.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.model.cfg.Mutilang;

/**
 * ��������
 * <p> ͨ�� {@link ProgramResource} ���ɳ����ʵ����
 * <br> �����������ڳ����ʼ����ʱ��ʹ�ã��ڴ�ʱ��������쳣��������������ģ�Ӧ���ڸ�֪�û�֮�������˳�����
 * @author  DwArFeng
 * @since 1.8
 */
public interface Generator<T> {
	
	/**
	 * ͨ��������������һ��ָ����ʵ����
	 * @param resourceMap ָ������Դӳ�䡣
	 * @param logger ��¼�й���Ϣ�ļ�¼����
	 * @param mutilang ��¼��ʹ�õĶ����Խӿڡ�
	 * @return �µ�ʵ����
	 * @throws EmergencyException �����ɹ����г����쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public T newInstance(Map<String, ProgramResource> resourceMap, ProgramLogger logger, Mutilang<LoggerStringKey> mutilang) throws EmergencyException;

}
