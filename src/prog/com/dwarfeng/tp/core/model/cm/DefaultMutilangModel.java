package com.dwarfeng.tp.core.model.cm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.obv.MutilangObverser;
import com.dwarfeng.tp.core.model.struct.DefaultName;
import com.dwarfeng.tp.core.model.struct.MutilangInfo;
import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * 默认多语言模型。
 * <p> 多语言模型接口的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultMutilangModel extends AbstractMutilangModel {
	
	private final Map<Locale, MutilangInfo> delegate = new HashMap<>();
	
	private Set<Name> supportedKeys = new HashSet<>();
	private Locale currentLocale = null;
	private File direction = new File("");
	private Map<Name, String> defaultMutilangMap = new HashMap<>();
	private String defaultValue = "";
	
	private Map<Name, String> mutilangMap = new HashMap<>();

	
	/**
	 * 新实例。
	 */
	public DefaultMutilangModel(){}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.vim.MutilangModel#getDir()
	 */
	@Override
	public File getDirection() {
		lock.readLock().lock();
		try{
			return direction;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.vim.MutilangModel#setDir(java.io.File)
	 */
	@Override
	public boolean setDircetion(File direction) {
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.direction, direction)) return false;
			File oldOne = this.direction;
			this.direction = direction;
			fireDirectionChanged(oldOne, direction);
			return true;
		}finally{
			lock.writeLock().unlock();
		}
	}
	
	private void fireDirectionChanged(File oldOne, File newOne){
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDirectionChanged(oldOne, newOne);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		lock.readLock().lock();
		try{
			return delegate.size();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		lock.readLock().lock();
		try{
			return delegate.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		lock.readLock().lock();
		try{
			return delegate.containsKey(key);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		lock.readLock().lock();
		try{
			return delegate.containsValue(value);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public MutilangInfo get(Object key) {
		lock.readLock().lock();
		try{
			return delegate.get(key);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public MutilangInfo put(Locale key, MutilangInfo value) {
		Objects.requireNonNull(key, "入口参数 key 不能为 null。");
		Objects.requireNonNull(value, "入口参数 value 不能为 null。");
		
		lock.writeLock().lock();
		try{
			if(containsKey(key)){
				MutilangInfo oldOne = get(key);
				fireEntryChanged(key, oldOne, value);
			}else{
				fireEntryAdded(key, value);
			}
			
			return delegate.put(key, value);
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireEntryAdded(Locale locale, MutilangInfo info) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryAdded(locale, info);
		}
	}

	private void fireEntryChanged(Locale locale, MutilangInfo oldOne, MutilangInfo newOne) {
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
		lock.writeLock().lock();
		try{
			if(containsKey(key)){
				fireEntryRemoved((Locale) key);
			}
			return delegate.remove(key);
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireEntryRemoved(Locale locale) {
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
		
		lock.writeLock().lock();
		try{
			for(Map.Entry<? extends Locale, ? extends MutilangInfo> entry : m.entrySet()){
				put(entry.getKey(), entry.getValue());
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		lock.writeLock().lock();
		try{
			fireCleared();
			delegate.clear();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCleared() {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCleared();
		}
	}

	/**
	 * 返回该模型的键集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的键集合。
	 */
	@Override
	public Set<Locale> keySet() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(delegate.keySet());
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * 返回该模型的值集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的值集合。
	 */
	@Override
	public Collection<MutilangInfo> values() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableCollection(delegate.values());
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * 返回该模型的入口集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的入口集合。
	 */
	@Override
	public Set<java.util.Map.Entry<Locale, MutilangInfo>> entrySet() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(delegate.entrySet());
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.MutilangModel#getSupportedKeys()
	 */
	@Override
	public Set<Name> getSupportedKeys() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(supportedKeys)){
				return Collections.unmodifiableSet(new HashSet<>());
			}
			return Collections.unmodifiableSet(supportedKeys);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.MutilangModel#setSupportedKeys(java.util.Set)
	 */
	@Override
	public boolean setSupportedKeys(Set<Name> names) {
		Objects.requireNonNull(names, "入口参数 names 不能为 null。");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.supportedKeys, names)) return false;
			Set<Name> oldOne = this.supportedKeys;
			this.supportedKeys = names;
			fireSupportedKeysChanged(Collections.unmodifiableSet(oldOne), Collections.unmodifiableSet(names));
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireSupportedKeysChanged(Set<Name> oldOne, Set<Name> newOne) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireSupportedKeysChanged(oldOne, newOne);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.MutilangModel#getCurrentLocale()
	 */
	@Override
	public Locale getCurrentLocale() {
		lock.readLock().lock();
		try{
			return this.currentLocale;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.MutilangModel#setCurrentLocale(java.util.Locale)
	 */
	@Override
	public boolean setCurrentLocale(Locale locale) {
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.currentLocale, locale)) return false;
			try{
				return innerSetCurrentLocale(locale);
			}catch (ProcessException e) {
				return false;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.MutilangModel#trySetCurrentLocale(java.util.Locale)
	 */
	@Override
	public boolean trySetCurrentLocale(Locale locale) throws ProcessException {
		lock.writeLock().lock();
		try{
			return innerSetCurrentLocale(locale);
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private boolean innerSetCurrentLocale(Locale locale) throws ProcessException{
		if(Objects.equals(this.currentLocale, locale)) return false;
		
		if(Objects.isNull(locale)){
			Locale oldOne = this.currentLocale;
			this.currentLocale = locale;
			this.mutilangMap = this.defaultMutilangMap;
			fireCurrentLocaleChanged(oldOne, locale);
			return true;
			
		}else{
			File targetFile = new File(this.getDirection(), this.get(locale).getFile());
			FileInputStream in = null;
			
			try{
				in = new FileInputStream(targetFile);
				Properties properties = new Properties();
				properties.load(in);
				Map<Name, String> map = new HashMap<>();
				for(String key : properties.stringPropertyNames()){
					map.put(new DefaultName(key), properties.getProperty(key));
				}
				
				this.mutilangMap = map;
				Locale oldOne = this.currentLocale;
				this.currentLocale = locale;
				fireCurrentLocaleChanged(oldOne, locale);
				return true;
				
			}catch (IOException e) {
				throw new ProcessException(e.getMessage(), e);
			}finally{
				if(Objects.nonNull(in)){
					try {
						in.close();
					} catch (IOException e) {
						throw new ProcessException(e.getMessage(), e);
					}
				}
			}
			
		}
	}

	private void fireCurrentLocaleChanged(Locale oldOne, Locale newOne) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCurrentLocaleChanged(oldOne, newOne);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.MutilangModel#getMutilangMap()
	 */
	@Override
	public Map<Name, String> getMutilangMap() {
		lock.readLock().lock();
		try{
			return this.mutilangMap;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.MutilangModel#getDefaultMutilangMap()
	 */
	@Override
	public Map<Name, String> getDefaultMutilangMap() {
		lock.readLock().lock();
		try{
			return this.defaultMutilangMap;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.MutilangModel#setDefaultMutilangMap(java.util.Map)
	 */
	@Override
	public boolean setDefaultMutilangMap(Map<Name, String> mutilangMap) {
		Objects.requireNonNull(mutilangMap, "入口参数 mutilangMap 不能为 null。");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.defaultMutilangMap, mutilangMap)) return false;
			Map<Name, String> oldOne = this.defaultMutilangMap;
			this.defaultMutilangMap = mutilangMap;
			fireDefaultMutilangMapChanged(Collections.unmodifiableMap(oldOne), Collections.unmodifiableMap(mutilangMap));
			
			if(Objects.isNull(currentLocale)){
				this.mutilangMap = mutilangMap;
			}
			
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireDefaultMutilangMapChanged(Map<Name, String> oldOne, Map<Name, String> newOne) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDefaultMutilangMapChanged(oldOne, newOne);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.MutilangModel#getDefaultValue()
	 */
	@Override
	public String getDefaultValue() {
		lock.readLock().lock();
		try{
			return this.defaultValue;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.MutilangModel#setDefaultValue(java.lang.String)
	 */
	@Override
	public boolean setDefaultValue(String value) {
		Objects.requireNonNull(value, "入口参数 value 不能为 null。");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.defaultValue, value)) return false;
			String oldOne = this.defaultValue;
			this.defaultValue = value;
			fireDefaultValueChanged(oldOne, value);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireDefaultValueChanged(String oldOne, String newOne) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDefaultVauleChanged(oldOne, newOne);
		}
	}

}
