package com.dwarfeng.tp.core.model.vim;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.tp.core.model.obv.MutilangObverser;
import com.dwarfeng.tp.core.model.struct.MutilangInfo;

/**
 * 默认多语言模型。
 * <p> 多语言模型接口的默认实现。
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultMutilangModel extends AbstractMutilangModel {
	
	private final Map<Locale, MutilangInfo> delegate = new HashMap<>();
	
	private File dirFile;
	
	/**
	 * 新实例。
	 */
	public DefaultMutilangModel(){}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.vim.MutilangModel#getDir()
	 */
	@Override
	public File getDirFile() {
		return dirFile;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.vim.MutilangModel#setDir(java.io.File)
	 */
	@Override
	public boolean setDirFile(File dirFile) {
		if(this.dirFile.equals(dirFile)) return false;
		File oldOne = this.dirFile;
		this.dirFile = dirFile;
		fireDirFileChanged(oldOne, dirFile);
		return true;
	}
	
	private void fireDirFileChanged(File oldOne, File newOne){
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDirFileChanged(oldOne, newOne);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return delegate.size();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		return delegate.containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		return delegate.containsValue(value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public MutilangInfo get(Object key) {
		return delegate.get(key);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public MutilangInfo put(Locale key, MutilangInfo value) {
		Objects.requireNonNull(key, "入口参数 key 不能为 null。");
		Objects.requireNonNull(value, "入口参数 value 不能为 null。");
		
		if(containsKey(key)){
			MutilangInfo oldOne = get(key);
			fireInfoChanged(key, oldOne, value);
		}else{
			fireLocaleAdded(key, value);
		}
		
		return delegate.put(key, value);
	}

	private void fireLocaleAdded(Locale locale, MutilangInfo info) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryAdded(locale, info);
		}
	}

	private void fireInfoChanged(Locale locale, MutilangInfo oldOne, MutilangInfo newOne) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryChanged(locale, oldOne, newOne);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public MutilangInfo remove(Object key) {
		if(containsKey(key)){
			fireLocaleRemoved((Locale) key);
		}
		return delegate.remove(key);
	}

	private void fireLocaleRemoved(Locale locale) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryRemoved(locale);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends Locale, ? extends MutilangInfo> m) {
		Objects.requireNonNull(m, "入口参数 m 不能为 null。");
		for(Map.Entry<? extends Locale, ? extends MutilangInfo> entry : m.entrySet()){
			put(entry.getKey(), entry.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		fireCleared();
		this.dirFile = null;
		delegate.clear();
	}

	private void fireCleared() {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCleared();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<Locale> keySet() {
		return Collections.unmodifiableSet(delegate.keySet());
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<MutilangInfo> values() {
		return Collections.unmodifiableCollection(delegate.values());
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<Locale, MutilangInfo>> entrySet() {
		return Collections.unmodifiableSet(delegate.entrySet());
	}

}
