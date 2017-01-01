package com.dwarfeng.tp.core.model.struct;

import java.util.Locale;
import java.util.Objects;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.util.LocaleUtil;

/**
 * 默认核心配置提供器。
 * <p> 核心配置提供器的默认实现。
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultCoreConfigProvider implements CoreConfigProvider{

	private final ConfigModel configModel;

	public DefaultCoreConfigProvider(ConfigModel configModel) {
		Objects.requireNonNull(configModel, "入口参数 configModel 不能为 null。");
		this.configModel = configModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.CoreConfigProvider#getConfigModel()
	 */
	@Override
	public ConfigModel getConfigModel() {
		return this.configModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.CoreConfigProvider#getLoggerMutilangLocale()
	 */
	@Override
	public Locale getLoggerMutilangLocale() {
		return LocaleUtil.parseLocale(configModel.getValidValue(CoreConfig.MUTILANG_LOGGER.getConfigKey()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.CoreConfigProvider#getLabelMutilangLocale()
	 */
	@Override
	public Locale getLabelMutilangLocale() {
		return LocaleUtil.parseLocale(configModel.getValidValue(CoreConfig.MUTILANG_LABEL.getConfigKey()));
	}
	
	

}
