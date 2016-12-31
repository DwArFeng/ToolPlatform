package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 国际化观察器。
 * @author DwArFeng
 * @since 1.8
 */
public interface MutilangObverser extends Obverser {

	/**
	 * 通知程序的语言发生改变。
	 */
	public void fireLanguageChanged();
	
}
