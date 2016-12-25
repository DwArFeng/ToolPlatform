package com.dwarfeng.tp.model.io;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Logger;

/**
 * ���ʻ����ö�ȡ����
 * @author DwArFeng
 * @since 1.8
 */
public interface InternationalLoader {
	
	/**
	 * ��ȡ
	 * @return
	 */
	public Map<Locale, ResourceBundle> loadInternationalMap(String path, Logger logger);

}
