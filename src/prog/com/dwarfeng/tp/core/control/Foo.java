package com.dwarfeng.tp.core.control;

import java.io.InputStream;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;

import com.dwarfeng.dutil.basic.io.CT;

public final class Foo {

	public static void main(String[] args) throws Exception{
		InputStream in = ToolPlatform.class.getResourceAsStream("/com/dwarfeng/tp/resource/defaultres/logger/setting.xml");
		ConfigurationSource cs = new ConfigurationSource(in);
		Configuration cfg = Configurator.initialize(null, cs).getConfiguration();
		CT.trace(cfg.getLoggers().keySet());
	}

}
