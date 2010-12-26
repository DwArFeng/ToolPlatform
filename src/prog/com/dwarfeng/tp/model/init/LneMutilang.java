package com.dwarfeng.tp.model.init;

import com.dwarfeng.tp.model.cfg.LneStringKey;

/**
 * 一个仅支持记录和紧急窗口的多语言接口。
 * <p> 该接口是在引用关系建立之前临时向记录器和紧急窗口提供多语言支持的接口，
 * 不应在其它时候调用。
 * @author DwArFeng
 * @since 1.8
 */
public interface LneMutilang {
	
	/**
	 * 获取指定的文本键所对应的文本。
	 * @param key 指定的键。
	 * @return 指定的键对应的文本。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public String getString(LneStringKey key);

}
