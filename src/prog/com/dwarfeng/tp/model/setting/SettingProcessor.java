package com.dwarfeng.tp.model.setting;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.WeakHashMap;

import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.dutil.develop.cfg.ConfigModelObverser;
import com.dwarfeng.dutil.develop.cfg.DefaultConfigModel;

/**
 * 对于设置以及属性的处理器。
 * @author  DwArFeng
 * @since 1.8
 */
public final class SettingProcessor {
	
	/*
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 *                                                                      Appearance Config Model
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	private final ConfigModel appearanceConfigModel = new DefaultConfigModel(AppearanceConfig.values());
	
	
	
	
	
	/*
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 *                                                                       Program Config Model
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	private final ConfigModel programConfigModel = new DefaultConfigModel(ProgramConfig.values());
	
	private final ConfigModelObverser programConfigObverser = new ConfigModelAdapter() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dutil.develop.cfg.ConfigModelObverser#fireCurrentValueChanged(com.dwarfeng.dutil.develop.cfg.ConfigKey, java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue, String validValue) {
			//PROGRAM_INTERNATIONAL_LANGUAGE
			if(configKey.equals(ProgramConfig.PROGRAM_INTERNATIONAL_LANGUAGE.getConfigKey())){
				if(validValue == null) currentLocale = null;
				currentLocale = new Locale(validValue);
				for(InternationalObverser obverser : internationalObversers){
					obverser.fireLanguageChanged();
				}
			}
			//
		}
	};
	
	private final Set<InternationalObverser> internationalObversers = Collections.newSetFromMap(new WeakHashMap<>());
	private final Map<Locale, ResourceBundle> internationalMap;
	private Locale currentLocale = null;
	
	/**
	 * 国际化接口。
	 */
	private final International international = new International() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.setting.sub.International#getString(com.dwarfeng.tp.model.setting.sub.StringField)
		 */
		@Override
		public String getString(StringField stringField) {
			Objects.requireNonNull(stringField, "入口参数 stringField 不能为 null。");
			
			ResourceBundle rb = internationalMap.getOrDefault(currentLocale, internationalMap.get(null));
			return rb.getString(stringField.toString());
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
		 */
		@Override
		public Set<InternationalObverser> getObversers() {
			return Collections.unmodifiableSet(internationalObversers);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
		 */
		@Override
		public boolean addObverser(InternationalObverser obverser) {
			return internationalObversers.add(obverser);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
		 */
		@Override
		public boolean removeObverser(InternationalObverser obverser) {
			return internationalObversers.remove(obverser);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#clearObverser()
		 */
		@Override
		public void clearObverser() {
			internationalObversers.clear();
		}
		
	};
	
	
	/**
	 * 新的实例。
	 * @param internationalMap 国际化映射。
	 */
	public SettingProcessor(Map<Locale, ResourceBundle> internationalMap) {
		Objects.requireNonNull(internationalMap, "入口参数 internationalMap 不能为 null");
		
		this.internationalMap = internationalMap;
		
		this.programConfigModel.addObverser(programConfigObverser);
	}

	/**
	 * @return the international
	 */
	public International getInternational() {
		return international;
	}

}
