package com.dwarfeng.tp.core.model.cm;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.obv.MutilangObverser;
import com.dwarfeng.tp.core.model.struct.MutilangInfo;
import com.dwarfeng.tp.core.model.struct.ProcessException;
import com.dwarfeng.tp.core.model.struct.ReadWriteThreadSafe;

/**
 * ������ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangModel extends Map<Locale, MutilangInfo>, ObverserSet<MutilangObverser>, ReadWriteThreadSafe{
	
	/**
	 * ��ô�ģ���еĸ�Ŀ¼��
	 * @return ��ģ�͵ĸ�Ŀ¼��
	 */
	public File getDirection();
	
	/**
	 * ���ô�ģ�͵ĸ�Ŀ¼��
	 * @param direction ��ģ�͵ĸ�Ŀ¼��
	 * @return �ò����Ƿ�Դ�ģ������˸��ġ�
	 */
	public boolean setDircetion(File direction);
	
	/**
	 * ��ȡ������ģ������֧�ֵļ�ֵ���ϡ�
	 * <p> �ü����ǲ��ɸ��ĵģ����Ե�����༭�������׳� {@link UnsupportedOperationException}��
	 * @return ������ģ������֧�ֵļ����ϡ�
	 */
	public Set<Name> getSupportedKeys();
	
	/**
	 * ���øö�����ģ������֧�ֵļ�ֵ���ϡ�
	 * @param names ָ���ļ�ֵ���ϡ�
	 * @return �ò����Ƿ�Ը�ģ������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean setSupportedKeys(Set<Name> names);
	
	/**
	 * ��ȡģ���е�ǰ�����ԣ�<code>null</code>����Ĭ�����ԡ�
	 * @return ģ���еĵ�ǰ���ԣ�<code>null</code>����Ĭ�����ԡ�
	 */
	public Locale getCurrentLocale();
	
	/**
	 * ����ģ���еĵ�ǰ���ԡ�
	 * <p> ��ڲ���ֻ��Ϊ <code>null</code> - ����Ĭ�����ԣ������Ǹ�ģ���а��������ԣ��� <code>containsKey(locale) == true</code>��
	 * ���򣬻��׳� {@link IllegalArgumentException}
	 * <p> �÷������᳢�Խ���ǰ������Ϊָ�������ԣ������׳��쳣�������ò��ɹ�ʱ������ false��
	 * @param locale ָ�������ԡ�
	 * @return �ò����Ƿ�Ը�ģ������˸ı䡣
	 * @throws IllegalArgumentException ָ�������Բ�Ϊ <code>null</code>,��ģ���в����������ԡ�
	 */
	public boolean setCurrentLocale(Locale locale);
	
	/**
	 * ��������ģ���еĵ�ǰ����Ϊָ�����ԡ�
	 * <p> ��ڲ���ֻ��Ϊ <code>null</code> - ����Ĭ�����ԣ������Ǹ�ģ���а��������ԣ��� <code>containsKey(locale) == true</code>��
	 * ���򣬻��׳� {@link IllegalArgumentException}��
	 * <p> �÷������᳢�Խ���ǰ������Ϊָ�������ԣ������ù����������쳣ʱ�������׳� {@link ProcessException}��
	 * ����ͨ�� {@link ProcessException#getCause()}���鿴���������쳣��
	 * @param locale ָ�������ԡ�
	 * @return �ò����Ƿ�Ը�ģ������˸ı䡣
	 * @throws IllegalArgumentException ָ�������Բ�Ϊ <code>null</code>,��ģ���в����������ԡ�
	 * @throws ProcessException ���ù����������쳣��
	 */
	public boolean trySetCurrentLocale(Locale locale) throws ProcessException;
	
	/**
	 * ��ȡģ���еĶ����Լ�ֵӳ�䡣
	 * <p> ���صĶ����Լ�ֵӳ�����ڵ�ǰ���Ի����µļ�ֵӳ�䡣
	 * <p> �ü�ֵӳ���ǲ����޸ĵģ���ͼ������༭�������׳� {@link UnsupportedOperationException}��
	 * @return �����Լ�ֵӳ�䡣
	 */
	public Map<Name, String> getMutilangMap();
	
	/**
	 * ��ȡģ���е�Ĭ�϶����Լ�ֵӳ�䡣
	 * <p> �÷������ص�ǰ����Ϊ <code>null</code>������µĶ����Լ�ֵӳ�䡣
	 * <p> �ü�ֵӳ���ǲ��ɸ��ĵģ���ͼ������༭�������׳� {@link UnsupportedOperationException}��
	 * @return Ĭ�ϵĶ����Լ�ֵӳ�䡣
	 */
	public Map<Name, String> getDefaultMutilangMap();
	
	/**
	 * ����ģ���е�Ĭ�϶����Լ�ֵӳ�䡣
	 * @param mutilangMap ָ���Ķ����Լ�ֵӳ�䡣
	 * @return �ò����Ƿ�Ը�ģ������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean setDefaultMutilangMap(Map<Name, String> mutilangMap);
	
	/**
	 * ��ȡģ���еĶ����Լ�ֵӳ���Ĭ��ֵ��
	 * <p> ����ڶ�����ӳ���У��Ҳ�����Ӧ�ļ���ֵ����ô�ͷ��ظ�ֵ��
	 * @return ������ӳ��ֵ���Ĭ��ֵ��
	 */
	public String getDefaultValue();
	
	/**
	 * ����ģ���еĶ����Լ�ֵĬ��ֵ��
	 * @param value ָ����ֵ��
	 * @return �ò����Ƿ��ģ������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean setDefaultValue(String value);
	
}
