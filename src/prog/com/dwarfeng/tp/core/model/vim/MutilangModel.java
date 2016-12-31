package com.dwarfeng.tp.core.model.vim;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.obv.MutilangObverser;
import com.dwarfeng.tp.core.model.struct.MutilangInfo;

/**
 * ������ģ�͡�
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangModel extends Map<Locale, MutilangInfo>, ObverserSet<MutilangObverser>{
	
	/**
	 * ��ȡģ�������еļ�ֵ���ϡ�
	 * @return ģ�������еļ�ֵ���ϡ�
	 */
	public Set<Name> nameSet();
	
	/**
	 * ��ȡ��ģ���Ƿ�֧��ָ���ļ�ֵ��
	 * @param key ָ���ļ�ֵ��
	 * @return ��ģ���Ƿ�֧��ָ���ļ�ֵ��
	 */
	public boolean isSupport(Name key);
	
	/**
	 * ��ô�ģ���еĸ�Ŀ¼��
	 * @return ��ģ�͵ĸ�Ŀ¼��
	 */
	public File getDirFile();
	
	/**
	 * ���ô�ģ�͵ĸ�Ŀ¼��
	 * @param dirFile ��ģ�͵ĸ�Ŀ¼��
	 * @return �ò����Ƿ�Դ�ģ������˸��ġ�
	 */
	public boolean setDirFile(File dirFile);
	
	/**
	 * ��ȡģ���е�Ĭ���ı���
	 * <p> Ĭ���ı��������Ҳ���ָ���ļ�ʱ���Է��ص�Ĭ���ı���
	 * @return ģ���е�Ĭ���ı���
	 */
	public String getDefaultString();
	
	/**
	 * ����ģ���е�Ĭ���ı���
	 * <p> Ĭ���ı��������Ҳ���ָ���ļ�ʱ���Է��ص��ı���
	 * @param defaultString ָ����Ĭ���ı���
	 * @return �ò����Ƿ�Դ�ģ������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean setDefaultString(String defaultString);
}
