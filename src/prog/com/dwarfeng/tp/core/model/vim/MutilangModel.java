package com.dwarfeng.tp.core.model.vim;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.obv.MutilangObverser;
import com.dwarfeng.tp.core.model.struct.MutilangInfo;

/**
 * ������ģ�͡�
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangModel extends Map<Locale, MutilangInfo>, ObverserSet<MutilangObverser>{
	
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
	
}