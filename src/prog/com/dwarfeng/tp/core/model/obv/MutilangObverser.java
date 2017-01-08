package com.dwarfeng.tp.core.model.obv;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.struct.MutilangInfo;

/**
 * 多语言观察器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangObverser extends Obverser{

	/**
	 * 通知模型中添加了指定的语言。
	 * @param locale 指定的语言。
	 * @param info 与指定语言对应的多语言信息。
	 */
	public void fireEntryAdded(Locale locale, MutilangInfo info);
	
	/**
	 * 通知模型中移除了指定的语言。
	 * @param locale 指定的语言。
	 */
	public void fireEntryRemoved(Locale locale);
	
	/**
	 *  通知模型中指定语言的多语言信息发生了改变。
	 * @param locale 指定的语言。
	 * @param oldOne 指定的语言对应的旧语言信息。
	 * @param newOne 指定的语言对应的新语言信息。
	 */
	public void fireEntryChanged(Locale locale, MutilangInfo oldOne, MutilangInfo newOne);
	
	/**
	 * 通知模型中的数据被清除。
	 */
	public void fireCleared();
	
	/**
	 * 通知模型中的根目录被改变。
	 * @param oldOne 旧的根目录。
	 * @param newOne 新的根目录。
	 */
	public void fireDirectionChanged(File oldOne, File newOne);
	
	/**
	 * 通知模型中的受支持的键集合发生改变。
	 * @param oldOne 旧的受支持键集合。
	 * @param newOne 新的受支持键集合。
	 */
	public void fireSupportedKeysChanged(Set<Name> oldOne, Set<Name> newOne);
	
	/**
	 * 通知模型中的当前语言发生了改变。
	 * @param oldOne 旧的当前语言。
	 * @param newOne 新的当前语言。
	 */
	public void fireCurrentLocaleChanged(Locale oldOne, Locale newOne);
	
	/**
	 * 通知模型中的默认多语言键值映射发生了改变。
	 * @param oldOne 旧的多语言键值映射。
	 * @param newOne 新的多语言键值映射。
	 */
	public void fireDefaultMutilangMapChanged(Map<Name, String> oldOne, Map<Name, String> newOne);
	
	/**
	 * 通知模型中的默认文本发生改变。
	 * @param oldOne 旧的默认文本。
	 * @param newOne 新的默认文本。
	 */
	public void fireDefaultVauleChanged(String oldOne, String newOne);
	
	/**
	 * 通知模型中进行了更新操作。
	 */
	public void fireUpdated();
	
}
