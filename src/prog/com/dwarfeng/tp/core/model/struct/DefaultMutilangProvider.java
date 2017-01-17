package com.dwarfeng.tp.core.model.struct;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.cm.MutilangModel;

/**
 * 默认多语言接口提供器。
 * <p> 多语言提供器接口的默认实现。
 * @author  DwArFeng
 * @since 1.8
 */
@SuppressWarnings("unused")
public final class DefaultMutilangProvider implements MutilangProvider {
	
	private final MutilangModel mutilangModel;
	
	private final Mutilang mutilang = new Mutilang() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
		 */
		@Override
		public String getString(String key) {
			Objects.requireNonNull(key, "入口参数 key 不能为 null。");
			
			Map<String, String> mutilangMap;
			String defaultValue;
			ReadWriteLock lock = mutilangModel.getLock();
			
			lock.readLock().lock();
			try{
				if(!mutilangModel.getSupportedKeys().contains(key)){
					throw new IllegalArgumentException("该多语言接口不支持此键");
				}
				mutilangMap = new HashMap<>(mutilangModel.getMutilangMap());
				defaultValue = mutilangModel.getDefaultValue();
			}finally {
				lock.readLock().unlock();
			}
			
			return mutilangMap.getOrDefault(key, defaultValue);
		}
	};
	
	/**
	 * 新实例。
	 * @param mutilangModel 指定的多语言模型。
	 * @param supportedKeys 指定的受支持的键值集合。
	 */
	public DefaultMutilangProvider(MutilangModel mutilangModel) {
		Objects.requireNonNull(mutilangModel, "入口参数 mutilangModel 不能为 null。");
		this.mutilangModel = mutilangModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangProvider#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		return this.mutilang;
	}
}
