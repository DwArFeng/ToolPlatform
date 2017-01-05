package com.dwarfeng.tp.core.model.obv;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.dutil.basic.str.Name;
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
	public void fireEntryAdded(Locale locale, MutilangInfo info);
	
	/**
	 * ֪ͨģ�����Ƴ���ָ�������ԡ�
	 * @param locale ָ�������ԡ�
	 */
	public void fireEntryRemoved(Locale locale);
	
	/**
	 *  ֪ͨģ����ָ�����ԵĶ�������Ϣ�����˸ı䡣
	 * @param locale ָ�������ԡ�
	 * @param oldOne ָ�������Զ�Ӧ�ľ�������Ϣ��
	 * @param newOne ָ�������Զ�Ӧ����������Ϣ��
	 */
	public void fireEntryChanged(Locale locale, MutilangInfo oldOne, MutilangInfo newOne);
	
	/**
	 * ֪ͨģ���е����ݱ������
	 */
	public void fireCleared();
	
	/**
	 * ֪ͨģ���еĸ�Ŀ¼���ı䡣
	 * @param oldOne �ɵĸ�Ŀ¼��
	 * @param newOne �µĸ�Ŀ¼��
	 */
	public void fireDirectionChanged(File oldOne, File newOne);
	
	/**
	 * ֪ͨģ���е���֧�ֵļ����Ϸ����ı䡣
	 * @param oldOne �ɵ���֧�ּ����ϡ�
	 * @param newOne �µ���֧�ּ����ϡ�
	 */
	public void fireSupportedKeysChanged(Set<Name> oldOne, Set<Name> newOne);
	
	/**
	 * ֪ͨģ���еĵ�ǰ���Է����˸ı䡣
	 * @param oldOne �ɵĵ�ǰ���ԡ�
	 * @param newOne �µĵ�ǰ���ԡ�
	 */
	public void fireCurrentLocaleChanged(Locale oldOne, Locale newOne);
	
	/**
	 * ֪ͨģ���е�Ĭ�϶����Լ�ֵӳ�䷢���˸ı䡣
	 * @param oldOne �ɵĶ����Լ�ֵӳ�䡣
	 * @param newOne �µĶ����Լ�ֵӳ�䡣
	 */
	public void fireDefaultMutilangMapChanged(Map<Name, String> oldOne, Map<Name, String> newOne);
	
	/**
	 * ֪ͨģ���е�Ĭ���ı������ı䡣
	 * @param oldOne �ɵ�Ĭ���ı���
	 * @param newOne �µ�Ĭ���ı���
	 */
	public void fireDefaultStringChanged(String oldOne, String newOne);

}
