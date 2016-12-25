package com.dwarfeng.tp.model;

import java.util.Objects;

import com.dwarfeng.tp.model.setting.SettingProcessor;

/**
 * �����ģ�͹�������
 * @author DwArFeng
 * @since 1.8
 */
public final class ModelManager {
	
	private final SettingProcessor settingProcessor;
	
	/**
	 * ��ʵ����
	 */
	public ModelManager(SettingProcessor settingProcessor) {
		Objects.requireNonNull(settingProcessor, "��ڲ��� settingProcessor ����Ϊ null��");
		
		this.settingProcessor = settingProcessor;
	}

	/**
	 * @return the settingProcessor
	 */
	public SettingProcessor getSettingProcessor() {
		return settingProcessor;
	}

}
