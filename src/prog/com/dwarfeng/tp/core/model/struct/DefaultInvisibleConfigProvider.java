package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;

import com.dwarfeng.tp.core.model.cfg.InvisibleConfig;
import com.dwarfeng.tp.core.model.cm.SyncConfigModel;

/**
 * 默认不可见配置提供器。
 * <p> 不可见配置提供器的默认实现。
 * @author  DwArFeng
 * @since 1.8
 */
public class DefaultInvisibleConfigProvider implements InvisibleConfigProvider {
	
	private final SyncConfigModel configModel;

	public DefaultInvisibleConfigProvider(SyncConfigModel configModel) {
		Objects.requireNonNull(configModel, "入口参数 configModel 不能为 null。");
		this.configModel = configModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.InvisibleConfigProvider#getConfigModel()
	 */
	@Override
	public SyncConfigModel getConfigModel() {
		return configModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.InvisibleConfigProvider#getMainFrameStartupHeight()
	 */
	@Override
	public int getMainFrameStartupHeight() {
		if(! configModel.containsKey(InvisibleConfig.STARTUP_MAINFRAME_HEIGHT.getConfigKey())){
			throw new IllegalStateException("与之关联模型中没有找到指定的键");
		}
		return Integer.parseInt(configModel.getValidValue(InvisibleConfig.STARTUP_MAINFRAME_HEIGHT.getConfigKey()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.InvisibleConfigProvider#getMainFrameStartupWidth()
	 */
	@Override
	public int getMainFrameStartupWidth() {
		if(! configModel.containsKey(InvisibleConfig.STARTUP_MAINFRAME_WIDTH.getConfigKey())){
			throw new IllegalStateException("与之关联模型中没有找到指定的键");
		}
		return Integer.parseInt(configModel.getValidValue(InvisibleConfig.STARTUP_MAINFRAME_WIDTH.getConfigKey()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.InvisibleConfigProvider#getMainFrameStartupExtendedState()
	 */
	@Override
	public int getMainFrameStartupExtendedState() {
		if(! configModel.containsKey(InvisibleConfig.STARTUP_MAINFRAME_EXTENDEDSTATE.getConfigKey())){
			throw new IllegalStateException("与之关联模型中没有找到指定的键");
		}
		return Integer.parseInt(configModel.getValidValue(InvisibleConfig.STARTUP_MAINFRAME_EXTENDEDSTATE.getConfigKey()));
	}
	
	
	

}
