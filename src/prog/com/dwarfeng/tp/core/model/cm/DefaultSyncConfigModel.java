package com.dwarfeng.tp.core.model.cm;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;

/**
 * 默认同步配置模型。
 * <p> 配置模型的线程安全的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author DwArFeng
 * @since 1.8
 */
public class DefaultSyncConfigModel extends AbstractSyncConfigModel {

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsKey(ConfigKey configKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCurrentValue(ConfigKey configKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<ConfigKey> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(ConfigEntry configEntry) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<ConfigEntry> configEntries) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(ConfigKey configKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<ConfigKey> configKeys) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<ConfigKey> configKeys) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isValueValid(ConfigKey configKey, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getValidValue(ConfigKey configKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConfigFirmProps getConfigFirmProps(ConfigKey configKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setConfigFirmProps(ConfigKey configKey, ConfigFirmProps configFirmProps) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setCurrentValue(ConfigKey configKey, String currentValue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setAllCurrentValue(Map<ConfigKey, String> map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resetCurrentValue(ConfigKey configKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resetAllCurrentValue() {
		// TODO Auto-generated method stub
		return false;
	}

}
