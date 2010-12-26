package com.dwarfeng.tp.model.cfg;

import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigModelObverser;

/**
 * ����ģ����Ӧ����
 * <p> ����Ӧ����д�ڱ����������ù۲����в���Ҫʵ�ֵķ������Խ�ʡ��������
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
