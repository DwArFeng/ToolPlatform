package com.dwarfeng.tp.core.model.struct;

import java.util.Locale;
import java.util.Set;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.cm.MutilangModel;

/**
 * 多语言接口提供器。
 * <p>提供多语言接口。
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangProvider {
	
	/**
	 * 返回该多语言接口提供器使用的多语言模型。
	 * @return 使用的多语言模型。
	 */
	public MutilangModel getMutilangModel();

	/**
	 * 获取当前的多语言接口。
	 * <p> 该返回值应恒不为 <code>null</code>。
	 * @return 当前的多语言接口。
	 */
	public Mutilang getMutilang();
	
	/**
	 * 获取该多语言接口提供器中受支持的所有键。
	 * <p> 返回的集合是不可改变的，试图调用其中的更改方法会抛出 {@link UnsupportedOperationException}。
	 * @return 该多语言接口提供器中受支持的所有字段。
	 */
	public Set<Name> getSupportedKeys();
	
	/**
	 * 判断指定的键是否受该提供器的支持。
	 * <p> 入口参数可以为 <code>null</code>，此时，该方法恒返回 <code>false</code>。
	 * @param key 指定的键。
	 * @return 该提供器是否支持指定的键。
	 */
	public boolean isSupport(Name key);
	
	/**
	 * 将多语言接口更新为默认值。
	 */
	public void update2Default();
	
	/**
	 * 刷新多语言接口，使多语言接口符合指定的语言。
	 * @param locale 指定的语言。
	 * @throws MutilangException 多语言接口异常。
	 * @throws ProcessException 过程异常。
	 */
	public void update(Locale locale) throws ProcessException;
}
