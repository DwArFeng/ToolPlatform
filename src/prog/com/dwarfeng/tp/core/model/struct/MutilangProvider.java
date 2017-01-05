package com.dwarfeng.tp.core.model.struct;

/**
 * 多语言接口提供器 。
 * <p>提供多语言接口。
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangProvider {
	
	/**
	 * 获取当前的多语言接口。
	 * <p> 该返回值应恒不为 <code>null</code>。
	 * @return 当前的多语言接口。
	 */
	public Mutilang getMutilang();
}
