package com.dwarfeng.tp.model.cfg;

import com.dwarfeng.dutil.basic.prog.ObverserSet;

/**
 * 国际化接口。
 * @author  DwArFeng
 * @since 1.8
 */
public interface Mutilang extends ObverserSet<MutilangObverser>{
		/**
	 * 获取当前语言下指定字符域所对应的字符串。
	 * @param stringField 指定的字符域。
	 * @return 在当前语言下指定的字符域所对应的字符串。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public String getString(StringKey stringField);
	
}
