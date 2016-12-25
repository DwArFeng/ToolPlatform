package com.dwarfeng.tp.model.io;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Logger;

/**
 * 国际化配置读取器。
 * @author DwArFeng
 * @since 1.8
 */
public interface InternationalLoader {
	
	/**
	 * 读取
	 * @return
	 */
	public Map<Locale, ResourceBundle> loadInternationalMap(String path, Logger logger);

}
