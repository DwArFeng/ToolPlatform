package com.dwarfeng.tp.core.model;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.PlatformLogger;

/**
 * �����ģ�͹�������
 * @author DwArFeng
 * @since 1.8
 */
public interface ModelManager {
	
	/**
	 * ��ȡƽ̨����־��¼����
	 * @return ƽ̨����־��¼����
	 */
	public PlatformLogger getLogger();
	
	/**
	 * ��ȡ��¼���Ķ����Խӿڡ�
	 * @return ��¼���Ķ����Խӿڡ�
	 */
	public Mutilang<LoggerStringKey> getLoggerMutilang();
	
	/**
	 * ��ȡ��ǩ�Ķ����Խӿڡ�
	 * @return ��ǩ�Ķ����Խӿڡ�
	 */
	public Mutilang<LabelStringKey> getLabelMutilang();
	
	/**
	 * ��ȡ����ĺ������á�
	 * @return ����ĺ������á�
	 */
	public ConfigModel getCoreConfigModel();
	
	/**
	 * ��ȡ����Ĳ��ɼ����á�
	 * @return ����Ĳ��ɼ����á�
	 */
	public ConfigModel getInvisibleConfigModel();
}
