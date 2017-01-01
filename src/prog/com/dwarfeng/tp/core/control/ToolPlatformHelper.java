package com.dwarfeng.tp.core.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.basic.io.IoUtil;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.dutil.develop.cfg.ConfigUtil;
import com.dwarfeng.dutil.develop.cfg.DefaultConfigModel;
import com.dwarfeng.dutil.develop.cfg.io.PropertiesConfigLoader;
import com.dwarfeng.dutil.develop.cfg.io.StreamConfigLoader;
import com.dwarfeng.tp.core.control.proc.CoreProvider;
import com.dwarfeng.tp.core.control.proc.Initializer;
import com.dwarfeng.tp.core.model.ModelManager;
import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.cfg.PathKey;
import com.dwarfeng.tp.core.model.struct.ConfigChangeFailedException;
import com.dwarfeng.tp.core.model.struct.InitializeFailedException;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.PlatformLogger;
import com.dwarfeng.tp.core.model.struct.Resource;
import com.dwarfeng.tp.core.view.ViewManager;

/**
 * ���ڹ���ƽ̨�Ĺ����ࡣ
 * @author  DwArFeng
 * @since 1.8
 */
public final class ToolPlatformHelper {
	
	/**
	 * ��ȡ�µĳ�������ṩ����
	 * @return �µĳ�������ṩ����
	 */
	public final static CoreProvider newCoreProvider(){
		return new DefaultCoreProvider();
	}
	
	/**
	 * ����ָ�����ַ������������Ӧ�����ԡ�
	 * @param string ָ�����ַ�����
	 * @return ָ�������ԡ�
	 */
	public final static Locale newLocaleFormString(String string){
		Objects.requireNonNull(string, "��ڲ��� string ����Ϊ null��");
		
		if(string.equals("")) return null;
		
		StringTokenizer tokenizer = new StringTokenizer(string, "_");
		String language = tokenizer.hasMoreTokens()? tokenizer.nextToken() : "";
		String country = tokenizer.hasMoreTokens()? tokenizer.nextToken() : "";
		String variant  = tokenizer.hasMoreTokens()? tokenizer.nextToken() : "";
		
		return new Locale(language, country, variant);
	}
	
	/**
	 * ����һ���µĳ�ʼ������
	 * @return �µĳ�ʼ������
	 */
	public final static Initializer newDefaultInitializer(){
		return new DefaultInitializer();
	}
	

	/**
	 * Ĭ�ϵĳ����¼����
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private static class DefaultPlatformLogger implements PlatformLogger{
	
		private final Collection<? extends Logger> loggers;
		private final LoggerContext loggerContext;
		
		public DefaultPlatformLogger(Collection<? extends Logger> loggers, LoggerContext loggerContext) {
			Objects.requireNonNull(loggers, "��ڲ��� loggers ����Ϊ null��");
			Objects.requireNonNull(loggerContext, "��ڲ��� loggerContext ����Ϊ null��");
			
			this.loggers = loggers;
			this.loggerContext = loggerContext;
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.PlatformLogger#trace(java.lang.String)
		 */
		@Override
		public void trace(String message) {
			for(Logger logger : loggers){
				logger.trace(message);
			}
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.PlatformLogger#debug(java.lang.String)
		 */
		@Override
		public void debug(String message) {
			for(Logger logger : loggers){
				logger.debug(message);
			}
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.PlatformLogger#info(java.lang.String)
		 */
		@Override
		public void info(String message) {
			for(Logger logger : loggers){
				logger.info(message);
			}
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.PlatformLogger#warn(java.lang.String)
		 */
		@Override
		public void warn(String message) {
			for(Logger logger : loggers){
				logger.warn(message);
			}
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.PlatformLogger#warn(java.lang.String, java.lang.Throwable)
		 */
		@Override
		public void warn(String message, Throwable t) {
			for(Logger logger : loggers){
				logger.warn(message, t);
			}
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.PlatformLogger#error(java.lang.String, java.lang.Throwable)
		 */
		@Override
		public void error(String message, Throwable t) {
			for(Logger logger : loggers){
				logger.error(message, t);
			}
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.PlatformLogger#fatal(java.lang.String, java.lang.Throwable)
		 */
		@Override
		public void fatal(String message, Throwable t) {
			for(Logger logger : loggers){
				logger.fatal(message, t);
			}
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.PlatformLogger#stop()
		 */
		@Override
		public void stop() {
			this.loggerContext.stop();
		}
		
	}


	/**
	 * Ĭ�ϵĳ����ʼ������
	 * <p> ִ�г�����Ĭ�ϵĳ�ʼ��������
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private static final class DefaultInitializer implements Initializer{
		
		private final String missingLabel = "!�ֶ�ȱʧ";
		
		private PlatformLogger usingLogger = null;
		private Mutilang<LoggerStringKey> usingMutilang = null;
		
		private ModelManager modelManager = null;
		private ViewManager viewManager = null;
		
		
		public DefaultInitializer(){
			this(new EmptyPlatformLogger(), new DefaultLoggerMutilang());
		}
		
		public DefaultInitializer(PlatformLogger usingLogger, Mutilang<LoggerStringKey> usingMutilang){
			Objects.requireNonNull(usingLogger, "��ڲ��� usingLogger ����Ϊ null��");
			Objects.requireNonNull(usingMutilang, "��ڲ��� usingMutilang ����Ϊ null��");

			this.usingLogger = usingLogger;
			this.usingMutilang = usingMutilang;
		}
		
		/**�����е���Դ��ȡ����*/
		private final ResourceLoader resourceLoader = new ResourceLoader() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.ToolPlatform.Attributes.InnerInitializer.ResourceLoader#loadResources()
			 */
			@Override
			public Map<String, Resource> loadResources() throws InitializeFailedException {
				try{
					usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_10));
					
					URL path = ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/paths.xml");
					SAXReader reader = new SAXReader();
					Element root = reader.read(path).getRootElement();
					
					Map<String, Resource> resourceMap = new HashMap<>();
					
					/*
					 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
					 */
					@SuppressWarnings("unchecked")
					List<Element> infos = (List<Element>)root.elements("info");
					
					for(Element info : infos){
						String defString = info.attributeValue("default");
						String resString = info.attributeValue("path");
						String key = info.attributeValue("key");
						
						if(Objects.isNull(defString) || Objects.isNull(resString) || Objects.isNull(key)) {
							throw new InitializeFailedException(usingMutilang.getString(LoggerStringKey.Initializer_11));
						}
						
						URL def = ToolPlatform.class.getResource(defString);
						
						if(Objects.isNull(def)){
							throw new InitializeFailedException(usingMutilang.getString(LoggerStringKey.Initializer_12));
						}
						
						File res = new File(resString);
						
						resourceMap.put(key, new DefaultResource(def, res));
					}
					
					return resourceMap;
					
				}catch (DocumentException | InitializeFailedException e) {
					InitializeFailedException ee = new InitializeFailedException(
							usingMutilang.getString(LoggerStringKey.Initializer_13), 
							usingMutilang.getString(LoggerStringKey.Initializer_14),
							e.getMessage(),
							e);
					usingLogger.fatal(ee.getDialogTitle(), ee);
					throw ee;
				}
			}
		};
		
		/**�����еļ�¼����������*/
		private final Generator<PlatformLogger> loggerGenerator = new Generator<PlatformLogger>() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.control.ProgramAttributes.InnerInitializer.Generator#newInstance(java.util.Map)
			 */
			@Override
			public PlatformLogger newInstance(Map<String, Resource> resourceMap) throws InitializeFailedException {
				Objects.requireNonNull(resourceMap, "��ڲ��� resourceMap ����Ϊ null��");
				
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
					 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
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
					InitializeFailedException ee = new InitializeFailedException(
							usingMutilang.getString(LoggerStringKey.Initializer_4), 
							usingMutilang.getString(LoggerStringKey.Initializer_15),
							e.getMessage(),
							e);
					usingLogger.fatal(ee.getDialogTitle(), ee);
					throw ee;
				}
	
			}
	
		};
		
		/**�������ö�ȡ��*/
		private final Generator<ConfigModel> coreConfigGenerator = new Generator<ConfigModel>() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.control.ProgramAttributes.InnerInitializer.Generator#newInstance(java.util.Map)
			 */
			@Override
			public ConfigModel newInstance(Map<String, Resource> resourceMap) throws InitializeFailedException {
				Objects.requireNonNull(resourceMap, "��ڲ��� resourceMap ����Ϊ null��");
				
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
					InitializeFailedException ee = new InitializeFailedException(
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
		
		/**��¼�������Ի���ȡ��*/
		private final Generator<Mutilang<LoggerStringKey>> loggerMutilangGenerator = new Generator<Mutilang<LoggerStringKey>>() {
	
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.control.ProgramAttributes.InnerInitializer.Generator#newInstance(java.util.Map)
			 */
			@Override
			public Mutilang<LoggerStringKey> newInstance(Map<String, Resource> resourceMap)throws InitializeFailedException {
				Objects.requireNonNull(resourceMap, "��ڲ��� resourceMap ����Ϊ null��");
				
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
						throw new InitializeFailedException(usingMutilang.getString(LoggerStringKey.Initializer_19));
					}
					
					File rootDir = new File(rootDirStr);
					Map<Locale, URL> urlMap = new HashMap<>();
					
					/*
					 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
					 * 	<!--ʹ�����µĸ�ʽ��<info language="zh" country="CN" variant="" label="��������" file="zh_CN.properties"></info>-->
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
					
					DefaultMutilang<LoggerStringKey> loggerMutiLang = new DefaultMutilang<>(resourceBundle, urlMap, missingLabel);
					usingMutilang = loggerMutiLang;
					
					usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_21));
					return loggerMutiLang;
					
				}catch (IOException | DocumentException e) {
					InitializeFailedException ee = new InitializeFailedException(
							usingMutilang.getString(LoggerStringKey.Initializer_27), 
							usingMutilang.getString(LoggerStringKey.Initializer_28),
							e.getMessage(),
							e);
					usingLogger.fatal(ee.getDialogTitle(), ee);
					throw ee;
				}
			}
			
		};
		
		/**��ǩ�����Ի���ȡ��*/
		private final Generator<Mutilang<LabelStringKey>> labelMutilangGenerator = new Generator<Mutilang<LabelStringKey>>() {
	
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.control.ProgramAttributes.InnerInitializer.Generator#newInstance(java.util.Map)
			 */
			@Override
			public Mutilang<LabelStringKey> newInstance(Map<String, Resource> resourceMap)throws InitializeFailedException {
				Objects.requireNonNull(resourceMap, "��ڲ��� resourceMap ����Ϊ null��");
				
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
						throw new InitializeFailedException(usingMutilang.getString(LoggerStringKey.Initializer_30));
					}
					
					File rootDir = new File(rootDirStr);
					Map<Locale, URL> urlMap = new HashMap<>();
					
					/*
					 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
					 * 	<!--ʹ�����µĸ�ʽ��<info language="zh" country="CN" variant="" label="��������" file="zh_CN.properties"></info>-->
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
					
					DefaultMutilang<LoggerStringKey> loggerMutiLang = new DefaultMutilang<>(resourceBundle, urlMap, missingLabel);
					usingMutilang = loggerMutiLang;
					
					usingLogger.info(usingMutilang.getString(LoggerStringKey.Initializer_32));
					return null;
					
				}catch (IOException | DocumentException e) {
					InitializeFailedException ee = new InitializeFailedException(
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
		public void init() throws InitializeFailedException {
			
			Map<String, Resource> resourceMap = null;
			PlatformLogger logger = null;
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
		
		
		private InputStream getResourceInput(Map<String, Resource> resourceMap, PathKey key) throws IOException, InitializeFailedException{
			Resource resource = resourceMap.get(key.getName());
			
			if(Objects.isNull(resource)){
				throw new InitializeFailedException(usingMutilang.getString(LoggerStringKey.Initializer_0));
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
			public T newInstance(Map<String, Resource> resourceMap) throws InitializeFailedException;
		}
		private interface ResourceLoader {
			public Map<String, Resource> loadResources() throws InitializeFailedException;
		}
		
	}


	/**
	 * Ĭ�ϵļ�¼�������Խӿڡ�
	 * <p> ʹ�ü������ģ����Ҳ���Ӧ�������Է����ͽ���������ΪĬ��ֵ������
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private static final class DefaultLoggerMutilang implements Mutilang<LoggerStringKey> {
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
				"com.dwarfeng.tp.resource.defaultres.mutilang.logger.default");
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.cfg.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
		 */
		@Override
		public String getString(LoggerStringKey key) {
			return resourceBundle.getString(key.getName());
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#setLocale(java.util.Locale)
		 */
		@Override
		public void setLocale(Locale locale) {}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#setLocale2Default()
		 */
		@Override
		public void setLocale2Default() {}
		
	}


	/**
	 * Ĭ����Դ��
	 * <p> ��������Դ��Ĭ��ʵ�֡�
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private final static class DefaultResource implements Resource{
		
		private final URL def;
		private final File res;
		
		/**
		 * ����ʵ����
		 * @param def ָ����Ĭ��URL��
		 * @param res ָ������Դ�ļ���
		 * @throws NullPointerException ��ڲ���Ϊ null��
		 */
		public DefaultResource(URL def, File res) {
			Objects.requireNonNull(def, "��ڲ��� def ����Ϊ null");
			Objects.requireNonNull(res, "��ڲ��� res ����Ϊ null");
			
			this.def = def;
			this.res = res;
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Resource#openInputStream()
		 */
		@Override
		public InputStream openInputStream() throws IOException {
			return new FileInputStream(res);
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Resource#openOutputStream()
		 */
		@Override
		public OutputStream openOutputStream() throws IOException {
			return new FileOutputStream(res);
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Resource#reset()
		 */
		@Override
		public void reset() throws IOException {
			FileUtil.createFileIfNotExists(res);
			
			InputStream in = null;
			OutputStream out = null;
			
			try{
				in = def.openStream();
				out = new FileOutputStream(res);
				IoUtil.trans(in, out, 8192);
			}finally {
				if(Objects.nonNull(in)){
					in.close();
				}
				if(Objects.nonNull(out)){
					out.close();
				}
			}
		}
		
	}
	
	
	/**
	 * Ĭ�϶����Խӿڡ�
	 * <p> �����Խӿڵ�Ĭ��ʵ�֡�
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
			Objects.requireNonNull(defaultResourceBundle, "��ڲ��� defaultResourceBundle ����Ϊ null��");
			Objects.requireNonNull(urlMap, "��ڲ��� urlMap ����Ϊ null��");
			Objects.requireNonNull(missingValue, "��ڲ��� missingValue ����Ϊ null��");
			
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
						throw new ConfigChangeFailedException("û���ҵ���ָ������ " + locale.toString() + " �йص� URL��");
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
	
	
	/**
	 * Ĭ��ģ�͹�������
	 * <p> ������ģ�͹������ӿڵ�Ĭ��ʵ�֡�
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private static class DefaultModelManager implements ModelManager{
		
		private final PlatformLogger logger;
		private final Mutilang<LoggerStringKey> loggerMutilang;
		private final Mutilang<LabelStringKey> labelMutilang;
		private final ConfigModel coreConfigModel;
		private final ConfigModel invisibleConfigModel;
		
		public DefaultModelManager(
				PlatformLogger logger, 
				Mutilang<LoggerStringKey> loggerMutilang,
				Mutilang<LabelStringKey> labelMutilang,
				ConfigModel coreConfigModel,
				ConfigModel invisibleConfigModel
				) {
			Objects.requireNonNull(logger, "��ڲ��� logger ����Ϊ null��");
			Objects.requireNonNull(loggerMutilang, "��ڲ��� loggerMutilang ����Ϊ null��");
			Objects.requireNonNull(labelMutilang, "��ڲ��� labelMutilang ����Ϊ null��");
			Objects.requireNonNull(coreConfigModel, "��ڲ��� coreConfigModel ����Ϊ null��");
			Objects.requireNonNull(invisibleConfigModel, "��ڲ��� invisibleConfigModel ����Ϊ null��");

			this.logger = logger;
			this.loggerMutilang = loggerMutilang;
			this.labelMutilang = labelMutilang;
			this.coreConfigModel = coreConfigModel;
			this.invisibleConfigModel = invisibleConfigModel;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.ModelManager#getLogger()
		 */
		@Override
		public PlatformLogger getLogger() {
			return this.logger;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.ModelManager#getLoggerMutilang()
		 */
		@Override
		public Mutilang<LoggerStringKey> getLoggerMutilang() {
			return this.loggerMutilang;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.ModelManager#getLabelMutilang()
		 */
		@Override
		public Mutilang<LabelStringKey> getLabelMutilang() {
			return this.labelMutilang;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.ModelManager#getCoreConfigModel()
		 */
		@Override
		public ConfigModel getCoreConfigModel() {
			return this.coreConfigModel;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.ModelManager#getInvisibleConfigModel()
		 */
		@Override
		public ConfigModel getInvisibleConfigModel() {
			return this.invisibleConfigModel;
		}
		
	}


	/**
	 * �յĳ����¼����
	 * <p> �˳����¼�����κμ�¼��������ִ���κζ�����
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private static class EmptyPlatformLogger implements PlatformLogger{
		@Override
		public void trace(String message) {}
		@Override
		public void debug(String message) {}
		@Override
		public void info(String message) {}
		@Override
		public void warn(String message) {}
		@Override
		public void warn(String message, Throwable t) {}
		@Override
		public void error(String message, Throwable t) {}
		@Override
		public void fatal(String message, Throwable t) {}
		@Override
		public void stop() {}
		
	}
	
	private static class DefaultCoreProvider implements CoreProvider{
		
	}
	
	
	//��ֹ�ⲿʵ����
	private ToolPlatformHelper(){}

}
