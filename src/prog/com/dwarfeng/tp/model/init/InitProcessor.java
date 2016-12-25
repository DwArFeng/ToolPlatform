package com.dwarfeng.tp.model.init;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Logger;

import com.dwarfeng.tp.model.io.InternationalLoader;

/**
 * 初始化处理器。
 * @author DwArFeng
 * @since 1.8
 */
public final class InitProcessor {
	
	private final Logger logger;
	
	private final InternationalLoader internationalLoader;
	
	private final class InnerInternationalLoader implements InternationalLoader{

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.io.InternationalLoader#loadInternationalMap(java.lang.String, org.apache.logging.log4j.Logger)
		 */
		@Override
		public Map<Locale, ResourceBundle> loadInternationalMap(String path, Logger logger) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	/**
	 * 新实例。
	 */
	public InitProcessor() {
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}
	
}
