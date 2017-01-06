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
 * 多语言模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangModel extends Map<Locale, MutilangInfo>, ObverserSet<MutilangObverser>, ReadWriteThreadSafe{
	
	/**
	 * 获得此模型中的根目录。
	 * @return 此模型的根目录。
	 */
	public File getDirection();
	
	/**
	 * 设置此模型的根目录。
	 * @param direction 此模型的根目录。
	 * @return 该操作是否对此模型造成了更改。
	 */
	public boolean setDircetion(File direction);
	
	/**
	 * 获取多语言模型中受支持的键值集合。
	 * <p> 该集合是不可更改的，尝试调用其编辑方法会抛出 {@link UnsupportedOperationException}。
	 * @return 多语言模型中受支持的键集合。
	 */
	public Set<Name> getSupportedKeys();
	
	/**
	 * 设置该多语言模型中受支持的键值集合。
	 * @param names 指定的键值集合。
	 * @return 该操作是否对该模型造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setSupportedKeys(Set<Name> names);
	
	/**
	 * 获取模型中当前的语言，<code>null</code>代表默认语言。
	 * @return 模型中的当前语言，<code>null</code>代表默认语言。
	 */
	public Locale getCurrentLocale();
	
	/**
	 * 设置模型中的当前语言。
	 * <p> 入口参数只能为 <code>null</code> - 代表默认语言，或者是该模型中包含的语言，即 <code>containsKey(locale) == true</code>。
	 * 否则，会抛出 {@link IllegalArgumentException}
	 * <p> 该方法将会尝试将当前语言设为指定的语言，不会抛出异常，当设置不成功时，返回 false。
	 * @param locale 指定的语言。
	 * @return 该操作是否对该模型造成了改变。
	 * @throws IllegalArgumentException 指定的语言不为 <code>null</code>,且模型中不包含该语言。
	 */
	public boolean setCurrentLocale(Locale locale);
	
	/**
	 * 尝试设置模型中的当前语言为指定语言。
	 * <p> 入口参数只能为 <code>null</code> - 代表默认语言，或者是该模型中包含的语言，即 <code>containsKey(locale) == true</code>。
	 * 否则，会抛出 {@link IllegalArgumentException}。
	 * <p> 该方法将会尝试将当前语言设为指定的语言，当设置过程中遇到异常时，将会抛出 {@link ProcessException}，
	 * 可以通过 {@link ProcessException#getCause()}来查看遭遇到的异常。
	 * @param locale 指定的语言。
	 * @return 该操作是否对该模型造成了改变。
	 * @throws IllegalArgumentException 指定的语言不为 <code>null</code>,且模型中不包含该语言。
	 * @throws ProcessException 设置过程中遇到异常。
	 */
	public boolean trySetCurrentLocale(Locale locale) throws ProcessException;
	
	/**
	 * 获取模型中的多语言键值映射。
	 * <p> 返回的多语言键值映射是在当前语言环境下的键值映射。
	 * <p> 该键值映射是不可修改的，试图调用其编辑方法会抛出 {@link UnsupportedOperationException}。
	 * @return 多语言键值映射。
	 */
	public Map<Name, String> getMutilangMap();
	
	/**
	 * 获取模型中的默认多语言键值映射。
	 * <p> 该方法返回当前语言为 <code>null</code>的情况下的多语言键值映射。
	 * <p> 该键值映射是不可更改的，试图调用其编辑方法会抛出 {@link UnsupportedOperationException}。
	 * @return 默认的多语言键值映射。
	 */
	public Map<Name, String> getDefaultMutilangMap();
	
	/**
	 * 设置模型中的默认多语言键值映射。
	 * @param mutilangMap 指定的多语言键值映射。
	 * @return 该操作是否对该模型造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setDefaultMutilangMap(Map<Name, String> mutilangMap);
	
	/**
	 * 获取模型中的多语言键值映射的默认值。
	 * <p> 如果在多语言映射中，找不到对应的键的值，那么就返回该值。
	 * @return 多语言映键值射的默认值。
	 */
	public String getDefaultValue();
	
	/**
	 * 设置模型中的多语言键值默认值。
	 * @param value 指定的值。
	 * @return 该操作是否对模型造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setDefaultValue(String value);
	
}
