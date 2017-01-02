package com.dwarfeng.tp.core.model.io;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.cm.LoggerModel;

/**
 * xml��¼��ģ�Ͷ�ȡ����
 * <p> ʹ��xml��ȡ������ģ�͡�
 * @author  DwArFeng
 * @since 1.8
 */
public final class XmlLoggerLoader extends StreamLoggerLoader {

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
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

		try{
			ConfigurationSource cs = new ConfigurationSource(in);
			LoggerContext loggerContext =  Configurator.initialize(null, cs);
			
			loggerModel.setLoggerContext(loggerContext);
			
			Configuration cfg = loggerContext.getConfiguration();
			loggerModel.addAll(cfg.getLoggers().keySet());
		}catch (IOException e) {
			throw new LoadFailedException("�޷���ָ���ļ�¼��ģ���ж�ȡ���е�����", e);
		}

	}

}
