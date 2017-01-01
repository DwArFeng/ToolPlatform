package com.dwarfeng.tp.core.model.struct;

import java.util.Locale;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;

/**
 * ���������ṩ����
 * @author  DwArFeng
 * @since 1.8
 */
public interface CoreConfigProvider {
	
	/**
	 * ��ȡ���ṩ�������õ�����ģ�͡�
	 * @return ���ṩ�������õ�����ģ�͡�
	 */
	public ConfigModel getConfigModel();
	
	/**
	 * ��ȡ��¼�������Խӿڵĵ�ǰ���ԡ�
	 * @return ��¼�������Խӿڵ�ǰ�����ԡ�
	 */
	public Locale getLoggerMutilangLocale();
	
	/**
	 * ��ȡ��ǩ�����Խӿڵĵ�ǰ���ԡ�
	 * @return ��ǩ�����Խӿڵĵ�ǰ���ԡ�
	 */
	public Locale getLabelMutilangLocale();

}
