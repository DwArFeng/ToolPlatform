package com.dwarfeng.tp.control;

import java.net.URL;

import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;

/**
 * ��¼�����һЩ�̶���������ԡ�
 * @author  DwArFeng
 * @since 1.8
 */
public class ProgramAttributes {
	
	/**����İ汾*/
	public final Version VERSION = new DefaultVersion.Builder()
			.type(VersionType.RELEASE)
			.firstVersion((byte) 0)
			.secondVersion((byte) 0)
			.thirdVersion((byte) 0)
			.buildDate("20161222")
			.buildVersion('A')
			.build();
	
	/**���������*/
	public final String AUTHOR = "DwArFeng";
	
	/**����ļ�¼��·����Ĭ��λ��*/
	public final URL LOGGER_PATH = ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/logger-path.xml");
	
	/**Logger����ʧ�ܵı���*/
	public final String LOGGER_FAIL_TITLE = "Logger ����ʧ��";
	
	/**Logger����ʧ����Ϣ0*/
	public final String LOGGER_FAIL_MESSAGE_0 = "xml�ļ�����ʧ�ܣ�����ϵͳ���û����������ԡ����ܵ�ԭ���ǣ�\n"
			+ "\t1. ָ����xml·����Ч��\n"
			+ "\t2. xml�е������𻵡�";
	
	/**Logger����ʧ����Ϣ1*/
	public final String LOGGER_FAIL_MESSAGE_1 = "xml�ļ�����ʧ�ܣ���������ļ�����ȷ�ԡ����ܵ�ԭ���ǣ�\n"
			+ "\t1. xml�е������𻵡�\n"
			+ "\t2. xml���Ҳ�����������Ϊ key=\"logger-cfg\" �� info Ԫ�ء�";

	public final String LOGGER_FAIL_MESSAGE_2 = "xml�ļ�����ʧ�ܣ���������ļ�����ȷ�ԡ����ܵ�ԭ���ǣ�\n"
			+ "\t1. xml�� info Ԫ�����ӽڵ�ȱʧ��\n"
			+ "\t2. xml�� default-path �е� type ���Ժ���δ֪��ֵ��\n"
			+ "\t3. xml�� release-path �е� type ���Ժ���δ֪��ֵ��\n"
			+ "\t4. xml�� release-path �е� type ���Գ����в�֧�֡�";
	
	public final String LOGGER_FAIL_MESSAGE_3 = "�ļ��޷�����ȷ���ͷš����ܵ�ԭ���ǣ�\n"
			+ "\t1. �ļ���ռ�á�\n"
			+ "\t2. xml����Ӧ�����ô��ļ�����Ŀ¼�������﷨����ȷ��\n";
	
	public final String LOGGER_FAIL_MESSAGE_4 = "Logger�����޷�����ȷ�Ķ�ȡ�����ܵ�ԭ���ǣ�\n"
			+ "\tLogger���ò���ȷ";
	
	public final String LOGGER_FAIL_MESSAGE_5 = "Logger�޷���ȷ���ɡ�";
	
	public final String MISSING_STRING = "!�ı���ʧ";
	
}
