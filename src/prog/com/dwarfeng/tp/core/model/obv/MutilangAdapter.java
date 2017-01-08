package com.dwarfeng.tp.core.model.obv;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.struct.MutilangInfo;

/**
 * ∂‡”Ô—‘ƒ£–Õπ€≤Ï∆˜  ≈‰∆˜°£
 * @author DwArFeng
 * @since 1.8
 */
public abstract class MutilangAdapter implements MutilangObverser {

	@Override
	public void fireEntryAdded(Locale locale, MutilangInfo info) {	}
	@Override
	public void fireEntryRemoved(Locale locale) {}
	@Override
	public void fireEntryChanged(Locale locale, MutilangInfo oldOne, MutilangInfo newOne) {}
	@Override
	public void fireCleared() {}
	@Override
	public void fireDirectionChanged(File oldOne, File newOne) {}
	@Override
	public void fireSupportedKeysChanged(Set<Name> oldOne, Set<Name> newOne) {}
	@Override
	public void fireCurrentLocaleChanged(Locale oldOne, Locale newOne) {}
	@Override
	public void fireDefaultMutilangMapChanged(Map<Name, String> oldOne, Map<Name, String> newOne) {}
	@Override
	public void fireDefaultVauleChanged(String oldOne, String newOne) {}
	@Override
	public void fireUpdated() {}

}
