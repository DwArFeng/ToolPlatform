package com.dwarfeng.tp.model.init;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
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
import com.dwarfeng.tp.control.ToolPlatform;
import com.dwarfeng.tp.model.cfg.PathKey;
import com.dwarfeng.tp.model.io.DefaultProgramLogger;
import com.dwarfeng.tp.model.io.ProgramLogger;

/**
 * 初始化工厂。
 * <p> 用于生成初始化中的有关类。
 * @author DwArFeng
 * @since 1.8
 */
public final class InitFactory {

	/**
	 * 生成一个由指定配置确定的新的平台用记录器。
	 * @param path 指定的记录器路径配置所在的路径。
	 * @param forceOverride 是否强行覆盖现有的配置。
	 * @return 新的平台用记录器。
	 * @throws NullPointerException 入口参数为 null。
	 * @throws LoadFailedException 读取失败异常。
	 */
	public static ProgramLogger newProgramLogger(URL path, boolean forceOverride) throws LoadFailedException{

		SAXReader reader0 = new SAXReader();
		Document document0 = null;
		try {
			document0 = reader0.read(path);
		} catch (DocumentException e) {
			LoadFailedException lfe = new LoadFailedException(ToolPlatform.ATTRIBUTES.LOGGER_FAIL_MESSAGE_0);
			lfe.setStackTrace(e.getStackTrace());
			throw lfe;
		}
		
		Element root = document0.getRootElement();
		
		Element info = null;
		
		/*
		 * 根据 dom4j 的相关说明，此处转换是安全的。
		 */
		@SuppressWarnings("unchecked")
		List<Element> perhapsInfos = (List<Element>)root.elements("info");
		for(Element perhapsInfo : perhapsInfos){
			Attribute attribute = perhapsInfo.attribute("key");
			if(Objects.nonNull(attribute)){
				if(attribute.getValue().equals(PathKey.LOGGER_CFG.getName())){
					info = perhapsInfo;
					break;
				}
			}
		}
		
		if(Objects.isNull(info)){
			throw new LoadFailedException(ToolPlatform.ATTRIBUTES.LOGGER_FAIL_MESSAGE_1);
		}
		
		PathResolve pr = null;
		try{
			pr = new PathResolve(info, forceOverride);
		}catch (LoadFailedException e) {
			LoadFailedException lfe = new LoadFailedException(ToolPlatform.ATTRIBUTES.LOGGER_FAIL_MESSAGE_2);
			lfe.setStackTrace(e.getStackTrace());
			throw lfe;
		}
		
		try{
			pr.release();
		}catch (IOException e) {
			LoadFailedException lfe = new LoadFailedException(ToolPlatform.ATTRIBUTES.LOGGER_FAIL_MESSAGE_3);
			lfe.setStackTrace(e.getStackTrace());
			throw lfe;
		}
		
		InputStream in0 = null;
		Set<String> loggerNames = new HashSet<>();
		
		try{
			try {
				in0 = pr.getReleaseFileInputstream();
			} catch (IOException e) {
				//因为之前文件已经正确的释放或者确认存在，所以不可能抛出这个异常。
				e.printStackTrace();
			}
			
			SAXReader reader = new SAXReader();
			Document document = null;
			
			try {
				document = reader.read(in0);
			} catch (DocumentException e) {
				LoadFailedException lfe = new LoadFailedException(ToolPlatform.ATTRIBUTES.LOGGER_FAIL_MESSAGE_4);
				lfe.setStackTrace(e.getStackTrace());
				throw lfe;
			}
			
			Element configuration = document.getRootElement();
			if(Objects.isNull(configuration)) throw new LoadFailedException(ToolPlatform.ATTRIBUTES.LOGGER_FAIL_MESSAGE_4);
			Element loggers = configuration.element("Loggers");
			if(Objects.isNull(loggers)) throw new LoadFailedException(ToolPlatform.ATTRIBUTES.LOGGER_FAIL_MESSAGE_4);
			
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
			
		}finally{
			if(Objects.nonNull(in0)){
				try {
					in0.close();
					in0 = null;
				} catch (IOException e) {
					LoadFailedException lfe = new LoadFailedException(ToolPlatform.ATTRIBUTES.LOGGER_FAIL_MESSAGE_4);
					lfe.setStackTrace(e.getStackTrace());
					throw lfe;
				}
			}
		}
		
		InputStream in1 = null;
		
		try{
			try {
				in1 = pr.getReleaseFileInputstream();
			} catch (IOException e) {
				//因为之前文件已经正确的释放或者确认存在，所以不可能抛出这个异常。
				e.printStackTrace();
			}
			
			ConfigurationSource cs = null;
			try {
				cs = new ConfigurationSource(in1);
			} catch (IOException e) {
				LoadFailedException lfe = new LoadFailedException(ToolPlatform.ATTRIBUTES.LOGGER_FAIL_MESSAGE_4);
				lfe.setStackTrace(e.getStackTrace());
				throw lfe;
			}
			
			LoggerContext lc =  Configurator.initialize(null, cs);	
			
			Set<Logger> loggers = new HashSet<>();
			
			for(String loggerName : loggerNames){
				loggers.add(lc.getLogger(loggerName));
			}
			
			return new DefaultProgramLogger(loggers);
			
		}finally{
			if(Objects.nonNull(in1)){
				try {
					in1.close();
					in1 = null;
				} catch (IOException e) {
					LoadFailedException lfe = new LoadFailedException(ToolPlatform.ATTRIBUTES.LOGGER_FAIL_MESSAGE_4);
					lfe.setStackTrace(e.getStackTrace());
					throw lfe;
				}
			}
		}
	
	}
	
	//禁止外部实例化。
	private InitFactory(){};
	
}
