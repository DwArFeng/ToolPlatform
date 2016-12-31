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
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.basic.io.IoUtil;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.dutil.develop.cfg.ConfigUtil;
import com.dwarfeng.dutil.develop.cfg.DefaultConfigModel;
import com.dwarfeng.dutil.develop.cfg.io.PropertiesConfigLoader;
import com.dwarfeng.dutil.develop.cfg.io.StreamConfigLoader;
import com.dwarfeng.tp.core.control.proc.Initializer;
import com.dwarfeng.tp.core.model.ModelManager;
import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.cfg.PathKey;
import com.dwarfeng.tp.core.model.struct.EmergencyException;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.ProgramLogger;
import com.dwarfeng.tp.core.model.struct.Resource;
import com.dwarfeng.tp.core.view.ViewManager;
import com.dwarfeng.tp.core.view.ViewUtil;

/**
 * ToolPlatform��DwArFeng �Ĺ���ƽ̨����
 * <p> �ù���ƽ̨���������� DwArFeng ��д���ڶ�Ĺ��ߵġ�
 * �ù���ƽ̨���÷����ȡ�乤��Ŀ¼�µ����й��ߣ�����ӵ�н���Щ���߽��зֱ�ǩ��������������Ĺ��ܡ�
 * <p> TODO ��Ҫ������ϸ��������
 * @author  DwArFeng
 * @since 1.8
 */
public final class ToolPlatform {
	
	public static void main(String[] args) {
		new ToolPlatform();
	}
	
	/**
	 * �����е����Լ��ϡ�
	 * <p> �����Լ����ṩ�����е�һЩ�������ԣ������������ơ���������ߡ�����İ汾�ȵȡ�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Attributes{
		
		/**����İ汾*/
		public final static Version VERSION = new DefaultVersion.Builder()
				.type(VersionType.RELEASE)
				.firstVersion((byte) 0)
				.secondVersion((byte) 0)
				.thirdVersion((byte) 0)
				.buildDate("20161222")
				.buildVersion('A')
				.build();
		
		/**���������*/
		public final static String author = "DwArFeng";
		
		/**������ϵͳ��δ���ҵ�ָ���ֶ���ʹ�õ�Ĭ���ı���*/
		private final static String missingMutilang = "!�ı�ֵδ�ҵ�";
		
		private final static Initializer newDefaultInitializer(){
			return new InnerInitializer();
		}
		
		private final static Mutilang<LoggerStringKey> newDefaultLoggerMutilang(){
			return new InnerDefaultLoggerMutilang();
		}
		
		private final static ProgramLogger newDefaultProgramLogger(){
			return new InnerDefaultProgramLogger();
		}
		
		
		private static class InnerProgramLogger implements ProgramLogger{

			private final Collection<? extends Logger> loggers;
			private final LoggerContext loggerContext;
			
			public InnerProgramLogger(Collection<? extends Logger> loggers, LoggerContext loggerContext) {
				Objects.requireNonNull(loggers, "��ڲ��� loggers ����Ϊ null��");
				Objects.requireNonNull(loggerContext, "��ڲ��� loggerContext ����Ϊ null��");
				
				this.loggers = loggers;
				this.loggerContext = loggerContext;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.model.io.ProgramLogger#trace(java.lang.String)
			 */
			@Override
			public void trace(String message) {
				for(Logger logger : loggers){
					logger.trace(message);
				}
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.model.io.ProgramLogger#debug(java.lang.String)
			 */
			@Override
			public void debug(String message) {
				for(Logger logger : loggers){
					logger.debug(message);
				}
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.model.io.ProgramLogger#info(java.lang.String)
			 */
			@Override
			public void info(String message) {
				for(Logger logger : loggers){
					logger.info(message);
				}
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.model.io.ProgramLogger#warn(java.lang.String)
			 */
			@Override
			public void warn(String message) {
				for(Logger logger : loggers){
					logger.warn(message);
				}
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.model.io.ProgramLogger#warn(java.lang.String, java.lang.Throwable)
			 */
			@Override
			public void warn(String message, Throwable t) {
				for(Logger logger : loggers){
					logger.warn(message, t);
				}
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.model.io.ProgramLogger#error(java.lang.String, java.lang.Throwable)
			 */
			@Override
			public void error(String message, Throwable t) {
				for(Logger logger : loggers){
					logger.error(message, t);
				}
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.model.io.ProgramLogger#fatal(java.lang.String, java.lang.Throwable)
			 */
			@Override
			public void fatal(String message, Throwable t) {
				for(Logger logger : loggers){
					logger.fatal(message, t);
				}
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.model.io.ProgramLogger#stop()
			 */
			@Override
			public void stop() {
				this.loggerContext.stop();
			}
			
		}
		
		private static final class InnerInitializer implements Initializer{
			
			private ProgramLogger usingLogger = null;
			private Mutilang<LoggerStringKey> usingMutilang = null;
			
			private ModelManager modelManager = null;
			private ViewManager viewManager = null;
			
			/**�����е���Դ��ȡ����*/
			private final ResourceLoader resourceLoader = new ResourceLoader() {
				
				/*
				 * (non-Javadoc)
				 * @see com.dwarfeng.tp.core.control.ToolPlatform.Attributes.InnerInitializer.ResourceLoader#loadResources()
				 */
				@Override
				public Map<String, Resource> loadResources() throws EmergencyException {
					try{
						usingLogger.info(usingMutilang.getString(LoggerStringKey.ProgramAttributes_10));
						
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
								throw new EmergencyException(usingMutilang.getString(LoggerStringKey.ProgramAttributes_11));
							}
							
							URL def = ToolPlatform.class.getResource(defString);
							
							if(Objects.isNull(def)){
								throw new EmergencyException(usingMutilang.getString(LoggerStringKey.ProgramAttributes_12));
							}
							
							File res = new File(resString);
							
							resourceMap.put(key, new InnerResource(def, res));
						}
						
						return resourceMap;
						
					}catch (DocumentException | EmergencyException e) {
						EmergencyException ee = new EmergencyException(
								usingMutilang.getString(LoggerStringKey.ProgramAttributes_13), 
								usingMutilang.getString(LoggerStringKey.ProgramAttributes_14),
								100, e.getMessage());
						ee.setStackTrace(e.getStackTrace());
						usingLogger.fatal(ee.getMessage(), ee);
						throw ee;
					}
				}
			};
			
			/**�����еļ�¼����������*/
			private final Generator<ProgramLogger> loggerGenerator = new Generator<ProgramLogger>() {
				
				/*
				 * (non-Javadoc)
				 * @see com.dwarfeng.tp.control.ProgramAttributes.InnerInitializer.Generator#newInstance(java.util.Map)
				 */
				@Override
				public ProgramLogger newInstance(Map<String, Resource> resourceMap) throws EmergencyException {
					Objects.requireNonNull(resourceMap, "��ڲ��� resourceMap ����Ϊ null��");
					
					try{
						usingLogger.info(usingMutilang.getString(LoggerStringKey.ProgramAttributes_1));
						
						Resource resource = resourceMap.get(PathKey.LOGGER_SETTING.getName());
						
						if(Objects.isNull(resource)){
							throw new EmergencyException(usingMutilang.getString(LoggerStringKey.ProgramAttributes_0));
						}
						
						InputStream in0 = null;
						InputStream in1 = null;
						
						try {
							in0 = resource.openInputStream();
							in1 = resource.openInputStream();
						} catch (IOException e) {
							
							usingLogger.warn(usingMutilang.getString(LoggerStringKey.ProgramAttributes_5));
							
							try{
								resource.reset();
								in0 = resource.openInputStream();
								in1 = resource.openInputStream();
							}catch (IOException e1) {
								if(Objects.nonNull(in0)){
									in0.close();
								}
								throw e1;
							}
						}
						
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
								usingLogger.info(String.format(usingMutilang.getString(LoggerStringKey.ProgramAttributes_2), name));
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
						usingLogger = new InnerProgramLogger(loggerSet, loggerContext);
						usingLogger.info(usingMutilang.getString(LoggerStringKey.ProgramAttributes_3));
						
						return usingLogger;
						
					}catch (IOException | DocumentException e) {
						EmergencyException ee = new EmergencyException(
								usingMutilang.getString(LoggerStringKey.ProgramAttributes_4), 
								usingMutilang.getString(LoggerStringKey.ProgramAttributes_15),
								100, e.getMessage());
						ee.setStackTrace(e.getStackTrace());
						usingLogger.fatal(ee.getMessage(), ee);
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
				public ConfigModel newInstance(Map<String, Resource> resourceMap) throws EmergencyException {
					Objects.requireNonNull(resourceMap, "��ڲ��� resourceMap ����Ϊ null��");
					
					try{
						usingLogger.info(usingMutilang.getString(LoggerStringKey.ProgramAttributes_8));
						
						Resource resource = resourceMap.get(PathKey.CONFIGURATION_CORE.getName());
						
						if(Objects.isNull(resource)){
							throw new EmergencyException(usingMutilang.getString(LoggerStringKey.ProgramAttributes_0));
						}
						
						InputStream in = null;
						
						try {
							in = resource.openInputStream();
						} catch (IOException e) {
							
							usingLogger.warn(usingMutilang.getString(LoggerStringKey.ProgramAttributes_5), e);
							
							try{
								resource.reset();
								in = resource.openInputStream();
							}catch (IOException e1) {
								throw e1;
							}
						}
						
						ConfigModel model = ConfigUtil.unmodifiableConfigModel(new DefaultConfigModel(CoreConfig.values()));
						StreamConfigLoader loader = new PropertiesConfigLoader(in);
						
						try {
							usingLogger.info(usingMutilang.getString(LoggerStringKey.ProgramAttributes_6));
							loader.loadConfig(model);
						} catch (LoadFailedException e) {
							e.printStackTrace();
						}finally{
							loader.close();
						}
						
						usingLogger.info(usingMutilang.getString(LoggerStringKey.ProgramAttributes_9));
						
						return model;
						
					}catch (IOException e) {
						EmergencyException ee = new EmergencyException(
								usingMutilang.getString(LoggerStringKey.ProgramAttributes_7), 
								usingMutilang.getString(LoggerStringKey.ProgramAttributes_16),
								100, e.getMessage());
						ee.setStackTrace(e.getStackTrace());
						usingLogger.fatal(ee.getMessage(), ee);
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
				public Mutilang<LoggerStringKey> newInstance(Map<String, Resource> resourceMap)throws EmergencyException {
					Objects.requireNonNull(resourceMap, "��ڲ��� resourceMap ����Ϊ null��");
					
					try{
						usingLogger.info(usingMutilang.getString(LoggerStringKey.ProgramAttributes_17));
						
						Resource resource = resourceMap.get(PathKey.MUTILANG_LOGGER_SETTING.getName());
						
						if(Objects.isNull(resource)){
							throw new EmergencyException(usingMutilang.getString(LoggerStringKey.ProgramAttributes_0));
						}
						
						InputStream in = null;
						
						try {
							in = resource.openInputStream();
						} catch (IOException e) {
							
							usingLogger.warn(usingMutilang.getString(LoggerStringKey.ProgramAttributes_5), e);
							
							try{
								resource.reset();
								in = resource.openInputStream();
							}catch (IOException e1) {
								throw e1;
							}
						}
						
						SAXReader reader = new SAXReader();
						
						Element root = null;
						try{
							usingLogger.info(usingMutilang.getString(LoggerStringKey.ProgramAttributes_18));
							root = reader.read(in).getRootElement();
						}finally {
							in.close();
						}
						
						
						//TODO
						
						return null;
						
					}catch (IOException | DocumentException e) {
						EmergencyException ee = new EmergencyException(
								usingMutilang.getString(LoggerStringKey.ProgramAttributes_7), 
								usingMutilang.getString(LoggerStringKey.ProgramAttributes_16),
								100, e.getMessage());
						ee.setStackTrace(e.getStackTrace());
						usingLogger.fatal(ee.getMessage(), ee);
						throw ee;
					}
				}
				
			};
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.control.proc.Initializer#init(com.dwarfeng.tp.model.struct.ProgramLogger, com.dwarfeng.tp.model.cfg.Mutilang)
			 */
			@Override
			public void init(ProgramLogger preLogger, Mutilang<LoggerStringKey> preMutilang) throws EmergencyException {
				Objects.requireNonNull(preLogger, "��ڲ��� preLogger ����Ϊ null��");
				Objects.requireNonNull(preMutilang, "��ڲ��� preMutilang ����Ϊ null��");
				
				this.usingLogger = preLogger;
				this.usingMutilang = preMutilang;
				
				Map<String, Resource> resourceMap = null;
				ProgramLogger logger = null;
				ConfigModel coreConfigModel = null;
				Mutilang<LoggerStringKey> loggerMutilang = null;
				
				resourceMap = resourceLoader.loadResources();
				logger = loggerGenerator.newInstance(resourceMap);
				coreConfigModel = coreConfigGenerator.newInstance(resourceMap);
				loggerMutilang = loggerMutilangGenerator.newInstance(resourceMap);
				
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
			
			
			
			
			private static interface Generator<T> {
				public T newInstance(Map<String, Resource> resourceMap) throws EmergencyException;
			}
			private interface ResourceLoader {
				public Map<String, Resource> loadResources() throws EmergencyException;
			}
			
		}
		
		private static final class InnerDefaultLoggerMutilang implements Mutilang<LoggerStringKey> {
			
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
			
		}
		
		private static final class InnerDefaultProgramLogger implements ProgramLogger{
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.model.struct.ProgramLogger#warn(java.lang.String, java.lang.Throwable)
			 */
			@Override
			public void warn(String message, Throwable t) {}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.model.struct.ProgramLogger#warn(java.lang.String)
			 */
			@Override
			public void warn(String message) {}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.model.struct.ProgramLogger#trace(java.lang.String)
			 */
			@Override
			public void trace(String message) {}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.model.struct.ProgramLogger#info(java.lang.String)
			 */
			@Override
			public void info(String message) {}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.model.struct.ProgramLogger#fatal(java.lang.String, java.lang.Throwable)
			 */
			@Override
			public void fatal(String message, Throwable t) {}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.model.struct.ProgramLogger#error(java.lang.String, java.lang.Throwable)
			 */
			@Override
			public void error(String message, Throwable t) {}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.model.struct.ProgramLogger#debug(java.lang.String)
			 */
			@Override
			public void debug(String message) {}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.model.struct.ProgramLogger#stop()
			 */
			@Override
			public void stop(){}
		
		}
		
		private final static class InnerResource implements Resource{
			
			private final URL def;
			private final File res;
			
			/**
			 * ����ʵ����
			 * @param def ָ����Ĭ��URL��
			 * @param res ָ������Դ�ļ���
			 * @throws NullPointerException ��ڲ���Ϊ null��
			 */
			public InnerResource(URL def, File res) {
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
		
		private static final class InnerLoggerMutilang implements Mutilang<LoggerStringKey> {

			
			
			public InnerLoggerMutilang() {
				// TODO Auto-generated constructor stub
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
			 */
			@Override
			public String getString(LoggerStringKey key) {
				// TODO Auto-generated method stub
				return null;
			}
			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**���������õ�ģ�͹�������*/
	private final ModelManager modelManager;
	private final ViewManager viewManager;
	
	/**
	 * ����һ��Ĭ�ϵĹ���ƽ̨ʵ����
	 * ����һ������ָ�� TODO
	 */
	public ToolPlatform() {
		this(Attributes.newDefaultInitializer(), Attributes.newDefaultProgramLogger(), Attributes.newDefaultLoggerMutilang());
	}
	
	/**
	 * 
	 * @param preLogger
	 * @param preMutilang
	 */
	public ToolPlatform(Initializer initializer, ProgramLogger preLogger, Mutilang<LoggerStringKey> preMutilang){
		Objects.requireNonNull(initializer, "��ڲ��� initializer ����Ϊ null��");
		Objects.requireNonNull(preLogger, "��ڲ��� preLogger ����Ϊ null��");
		Objects.requireNonNull(preMutilang, "��ڲ��� preLoggerMutilang ����Ϊ null��");
		
		try {
			initializer.init(preLogger, preMutilang);
		} catch (EmergencyException e) {
			ViewUtil.showEmergentMessage(e.getDialogTitle(), e.getDialogMessage());
			System.exit(e.getExitCode());
		}
		
		this.modelManager = initializer.getModelManager();
		this.viewManager = initializer.getViewManager();
		
		
	}
	
}
