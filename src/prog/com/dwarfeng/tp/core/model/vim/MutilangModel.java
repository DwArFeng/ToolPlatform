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
 * 多语言模型。
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangModel extends Map<Locale, MutilangInfo>, ObverserSet<MutilangObverser>{
	
	/**
	 * 获取模型中所有的键值集合。
	 * @return 模型中所有的键值集合。
	 */
	public Set<Name> nameSet();
	
	/**
	 * 获取此模型是否支持指定的键值。
	 * @param key 指定的键值。
	 * @return 此模型是否支持指定的键值。
	 */
	public boolean isSupport(Name key);
	
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
	
	/**
	 * 获取模型中的默认文本。
	 * <p> 默认文本是用在找不到指定的键时用以返回的默认文本。
	 * @return 模型中的默认文本。
	 */
	public String getDefaultString();
	
	/**
	 * 设置模型中的默认文本。
	 * <p> 默认文本是用在找不到指定的键时用以返回的文本。
	 * @param defaultString 指定的默认文本。
	 * @return 该操作是否对此模型造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setDefaultString(String defaultString);
}
