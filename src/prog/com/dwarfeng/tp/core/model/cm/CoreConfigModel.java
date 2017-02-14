package com.dwarfeng.tp.core.model.cm;

import java.util.Locale;

/**
 * ��������ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface CoreConfigModel extends SyncConfigModel {
	
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
	
	/**
	 * ��ȡ��������ʱ�Ƿ���Ҫ��ʾ�������档
	 * @return �Ƿ���Ҫ������ʱ��ʾ�������档
	 */
	public boolean isShowSplashScreen();
	
	/**
	 * ��ȡ��������ʱ�������ڵ������ʾʱ�䡣
	 * @return ��������ʱ�������ڵ������ʾʱ�䡣
	 */
	public int getStartupSplashDuration();
	
	/**
	 * ��ȡ�����Ƿ��Զ�ȡ����ɵ������й��ߡ�
	 * @return �����Ƿ��Զ�ȡ����ɵ������й��ߡ�
	 */
	public boolean isRunningToolAutoTake();
	
	/**
	 * ��ȡ������ʷģ�͵�����¼������
	 * @return ������ʷģ�͵�����¼������
	 */
	public int getToolHistoryMaxSize();

}
