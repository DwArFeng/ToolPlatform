package com.dwarfeng.tp.model.cfg;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

/**
 * 恒真式配置值检查器。
 * @author DwArFeng
 * @since 1.8
 */
public final class TrueConfigChecker implements ConfigChecker {

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigChecker#isValid(java.lang.String)
	 */
	@Override
	public boolean isValid(String value) {
		return true;
	}

}
