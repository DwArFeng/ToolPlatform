package com.dwarfeng.tp.core.model.vim;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.obv.MutilangObverser;
import com.dwarfeng.tp.core.model.struct.MutilangInfo;

/**
 * 默认多语言模型。
 * <p> 多语言模型接口的默认实现。
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultMutilangModel extends AbstractMutilangModel {
	
	private final Set<Name> nameSet;
	
	private final Map<Locale, MutilangInfo> delegate = new HashMap<>();
	
	private File dirFile;
	private String defaultString;
	
	/**
	 * 新实例。
	 * @param names 指定的键值数组。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultMutilangModel(Name[] names){
		Objects.requireNonNull(names, "入口参数 names 不能为 null。");
		this.nameSet = new HashSet<>(Arrays.asList(names));
	}
	
	/**
	 * 新实例。
	 * @param nameSet 指定的键值集合。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultMutilangModel(Set<Name> nameSet) {
		Objects.requireNonNull(nameSet, "入口参数 nameSet 不能为 null。");
		this.nameSet = nameSet;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.vim.MutilangModel#nameSet()
	 */
	@Override
	public Set<Name> nameSet() {
		return Collections.unmodifiableSet(nameSet);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.vim.MutilangModel#isSupport(com.dwarfeng.dutil.basic.str.Name)
	 */
	@Override
	public boolean isSupport(Name key) {
		return nameSet.contains(key);
	}

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
		Objects.requireNonNull(dirFile, "入口参数 dirFile 不能为 null。");
		
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
	 * @see com.dwarfeng.tp.core.model.vim.MutilangModel#getDefaultString()
	 */
	@Override
	public String getDefaultString() {
		return defaultString;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.vim.MutilangModel#setDefaultString(java.lang.String)
	 */
	@Override
	public boolean setDefaultString(String defaultString) {
		Objects.requireNonNull(defaultString, "入口参数 defaultString 不能为 null。");
		
		if(this.defaultString.equals(defaultString)) return false;
		String oldOne = this.defaultString;
		this.defaultString = defaultString;
		fireDefaultStringChanged(oldOne, defaultString);
		return true;
	}
	

	private void fireDefaultStringChanged(String oldOne, String newOne) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDefaultStringChanged(oldOne, newOne);
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
		return delegate.isEmpty() && defaultString.isEmpty();
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
			if(Objects.nonNull(obverser)) obverser.fireLocaleAdded(locale, info);
		}
	}

	private void fireInfoChanged(Locale locale, MutilangInfo oldOne, MutilangInfo newOne) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireInfoChanged(locale, oldOne, newOne);
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
			if(Objects.nonNull(obverser)) obverser.fireLocaleRemoved(locale);
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
