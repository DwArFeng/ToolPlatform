package com.dwarfeng.tp.core.model.struct;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.cm.MutilangModel;

/**
 * 默认多语言接口提供器。
 * <p> 多语言提供器接口的默认实现。
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultMutilangProvider implements MutilangProvider {
	
	private final String defaultString;
	private final MutilangModel mutilangModel;
	private final Set<Name> supportedKeys;
	private final Map<String, String> defaultMap;
	private final InnerMutilang mutilang = new InnerMutilang();
	
	/**
	 * 新实例。
	 * @param mutilangModel 指定的多语言模型。
	 * @param supportedKeys 指定的受支持的键值集合。
	 */
	public DefaultMutilangProvider(
			MutilangModel mutilangModel,
			Set<Name> supportedKeys, 
			Map<String, String> defaultMap,
			String defaultString) {
		Objects.requireNonNull(mutilangModel, "入口参数 mutilangModel 不能为 null。");
		Objects.requireNonNull(supportedKeys, "入口参数 supportedKeys 不能为 null。");
		Objects.requireNonNull(defaultMap, "入口参数 defaultMap 不能为 null。");
		Objects.requireNonNull(defaultString, "入口参数 defaultString 不能为 null。");
		
		this.mutilangModel = mutilangModel;
		this.supportedKeys = supportedKeys;
		this.defaultMap = defaultMap;
		this.defaultString = defaultString;
		
		this.mutilang.map = defaultMap;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangProvider#getMutilangModel()
	 */
	@Override
	public MutilangModel getMutilangModel() {
		return mutilangModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangProvider#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		return mutilang;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangProvider#getSupportedKeys()
	 */
	@Override
	public Set<Name> getSupportedKeys() {
		return Collections.unmodifiableSet(supportedKeys);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangProvider#isSupport(com.dwarfeng.dutil.basic.str.Name)
	 */
	@Override
	public boolean isSupport(Name key) {
		return supportedKeys.contains(key);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangProvider#update2Default()
	 */
	@Override
	public void update2Default() {
		mutilang.map = defaultMap;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangProvider#update(java.util.Locale)
	 */
	@Override
	public void update(Locale locale) throws ProcessException {
		if(locale == null){
			update2Default();
			return;
		}
		
		if(! this.mutilangModel.containsKey(locale)){
			throw new ProcessException("该多语言提供器不支持指定的语言");
		}
		
		File targetFile = new File(this.mutilangModel.getDirFile(), this.mutilangModel.get(locale).getFilePath());
		FileInputStream in = null;
		
		try{
			in = new FileInputStream(targetFile);
			Properties properties = new Properties();
			properties.load(in);
			Map<String, String> map = new HashMap<>();
			for(String key : properties.stringPropertyNames()){
				map.put(key, properties.getProperty(key));
			}
			this.mutilang.map = map;
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
	
	
	
	private final class InnerMutilang implements Mutilang{
		
		Map<String, String> map;
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
		 */
		@Override
		public String getString(Name key) {
			if(! isSupport(key)){
				throw new IllegalArgumentException("此多语言接口不支持该键");
			}
			return map.getOrDefault(key.getName(), defaultString);
		}
		
	}

}
