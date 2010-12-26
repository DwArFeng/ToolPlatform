package com.dwarfeng.tp.model.cfg;

import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigModelObverser;

/**
 * 配置模型适应器。
 * <p> 该适应器重写在本程序中配置观察器中不需要实现的方法，以节省代码量。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class ConfigModelAdapter implements ConfigModelObverser{

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModelObverser#fireConfigFirmPropsChanged(com.dwarfeng.dutil.develop.cfg.ConfigKey, com.dwarfeng.dutil.develop.cfg.ConfigFirmProps, com.dwarfeng.dutil.develop.cfg.ConfigFirmProps)
	 */
	@Override
	public void fireConfigFirmPropsChanged(ConfigKey configKey, ConfigFirmProps oldValue, ConfigFirmProps newValue) {}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModelObverser#fireConfigKeyCleared()
	 */
	@Override
	public void fireConfigKeyCleared() {}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModelObverser#fireConfigKeyRemoved(com.dwarfeng.dutil.develop.cfg.ConfigKey)
	 */
	@Override
	public void fireConfigKeyRemoved(ConfigKey configKey) {}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModelObverser#fireConfigKeyAdded(com.dwarfeng.dutil.develop.cfg.ConfigKey)
	 */
	@Override
	public void fireConfigKeyAdded(ConfigKey configKey) {}
	
}
