package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;

/**
 * 默认不可见配置提供器。
 * <p> 不可见配置提供器的默认实现。
 * @author  DwArFeng
 * @since 1.8
 */
public class DefaultInvisibleConfigProvider implements InvisibleConfigProvider {
	
	private final ConfigModel configModel;

	public DefaultInvisibleConfigProvider(ConfigModel configModel) {
		Objects.requireNonNull(configModel, "入口参数 configModel 不能为 null。");
		this.configModel = configModel;
	}
	
	
	

}
