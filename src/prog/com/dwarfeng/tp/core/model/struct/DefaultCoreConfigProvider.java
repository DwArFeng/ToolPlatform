package com.dwarfeng.tp.core.model.struct;

import java.util.Locale;
import java.util.Objects;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.util.LocaleUtil;

/**
 * Ĭ�Ϻ��������ṩ����
 * <p> ���������ṩ����Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 1.8
 */
public final class DefaultCoreConfigProvider implements CoreConfigProvider{

	private final ConfigModel configModel;

	public DefaultCoreConfigProvider(ConfigModel configModel) {
		Objects.requireNonNull(configModel, "��ڲ��� configModel ����Ϊ null��");
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
		if(configModel.containsKey(CoreConfig.MUTILANG_LOGGER.getConfigKey())){
			throw new IllegalStateException("��֮����ģ����û���ҵ�ָ���ļ�");
		}
		return LocaleUtil.parseLocale(configModel.getValidValue(CoreConfig.MUTILANG_LOGGER.getConfigKey()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.CoreConfigProvider#getLabelMutilangLocale()
	 */
	@Override
	public Locale getLabelMutilangLocale() {
		if(configModel.containsKey(CoreConfig.MUTILANG_LABEL.getConfigKey())){
			throw new IllegalStateException("��֮����ģ����û���ҵ�ָ���ļ�");
		}
		return LocaleUtil.parseLocale(configModel.getValidValue(CoreConfig.MUTILANG_LABEL.getConfigKey()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.CoreConfigProvider#isShowSplashScreen()
	 */
	@Override
	public boolean isShowSplashScreen() {
		if(configModel.containsKey(CoreConfig.STARTUP_SPLASH_ISSHOW.getConfigKey())){
			throw new IllegalStateException("��֮����ģ����û���ҵ�ָ���ļ�");
		}
		return Boolean.parseBoolean(configModel.getValidValue(CoreConfig.STARTUP_SPLASH_ISSHOW.getConfigKey()));
	}
	
	

}
