package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.cm.MutilangModel;

/**
 * 默认多语言接口提供器。
 * <p> 多语言提供器接口的默认实现。
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultMutilangProvider implements MutilangProvider {
	
	private final MutilangModel mutilangModel;
	
	private final Mutilang mutilang = new Mutilang() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
		 */
		@Override
		public String getString(Name key) {
			// TODO Auto-generated method stub
			return null;
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
