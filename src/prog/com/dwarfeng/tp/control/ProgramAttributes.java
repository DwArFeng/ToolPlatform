package com.dwarfeng.tp.control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.tp.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.model.cfg.Mutilang;
import com.dwarfeng.tp.model.cfg.PathKey;
import com.dwarfeng.tp.model.io.ProgramResourceLoader;
import com.dwarfeng.tp.model.struct.DefaultProgramResource;
import com.dwarfeng.tp.model.struct.EmergencyException;
import com.dwarfeng.tp.model.struct.Generator;
import com.dwarfeng.tp.model.struct.ProgramLogger;
import com.dwarfeng.tp.model.struct.ProgramResource;

/**
 * 记录程序的一些固定不变的属性。
 * @author  DwArFeng
 * @since 1.8
 */
public class ProgramAttributes {
	
	/**程序的版本*/
	public final Version VERSION = new DefaultVersion.Builder()
			.type(VersionType.RELEASE)
			.firstVersion((byte) 0)
			.secondVersion((byte) 0)
			.thirdVersion((byte) 0)
			.buildDate("20161222")
			.buildVersion('A')
			.build();
	
	/**程序的作者*/
	public final String author = "DwArFeng";
	
	/**程序中的资源读取器。*/
	public final ProgramResourceLoader resourceLoader = new ProgramResourceLoader() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.io.ProgramResourceLoader#loadResources()
		 */
		@Override
		public Map<String, ProgramResource> loadResources() throws EmergencyException {
			try{
				URL path = ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/paths.xml");
				SAXReader reader = new SAXReader();
				Element root = reader.read(path).getRootElement();
				
				Map<String, ProgramResource> resourceMap = new HashMap<>();
				
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
						throw new EmergencyException(null, "配置文件属性缺失");
					}
					
					URL def = ToolPlatform.class.getResource(defString);
					
					if(Objects.isNull(def)){
						throw new EmergencyException(null, "配置文件默认资源不正确");
					}
					
					File res = new File(resString);
					
					resourceMap.put(key, new DefaultProgramResource(def, res));
				}
				
				return resourceMap;
				
			}catch (DocumentException | EmergencyException e) {
				EmergencyException ee = new EmergencyException("无法解析初始化配置", e.getMessage());
				ee.setStackTrace(e.getStackTrace());
				throw ee;
			}
		}
	};
	
	/**程序中的记录器的生成器*/
	public final Generator<ProgramLogger> loggerGenerator = new Generator<ProgramLogger>() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.io.ProgramLoggerGenerator#newInstance(java.util.Map)
		 */
		@Override
		public ProgramLogger newInstance(Map<String, ProgramResource> resourceMap, ProgramLogger logger, Mutilang<LoggerStringKey> mutilang) throws EmergencyException {
			Objects.requireNonNull(resourceMap, "入口参数 resourceMap 不能为 null。");
			Objects.requireNonNull(logger, "入口参数 logger 不能为 null。");
			Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
			
			try{
				logger.info(mutilang.getString(LoggerStringKey.ProgramAttributes_1));
				
				ProgramResource resource = resourceMap.get(PathKey.LOGGER_SETTING.getName());
				
				if(Objects.isNull(resource)){
					throw new EmergencyException(null, mutilang.getString(LoggerStringKey.ProgramAttributes_0));
				}
				
				InputStream in0 = null;
				InputStream in1 = null;
				
				try {
					in0 = resource.openInputStream();
					in1 = resource.openInputStream();
				} catch (IOException e) {
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
				 * 根据 dom4j 的相关说明，此处转换是安全的。
				 */
				@SuppressWarnings("unchecked")
				List<Element> perhapsLoggers = (List<Element>)loggers.elements("Logger");
				for(Element perhapsLogger : perhapsLoggers){
					Attribute attribute = perhapsLogger.attribute("name");
					if(Objects.nonNull(attribute)){
						String name = attribute.getStringValue();
						loggerNames.add(name);
						logger.info(String.format(mutilang.getString(LoggerStringKey.ProgramAttributes_2), name));
					}
				}
				
				ConfigurationSource configurationSource =  new ConfigurationSource(in1);
				LoggerContext loggerContext =  Configurator.initialize(null, configurationSource);	
				
				Set<Logger> loggerSet = new HashSet<>();
				
				for(String loggerName : loggerNames){
					loggerSet.add(loggerContext.getLogger(loggerName));
				}
				
				ProgramLogger newLogger = new InnerProgramLogger(loggerSet, loggerContext);
				newLogger.info(mutilang.getString(LoggerStringKey.ProgramAttributes_3));
				
				return newLogger;
				
			}catch (IOException | DocumentException e) {
				logger.fatal(e.getMessage(), e);
				EmergencyException ee = new EmergencyException(mutilang.getString(LoggerStringKey.ProgramAttributes_4), e.getMessage());
				ee.setStackTrace(e.getStackTrace());
				throw ee;
			}

		}

	};
	
	/**多语言系统中未能找到指定字段所使用的默认文本。*/
	public final String missingMutilang = "!文本值未找到";
	
	/**默认的记录器多语言接口*/
	public final Mutilang<LoggerStringKey> defaultLoggerMutilang = new Mutilang<LoggerStringKey>() {
		
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
		
	};
	
	/**
	 * 程序的默认记录器。
	 * <p> 该记录器的 stop 方法不执行任何操作，调用stop之后仍然可以正常使用。
	 */
	public final ProgramLogger defaultProgramLogger = new ProgramLogger() {
		@Override
		public void warn(String message, Throwable t) {}
		@Override
		public void warn(String message) {}
		@Override
		public void trace(String message) {}
		@Override
		public void info(String message) {}
		@Override
		public void fatal(String message, Throwable t) {}
		@Override
		public void error(String message, Throwable t) {}
		@Override
		public void debug(String message) {}
		@Override
		public void stop(){}
	};
	
	/**程序配置读取器*/
	public final Generator<ConfigModel> programConfigGenerator = new Generator<ConfigModel>() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.io.Generator#newInstance(java.util.Map, com.dwarfeng.tp.model.io.ProgramLogger, com.dwarfeng.tp.model.cfg.Mutilang)
		 */
		@Override
		public ConfigModel newInstance(Map<String, ProgramResource> resourceMap, ProgramLogger logger, Mutilang<LoggerStringKey> mutilang) throws EmergencyException {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	
	
	
	private static class InnerProgramLogger implements ProgramLogger{

		private final Collection<? extends Logger> loggers;
		private final LoggerContext loggerContext;
		
		public InnerProgramLogger(Collection<? extends Logger> loggers, LoggerContext loggerContext) {
			Objects.requireNonNull(loggers, "入口参数 loggers 不能为 null。");
			Objects.requireNonNull(loggerContext, "入口参数 loggerContext 不能为 null。");
			
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
	
	
	
}
