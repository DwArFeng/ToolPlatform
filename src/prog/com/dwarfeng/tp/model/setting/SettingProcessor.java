package com.dwarfeng.tp.model.setting;

import java.util.Locale;
import java.util.Objects;

import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.dutil.develop.cfg.ConfigModelObverser;
import com.dwarfeng.dutil.develop.cfg.DefaultConfigModel;
import com.dwarfeng.tp.control.ToolPlatform;
import com.dwarfeng.tp.model.setting.cfg.AppearanceConfig;
import com.dwarfeng.tp.model.setting.cfg.ProgramConfig;
import com.dwarfeng.tp.model.setting.sub.International;
import com.dwarfeng.tp.model.setting.sub.StringField;

/**
 * 对于设置以及属性的处理器。
 * @author  DwArFeng
 * @since 1.8
 */
public final class SettingProcessor {
	
	private final ConfigModel appearanceConfigModel = new DefaultConfigModel(AppearanceConfig.values());
	
	
	
	
	
	/*
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 *                                                                       Program Config Model
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	private final ConfigModel programConfigModel = new DefaultConfigModel(ProgramConfig.values());
	
	/**
	 * 国际化接口。
	 */
	private final International international = new International() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.setting.sub.International#getSupportLocales()
		 */
		@Override
		public Locale[] getSupportLocales() {
			Objects.requireNonNull(ToolPlatform.SUPPORTED_LOCALES, "ToolPlatform.SUPPORTED_LOCALES 为 null，检查程序的正确性");
			return null;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.setting.sub.International#getString(com.dwarfeng.tp.model.setting.sub.StringField, java.util.Locale)
		 */
		@Override
		public String getString(StringField stringField, Locale locale) {
			// TODO Auto-generated method stub
			return null;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.setting.sub.International#getString(com.dwarfeng.tp.model.setting.sub.StringField)
		 */
		@Override
		public String getString(StringField stringField) {
			// TODO Auto-generated method stub
			return null;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.setting.sub.International#getCurrentLocale()
		 */
		@Override
		public Locale getCurrentLocale() {
			// TODO Auto-generated method stub
			return null;
		}
	};

	/**
	 * @return the international
	 */
	public International getInternational() {
		return international;
	}
	
	
	
	/**
	 * 新实例。
	 */
	public SettingProcessor() {
		//programConfigModel.addObverser(programConfigBridge);
	}

}
