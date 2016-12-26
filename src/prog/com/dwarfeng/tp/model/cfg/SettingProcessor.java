package com.dwarfeng.tp.model.cfg;

/**
 * ���������Լ����ԵĴ�������
 * @author  DwArFeng
 * @since 1.8
 */
public final class SettingProcessor {
//	
//	/*
//	 * -------------------------------------------------------------------------------------------------------------------------------------
//	 * 
//	 *                                                                      Appearance Config Model
//	 * 
//	 * -------------------------------------------------------------------------------------------------------------------------------------
//	 */
//	
//	private final ConfigModel appearanceConfigModel = new DefaultConfigModel(AppearanceConfig.values());
//	
//	
//	
//	
//	
//	/*
//	 * -------------------------------------------------------------------------------------------------------------------------------------
//	 * 
//	 *                                                                       Program Config Model
//	 * 
//	 * -------------------------------------------------------------------------------------------------------------------------------------
//	 */
//	
//	private final ConfigModel programConfigModel = new DefaultConfigModel(ProgramConfig.values());
//	
//	private final ConfigModelObverser programConfigObverser = new ConfigModelAdapter() {
//		
//		/*
//		 * (non-Javadoc)
//		 * @see com.dwarfeng.dutil.develop.cfg.ConfigModelObverser#fireCurrentValueChanged(com.dwarfeng.dutil.develop.cfg.ConfigKey, java.lang.String, java.lang.String, java.lang.String)
//		 */
//		@Override
//		public void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue, String validValue) {
//			//PROGRAM_MUTILANG_LANGUAGE
//			if(configKey.equals(ProgramConfig.PROGRAM_MUTILANG_LANGUAGE.getConfigKey())){
//				if(validValue == null) currentLocale = null;
//				currentLocale = new Locale(validValue);
//				for(MutilangObverser obverser : mutilangObversers){
//					obverser.fireLanguageChanged();
//				}
//			}
//			//
//		}
//	};
//	
//	private final Set<MutilangObverser> mutilangObversers = Collections.newSetFromMap(new WeakHashMap<>());
//	private final Map<Locale, ResourceBundle> mutilangMap;
//	private Locale currentLocale = null;
//	
//	/**
//	 * ���ʻ��ӿڡ�
//	 */
//	private final Mutilang mutilang = new Mutilang() {
//		
//		/*
//		 * (non-Javadoc)
//		 * @see com.dwarfeng.tp.model.setting.Mutilang#getString(com.dwarfeng.tp.model.setting.StringField)
//		 */
//		@Override
//		public String getString(StringKey stringField) {
//			Objects.requireNonNull(stringField, "��ڲ��� stringField ����Ϊ null��");
//			
//			ResourceBundle rb = mutilangMap.getOrDefault(currentLocale, mutilangMap.get(null));
//			return rb.getString(stringField.toString());
//		}
//
//		/*
//		 * (non-Javadoc)
//		 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
//		 */
//		@Override
//		public Set<MutilangObverser> getObversers() {
//			return Collections.unmodifiableSet(mutilangObversers);
//		}
//
//		/*
//		 * (non-Javadoc)
//		 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
//		 */
//		@Override
//		public boolean addObverser(MutilangObverser obverser) {
//			return mutilangObversers.add(obverser);
//		}
//
//		/*
//		 * (non-Javadoc)
//		 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
//		 */
//		@Override
//		public boolean removeObverser(MutilangObverser obverser) {
//			return mutilangObversers.remove(obverser);
//		}
//
//		/*
//		 * (non-Javadoc)
//		 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#clearObverser()
//		 */
//		@Override
//		public void clearObverser() {
//			mutilangObversers.clear();
//		}
//		
//	};
//	
//	
//	/**
//	 * �µ�ʵ����
//	 * @param mutilangMap ���ʻ�ӳ�䡣
//	 */
//	public SettingProcessor(Map<Locale, ResourceBundle> mutilangMap) {
//		Objects.requireNonNull(mutilangMap, "��ڲ��� mutilangMap ����Ϊ null");
//		
//		this.mutilangMap = mutilangMap;
//		
//		this.programConfigModel.addObverser(programConfigObverser);
//	}
//
//
//	/**
//	 * @return the mutilang
//	 */
//	public Mutilang getMutilang() {
//		return mutilang;
//	}
//
//
}
