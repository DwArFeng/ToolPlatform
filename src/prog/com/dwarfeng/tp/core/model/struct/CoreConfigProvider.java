package com.dwarfeng.tp.core.model.struct;

import java.util.Locale;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.tp.core.model.cm.SyncConfigModel;

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
	public SyncConfigModel getConfigModel();
	
	/**
	 * ��ȡ��¼�������Խӿڵĵ�ǰ���ԡ�
	 * @return ��¼�������Խӿڵ�ǰ�����ԡ�
	 * @throws IllegalStateException ���ģ����û����Ӧ�ļ���
	 */
	public Locale getLoggerMutilangLocale();
	
	/**
	 * ��ȡ��ǩ�����Խӿڵĵ�ǰ���ԡ�
	 * @return ��ǩ�����Խӿڵĵ�ǰ���ԡ�
	 * @throws IllegalStateException ���ģ����û����Ӧ�ļ���
	 */
	public Locale getLabelMutilangLocale();
	
	/**
	 * ��ȡ��������ʱ�Ƿ���Ҫ��ʾ�������档
	 * @return �Ƿ���Ҫ������ʱ��ʾ�������档
	 * @throws IllegalStateException ���ģ����û����Ӧ�ļ���
	 */
	public boolean isShowSplashScreen();
	
	/**
	 * ��ȡ��������ʱ�������ڵ������ʾʱ�䡣
	 * @return ��������ʱ�������ڵ������ʾʱ�䡣
	 * @throws IllegalStateException ���ģ����û����Ӧ�ļ���
	 */
	public int getStartupSplashDuration();

}
