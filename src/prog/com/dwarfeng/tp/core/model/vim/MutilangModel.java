package com.dwarfeng.tp.core.model.vim;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.obv.MutilangObverser;
import com.dwarfeng.tp.core.model.struct.MutilangInfo;

/**
 * 多语言模型。
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangModel extends Map<Locale, MutilangInfo>, ObverserSet<MutilangObverser>{
	
	/**
	 * 获得此模型中的根目录。
	 * @return 此模型的根目录。
	 */
	public File getDirFile();
	
	/**
	 * 设置此模型的根目录。
	 * @param dirFile 此模型的根目录。
	 * @return 该操作是否对此模型造成了更改。
	 */
	public boolean setDirFile(File dirFile);
	
}
