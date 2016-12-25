package com.dwarfeng.tp.model;

import java.util.Objects;

import com.dwarfeng.tp.model.setting.SettingProcessor;

/**
 * 程序的模型管理器。
 * @author DwArFeng
 * @since 1.8
 */
public final class ModelManager {
	
	private final SettingProcessor settingProcessor;
	
	/**
	 * 新实例。
	 */
	public ModelManager(SettingProcessor settingProcessor) {
		Objects.requireNonNull(settingProcessor, "入口参数 settingProcessor 不能为 null。");
		
		this.settingProcessor = settingProcessor;
	}

	/**
	 * @return the settingProcessor
	 */
	public SettingProcessor getSettingProcessor() {
		return settingProcessor;
	}

}
