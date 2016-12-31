package com.dwarfeng.tp.core.model.obv;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.MutilangInfo;

/**
 * �����Թ۲�����
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangObverser extends Obverser{

	/**
	 * ֪ͨģ���������ָ�������ԡ�
	 * @param locale ָ�������ԡ�
	 * @param info ��ָ�����Զ�Ӧ�Ķ�������Ϣ��
	 */
	public void fireLocaleAdded(Locale locale, MutilangInfo info);
	
	/**
	 * ֪ͨģ�����Ƴ���ָ�������ԡ�
	 * @param locale ָ�������ԡ�
	 */
	public void fireLocaleRemoved(Locale locale);
	
	/**
	 *  ֪ͨģ����ָ�����ԵĶ�������Ϣ�����˸ı䡣
	 * @param locale ָ�������ԡ�
	 * @param oldOne ָ�������Զ�Ӧ�ľ�������Ϣ��
	 * @param newOne ָ�������Զ�Ӧ����������Ϣ��
	 */
	public void fireInfoChanged(Locale locale, MutilangInfo oldOne, MutilangInfo newOne);
	
	/**
	 * ֪ͨģ���е����ݱ������
	 */
	public void fireCleared();
	
	/**
	 * ֪ͨģ���еĸ�Ŀ¼���ı䡣
	 * @param oldOne �ɵĸ�Ŀ¼��
	 * @param newOne �µĸ�Ŀ¼��
	 */
	public void fireDirFileChanged(File oldOne, File newOne);
	
	/**
	 * ֪ͨģ���е�Ĭ���ı������ı䡣
	 * @param oldOne �ɵ�Ĭ���ı���
	 * @param newOne �µ�Ĭ���ı���
	 */
	public void fireDefaultStringChanged(String oldOne, String newOne);

}
