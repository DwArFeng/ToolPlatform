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

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.tp.model.cfg.PathKey;
import com.dwarfeng.tp.model.io.DefaultProgramResource;
import com.dwarfeng.tp.model.io.ProgramLogger;
import com.dwarfeng.tp.model.io.ProgramLoggerGenerator;
import com.dwarfeng.tp.model.io.ProgramResource;
import com.dwarfeng.tp.model.io.ProgramResourceLoader;
import com.dwarfeng.tp.model.struct.EmergencyException;

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

	/**程序中的记录生成器*/
	public final ProgramLoggerGenerator loggerGenerator = new ProgramLoggerGenerator() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.io.ProgramLoggerGenerator#newInstance(java.util.Map)
		 */
		@Override
		public ProgramLogger newInstance(Map<String, ProgramResource> resourceMap) throws EmergencyException {
			
			try{
				ProgramResource resource = resourceMap.get(PathKey.LOGGER_CFG.getName());
				
				if(Objects.isNull(resource)){
					throw new EmergencyException(null, "未能在资源映射中找到指定的键值");
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
						loggerNames.add(attribute.getStringValue());
					}
				}
				
				ConfigurationSource cs =  new ConfigurationSource(in1);
				LoggerContext lc =  Configurator.initialize(null, cs);	
				
				Set<Logger> loggerSet = new HashSet<>();
				
				for(String loggerName : loggerNames){
					loggerSet.add(lc.getLogger(loggerName));
				}
				
				return new InnerProgramLogger(loggerSet);
				
			}catch (IOException | DocumentException e) {
				EmergencyException ee = new EmergencyException("无法生成记录器", e.getMessage());
				ee.setStackTrace(e.getStackTrace());
				throw ee;
			}

		}
	};
	
	/**Logger生成失败的标题*/
	public final String LOGGER_FAIL_TITLE = "Logger 生成失败";
	
	/**Logger生成失败信息0*/
	public final String LOGGER_FAIL_MESSAGE_0 = "xml文件解析失败，请检查系统配置或程序的完整性。可能的原因是：\n"
			+ "\t1. 指定的xml路径无效。\n"
			+ "\t2. xml中的内容损坏。";
	
	/**Logger生成失败信息1*/
	public final String LOGGER_FAIL_MESSAGE_1 = "xml文件解析失败，请检配置文件的正确性。可能的原因是：\n"
			+ "\t1. xml中的内容损坏。\n"
			+ "\t2. xml中找不到含有属性为 key=\"logger-cfg\" 的 info 元素。";

	public final String LOGGER_FAIL_MESSAGE_2 = "xml文件解析失败，请检配置文件的正确性。可能的原因是：\n"
			+ "\t1. xml中 info 元素下子节点缺失。\n"
			+ "\t2. xml中 default-path 中的 type 属性含有未知的值。\n"
			+ "\t3. xml中 release-path 中的 type 属性含有未知的值。\n"
			+ "\t4. xml中 release-path 中的 type 属性程序尚不支持。";
	
	public final String LOGGER_FAIL_MESSAGE_3 = "文件无法被正确的释放。可能的原因是：\n"
			+ "\t1. 文件被占用。\n"
			+ "\t2. xml中相应的配置处文件名、目录名或卷标语法不正确。\n";
	
	public final String LOGGER_FAIL_MESSAGE_4 = "Logger配置无法被正确的读取。可能的原因是：\n"
			+ "\tLogger配置不正确";
	
	public final String LOGGER_FAIL_MESSAGE_5 = "Logger无法正确生成。";
	
	public final String MISSING_STRING = "!文本域丢失";
	
	private static class InnerProgramLogger implements ProgramLogger{

		private final Collection<? extends Logger> loggers;
		
		public InnerProgramLogger(Collection<? extends Logger> loggers) {
			Objects.requireNonNull(loggers, "入口参数 loggers 不能为 null。");
			this.loggers = loggers;
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
		
	}
	
}
