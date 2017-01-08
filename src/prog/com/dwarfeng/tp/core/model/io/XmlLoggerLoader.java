package com.dwarfeng.tp.core.model.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Set;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.cm.LoggerModel;

/**
 * xml记录器模型读取器。
 * <p> 使用xml读取多语言模型。
 * @author  DwArFeng
 * @since 1.8
 */
public final class XmlLoggerLoader extends StreamLoggerLoader {

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public XmlLoggerLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.LoggerLoader#load(com.dwarfeng.tp.core.model.vim.LoggerModel)
	 */
	@Override
	public void load(LoggerModel loggerModel) throws LoadFailedException {
		Objects.requireNonNull(loggerModel, "入口参数 loggerModel 不能为 null。");

		try{
			ConfigurationSource cs = new ConfigurationSource(in);
			LoggerContext loggerContext =  Configurator.initialize(null, cs);
			Configuration cfg = loggerContext.getConfiguration();
			Set<String> loggerNames = cfg.getLoggers().keySet();
			
			loggerModel.setLoggerContext(loggerContext);
			loggerModel.setLoggerNames(loggerNames);
		}catch (IOException e) {
			throw new LoadFailedException("无法向指定的记录器模型中读取流中的数据", e);
		}

	}

}
