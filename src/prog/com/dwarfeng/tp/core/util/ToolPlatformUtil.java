package com.dwarfeng.tp.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.dutil.develop.cfg.ConfigUtil;
import com.dwarfeng.dutil.develop.cfg.DefaultConfigModel;
import com.dwarfeng.dutil.develop.cfg.io.PropertiesConfigLoader;
import com.dwarfeng.dutil.develop.cfg.io.StreamConfigLoader;
import com.dwarfeng.tp.core.control.ToolPlatform;
import com.dwarfeng.tp.core.control.proc.Initializer;
import com.dwarfeng.tp.core.model.ModelManager;
import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.cfg.PathKey;
import com.dwarfeng.tp.core.model.struct.ConfigChangeFailedException;
import com.dwarfeng.tp.core.model.struct.DefaultMutilangProvider;
import com.dwarfeng.tp.core.model.struct.DefaultResource;
import com.dwarfeng.tp.core.model.struct.InitializeException;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangProvider;
import com.dwarfeng.tp.core.model.struct.Resource;
import com.dwarfeng.tp.core.model.vim.MutilangModel;
import com.dwarfeng.tp.core.view.ViewManager;

/**
 * 关于工具平台的工厂类。
 * @author  DwArFeng
 * @since 1.8
 */
public final class ToolPlatformUtil {
	
	private final static String missingString = "!文本缺失";
	private final static ResourceBundle loggerMutilangResourceBundle = ResourceBundle.getBundle(
			"com.dwarfeng.tp.resource.defaultres.mutilang.logger.default");

	
	/**
	 * 获取初始化用多语言接口。
	 * <p> 该多语言接口是程序在初始化阶段，尚未通过配置生成专用的多语言接口提供器之前，
	 * 用于代替的多语言接口，该方法生成的多语言接口只会在程序在初始化的前期保留一段时间。
	 * <p> 使用简体中文，并且不响应设置语言方法和将语言设置为默认值方法。
	 * @return 新的初始化用多语言接口。
	 */
	public final static Mutilang newInitialLoggerMutilang(){
		return new InitialLoggerMutilang();
	}
	
	/**
	 * 通过指定的多语言模型生成一个新的记录器多语言提供器。
	 * @param mutilangModel 指定的多语言模型。
	 * @return 通过指定的多语言模型生成的记录器多语言提供器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public final static MutilangProvider newLoggerMutilangProvider(MutilangModel mutilangModel){
		Objects.requireNonNull(mutilangModel, "入口参数 mutilangModel 不能为 null。");
		
		Map<String, String> map = ResourceBundleUtil.toMap(loggerMutilangResourceBundle);
		return new DefaultMutilangProvider(
				mutilangModel, 
				new HashSet<>(Arrays.asList(LoggerStringKey.values())), 
				ResourceBundleUtil.toMap(loggerMutilangResourceBundle),
				missingString);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 
	 * 
	 * 
	 */
	
	/**
	 * 生成一个新的初始化器。
	 * @return 新的初始化器。
	 */
	public final static Initializer newDefaultInitializer(){
		return new DefaultInitializer();
	}

	/**
	 * 默认的程序初始化器。
	 * <p> 执行程序中默认的初始化动作。
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private static final class DefaultInitializer implements Initializer{
		
		private final String missingLabel = "!字段缺失";
		
		private Logger usingLogger = null;
		private Mutilang<LoggerStringKey> usingMutilang = null;
		
		private ModelManager modelManager = null;
		private ViewManager viewManager = null;
		
		
		public DefaultInitializer(){
			this(new EmptyPlatformLogger(), new InitialLoggerMutilang());
		}
		
		public DefaultInitializer(Logger usingLogger, Mutilang<LoggerStringKey> usingMutilang){
			Objects.requireNonNull(usingLogger, "入口参数 usingLogger 不能为 null。");
			Objects.requireNonNull(usingMutilang, "入口参数 usingMutilang 不能为 null。");

			this.usingLogger = usingLogger;
			this.usingMutilang = usingMutilang;
		}
		
		/**程序中的资源读取器。*/
		private final ResourceLoader resourceLoader = new ResourceLoader() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.ToolPlatform.Attributes.InnerInitializer.ResourceLoader#loadResources()
			 */
			@Override
			public Map<String, Resource> loadResources() throws InitializeException {
				try{
					usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_10));
					
					URL path = ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/paths.xml");
					SAXReader reader = new SAXReader();
					Element root = reader.read(path).getRootElement();
					
					Map<String, Resource> resourceMap = new HashMap<>();
					
					/*
					 * 根据 dom4j 的相关说明，此处转换是安全的。
					 */
					@SuppressWarnings("unchecked")
					List<Element> infos = (List<Element>)root.elements("info");
					
					for(Element info : infos){
						String defString = info.attributeValue("default");
						String resString = info.attributeValue("path");
						String key = info.attributeValue("key");
						
						if(Objects.isNull(defString) || Objects.isNull(resString) || Objects.isNull(key)) {
							throw new InitializeException(usingMutilang.getString(LoggerStringKey.Initializer_11));
						}
						
						URL def = ToolPlatform.class.getResource(defString);
						
						if(Objects.isNull(def)){
							throw new InitializeException(usingMutilang.getString(LoggerStringKey.Initializer_12));
						}
						
						File res = new File(resString);
						
						resourceMap.put(key, new DefaultResource(def, res));
					}
					
					return resourceMap;
					
				}catch (DocumentException | InitializeException e) {
					InitializeException ee = new InitializeException(
							usingMutilang.getString(LoggerStringKey.Initializer_13), 
							usingMutilang.getString(LoggerStringKey.Initializer_14),
							e.getMessage(),
							e);
					usingLogger.fatal(ee.getDialogTitle(), ee);
					throw ee;
				}
			}
		};
		
		/**程序中的记录器的生成器*/
		private final Generator<Logger> loggerGenerator = new Generator<Logger>() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.control.ProgramAttributes.InnerInitializer.Generator#newInstance(java.util.Map)
			 */
			@Override
			public Logger newInstance(Map<String, Resource> resourceMap) throws InitializeException {
				Objects.requireNonNull(resourceMap, "入口参数 resourceMap 不能为 null。");
				
				try{
					usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_1));
					
					InputStream in0 = getResourceInput(resourceMap, PathKey.LOGGER_SETTING);
					InputStream in1 = getResourceInput(resourceMap, PathKey.LOGGER_SETTING);
					
					SAXReader reader = new SAXReader();
					Element root = null;
					
					try{
						root = reader.read(in0).getRootElement();
					}finally{
						if(Objects.nonNull(in0)){
							in0.close();
						}
					}
					
					Set<String> loggerNames = new HashSet<>();
					
					Element loggers = root.element("Loggers");
	
					/*
					 * 根据 dom4j 的相关说明，此处转换是安全的。
					 */
					@SuppressWarnings("unchecked")
					List<Element> perhapsLoggers = (List<Element>)loggers.elements("Logger");
					for(Element perhapsLogger : perhapsLoggers){
						Attribute attribute = perhapsLogger.attribute("name");
						if(Objects.nonNull(attribute)){
							String name = attribute.getStringValue();
							loggerNames.add(name);
							usingLogger.info(String.format(usingMutilang.getString(LoggerStringKey.Initializer_2), name));
						}
					}
					
					ConfigurationSource configurationSource = null;
					try{
						configurationSource = new ConfigurationSource(in1);
					}finally{
						in1.close();
					}
					
					LoggerContext loggerContext =  Configurator.initialize(null, configurationSource);	
					
					Set<Logger> loggerSet = new HashSet<>();
					
					for(String loggerName : loggerNames){
						loggerSet.add(loggerContext.getLogger(loggerName));
					}
					
					usingLogger.stop();
					usingLogger = new DefaultPlatformLogger(loggerSet, loggerContext);
					usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_3));
					
					return usingLogger;
					
				}catch (IOException | DocumentException e) {
					InitializeException ee = new InitializeException(
							usingMutilang.getString(LoggerStringKey.Initializer_4), 
							usingMutilang.getString(LoggerStringKey.Initializer_15),
							e.getMessage(),
							e);
					usingLogger.fatal(ee.getDialogTitle(), ee);
					throw ee;
				}
	
			}
	
		};
		
		/**程序配置读取器*/
		private final Generator<ConfigModel> coreConfigGenerator = new Generator<ConfigModel>() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.control.ProgramAttributes.InnerInitializer.Generator#newInstance(java.util.Map)
			 */
			@Override
			public ConfigModel newInstance(Map<String, Resource> resourceMap) throws InitializeException {
				Objects.requireNonNull(resourceMap, "入口参数 resourceMap 不能为 null。");
				
				try{
					usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_8));
					
					InputStream in = getResourceInput(resourceMap, PathKey.CONFIGURATION_CORE);
					
					ConfigModel model = ConfigUtil.unmodifiableConfigModel(new DefaultConfigModel(CoreConfig.values()));
					StreamConfigLoader loader = new PropertiesConfigLoader(in);
					
					try {
						usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_6));
						loader.loadConfig(model);
					} catch (LoadFailedException e) {
						e.printStackTrace();
					}finally{
						loader.close();
					}
					
					usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_9));
					
					return model;
					
				}catch (IOException e) {
					InitializeException ee = new InitializeException(
							usingMutilang.getString(LoggerStringKey.Initializer_26), 
							usingMutilang.getString(LoggerStringKey.Initializer_16),
							e.getMessage(),
							e);
					ee.setStackTrace(e.getStackTrace());
					usingLogger.fatal(ee.getDialogTitle(), ee);
					throw ee;
				}
			}
		};
		
		/**记录器多语言化读取器*/
		private final Generator<Mutilang<LoggerStringKey>> loggerMutilangGenerator = new Generator<Mutilang<LoggerStringKey>>() {
	
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.control.ProgramAttributes.InnerInitializer.Generator#newInstance(java.util.Map)
			 */
			@Override
			public Mutilang<LoggerStringKey> newInstance(Map<String, Resource> resourceMap)throws InitializeException {
				Objects.requireNonNull(resourceMap, "入口参数 resourceMap 不能为 null。");
				
				try{
					usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_17));
					
					InputStream in = getResourceInput(resourceMap, PathKey.MUTILANG_LOGGER_SETTING);
					
					SAXReader reader = new SAXReader();
					
					Element root = null;
					try{
						usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_18));
						root = reader.read(in).getRootElement();
					}finally {
						in.close();
					}
					
					String rootDirStr = root.attributeValue("dir");
					if(Objects.isNull(rootDirStr)){
						throw new InitializeException(usingMutilang.getString(LoggerStringKey.Initializer_19));
					}
					
					File rootDir = new File(rootDirStr);
					Map<Locale, URL> urlMap = new HashMap<>();
					
					/*
					 * 根据 dom4j 的相关说明，此处转换是安全的。
					 * 	<!--使用如下的格式：<info language="zh" country="CN" variant="" label="简体中文" file="zh_CN.properties"></info>-->
					 */
					@SuppressWarnings("unchecked")
					List<Element> mutilangInfos = (List<Element>)root.elements("info");
					for(Element mutilangInfo : mutilangInfos){
						String language = mutilangInfo.attributeValue("language");
						String country = mutilangInfo.attributeValue("country");
						String variant = mutilangInfo.attributeValue("variant");
						String label = mutilangInfo.attributeValue("label");
						String fileName = mutilangInfo.attributeValue("file");
						
						if(
								Objects.nonNull(language) &&
								Objects.nonNull(country) &&
								Objects.nonNull(variant) &&
								Objects.nonNull(label) &&
								Objects.nonNull(fileName)
								){
							Locale locale = new Locale(language, country, variant);
							usingLogger.info(String.format(usingMutilang.getString(LoggerStringKey.Initializer_20), locale.toString()));
							File file = new File(rootDir, fileName);
							urlMap.put(locale, file.toURI().toURL());
						}
					}
					
					ResourceBundle resourceBundle = ResourceBundle.getBundle(
							"com.dwarfeng.tp.resource.defaultres.mutilang.logger.default");
					
					DefaultMutilang<LoggerStringKey> loggerMutiLang = new DefaultMutilang<>(loggerMutilangResourceBundle, urlMap, missingLabel);
					usingMutilang = loggerMutiLang;
					
					usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_21));
					return loggerMutiLang;
					
				}catch (IOException | DocumentException e) {
					InitializeException ee = new InitializeException(
							usingMutilang.getString(LoggerStringKey.Initializer_27), 
							usingMutilang.getString(LoggerStringKey.Initializer_28),
							e.getMessage(),
							e);
					usingLogger.fatal(ee.getDialogTitle(), ee);
					throw ee;
				}
			}
			
		};
		
		/**标签多语言化读取器*/
		private final Generator<Mutilang<LabelStringKey>> labelMutilangGenerator = new Generator<Mutilang<LabelStringKey>>() {
	
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.control.ProgramAttributes.InnerInitializer.Generator#newInstance(java.util.Map)
			 */
			@Override
			public Mutilang<LabelStringKey> newInstance(Map<String, Resource> resourceMap)throws InitializeException {
				Objects.requireNonNull(resourceMap, "入口参数 resourceMap 不能为 null。");
				
				try{
					usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_24));
					
					InputStream in = getResourceInput(resourceMap, PathKey.MUTILANG_LABEL_SETTING);
					
					SAXReader reader = new SAXReader();
					
					Element root = null;
					try{
						usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_29));
						root = reader.read(in).getRootElement();
					}finally {
						in.close();
					}
					
					String rootDirStr = root.attributeValue("dir");
					if(Objects.isNull(rootDirStr)){
						throw new InitializeException(usingMutilang.getString(LoggerStringKey.Initializer_30));
					}
					
					File rootDir = new File(rootDirStr);
					Map<Locale, URL> urlMap = new HashMap<>();
					
					/*
					 * 根据 dom4j 的相关说明，此处转换是安全的。
					 * 	<!--使用如下的格式：<info language="zh" country="CN" variant="" label="简体中文" file="zh_CN.properties"></info>-->
					 */
					@SuppressWarnings("unchecked")
					List<Element> mutilangInfos = (List<Element>)root.elements("info");
					for(Element mutilangInfo : mutilangInfos){
						String language = mutilangInfo.attributeValue("language");
						String country = mutilangInfo.attributeValue("country");
						String variant = mutilangInfo.attributeValue("variant");
						String label = mutilangInfo.attributeValue("label");
						String fileName = mutilangInfo.attributeValue("file");
						
						if(
								Objects.nonNull(language) &&
								Objects.nonNull(country) &&
								Objects.nonNull(variant) &&
								Objects.nonNull(label) &&
								Objects.nonNull(fileName)
								){
							Locale locale = new Locale(language, country, variant);
							usingLogger.info(String.format(usingMutilang.getString(LoggerStringKey.Initializer_31), locale.toString()));
							File file = new File(rootDir, fileName);
							urlMap.put(locale, file.toURI().toURL());
						}
					}
					
					ResourceBundle resourceBundle = ResourceBundle.getBundle(
							"com.dwarfeng.tp.resource.defaultres.mutilang.logger.default");
					
					DefaultMutilang<LoggerStringKey> loggerMutiLang = new DefaultMutilang<>(loggerMutilangResourceBundle, urlMap, missingLabel);
					usingMutilang = loggerMutiLang;
					
					usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_32));
					return null;
					
				}catch (IOException | DocumentException e) {
					InitializeException ee = new InitializeException(
							usingMutilang.getString(LoggerStringKey.Initializer_33), 
							usingMutilang.getString(LoggerStringKey.Initializer_34),
							e.getMessage(),
							e);
					usingLogger.fatal(ee.getDialogTitle(), ee);
					throw ee;
				}
			}
			
		};
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.proc.Initializer#init()
		 */
		@Override
		public void init() throws InitializeException {
			
			Map<String, Resource> resourceMap = null;
			Logger logger = null;
			ConfigModel coreConfigModel = null;
			Mutilang<LoggerStringKey> loggerMutilang = null;
			Mutilang<LabelStringKey> labelMutilang = null; 
			
			resourceMap = resourceLoader.loadResources();
			logger = loggerGenerator.newInstance(resourceMap);
			coreConfigModel = coreConfigGenerator.newInstance(resourceMap);
			loggerMutilang = loggerMutilangGenerator.newInstance(resourceMap);
			labelMutilang = labelMutilangGenerator.newInstance(resourceMap);
			
			
			
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.control.proc.Initializer#getModelManager()
		 */
		@Override
		public ModelManager getModelManager() {
			return this.modelManager;
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.control.proc.Initializer#getViewManager()
		 */
		@Override
		public ViewManager getViewManager() {
			return this.viewManager;
		}
		
		
		private InputStream getResourceInput(Map<String, Resource> resourceMap, PathKey key) throws IOException, InitializeException{
			Resource resource = resourceMap.get(key.getName());
			
			if(Objects.isNull(resource)){
				throw new InitializeException(usingMutilang.getString(LoggerStringKey.Initializer_0));
			}
			
			InputStream in = null;
			
			try {
				in = resource.openInputStream();
			} catch (IOException e) {
				
				usingLogger.warn(usingMutilang.getString(LoggerStringKey.Initializer_5), e);
				
				try{
					resource.reset();
					in = resource.openInputStream();
				}catch (IOException e1) {
					if(Objects.nonNull(in)){
						in.close();
					}
					throw e1;
				}
			}
			
			return in;
		}
		
		private static interface Generator<T> {
			public T newInstance(Map<String, Resource> resourceMap) throws InitializeException;
		}
		private interface ResourceLoader {
			public Map<String, Resource> loadResources() throws InitializeException;
		}
		
	}


	/**
	 * 初始化多语言接口。
	 * <p> 该多语言接口是程序在初始化阶段，尚未通过配置生成专用的多语言接口提供器之前，
	 * 用于代替的多语言接口，该方法生成的多语言接口只会在程序在初始化的前期保留一段时间。
	 * <p> 使用简体中文，并且不响应设置语言方法和将语言设置为默认值方法。
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private static final class InitialLoggerMutilang implements Mutilang {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
		 */
		@Override
		public String getString(Name key) {
			if(!(key instanceof LoggerStringKey)){
				throw new IllegalArgumentException("此多语言接口不支持该键");
			}
			return loggerMutilangResourceBundle.getString(key.getName());
		}
		
	}


	/**
	 * 默认多语言接口。
	 * <p> 多语言接口的默认实现。
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private static class DefaultMutilang<T extends Name> implements Mutilang<T>{

		private final Map<Locale, URL> urlMap;
		private final String missingLabel;
		
		private final ResourceBundle defaultResourceBundle;
		private final Map<String, String> valueMap = new HashMap<>();
		private boolean defaultFlag = true;
		
		public DefaultMutilang(ResourceBundle defaultResourceBundle, Map<Locale, URL> urlMap, String missingValue) {
			Objects.requireNonNull(defaultResourceBundle, "入口参数 defaultResourceBundle 不能为 null。");
			Objects.requireNonNull(urlMap, "入口参数 urlMap 不能为 null。");
			Objects.requireNonNull(missingValue, "入口参数 missingValue 不能为 null。");
			
			this.defaultResourceBundle = defaultResourceBundle;
			this.urlMap = urlMap;
			this.missingLabel = missingValue;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
		 */
		@Override
		public String getString(Name key) {
			if(defaultFlag){
				String tempStr =  defaultResourceBundle.getString(key.getName());
				return tempStr == null ? missingLabel : tempStr;
			}else{
				return valueMap.getOrDefault(key.getName(), missingLabel);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#setLocale(java.util.Locale)
		 */
		@Override
		public void setLocale(Locale locale) throws ConfigChangeFailedException{
			if(Objects.isNull(locale)){
				defaultFlag = true;
			}else{
				defaultFlag = false;
				try{
					Properties properties = new Properties();
					URL url = urlMap.get(locale);
					if(Objects.isNull(url)){
						throw new ConfigChangeFailedException("没有找到与指定语言 " + locale.toString() + " 有关的 URL。");
					}
					properties.load(url.openStream());
					valueMap.clear();
					
					for(String key : properties.stringPropertyNames()){
						valueMap.put(key, properties.getProperty(key));
					}
					
				}catch (ConfigChangeFailedException | IOException e) {
					defaultFlag = true;
					throw new ConfigChangeFailedException(e.getMessage(), e);
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#setLocale2Default()
		 */
		@Override
		public void setLocale2Default() {
			defaultFlag = true;
		}
		
	}
	
	
	//禁止外部实例化
	private ToolPlatformUtil(){}

}
