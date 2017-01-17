package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;

import com.dwarfeng.tp.core.model.cfg.InvisibleConfig;
import com.dwarfeng.tp.core.model.cm.SyncConfigModel;

/**
 * Ĭ�ϲ��ɼ������ṩ����
 * <p> ���ɼ������ṩ����Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 1.8
 */
public class DefaultInvisibleConfigProvider implements InvisibleConfigProvider {
	
	private final SyncConfigModel configModel;

	public DefaultInvisibleConfigProvider(SyncConfigModel configModel) {
		Objects.requireNonNull(configModel, "��ڲ��� configModel ����Ϊ null��");
		this.configModel = configModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.InvisibleConfigProvider#getConfigModel()
	 */
	@Override
	public SyncConfigModel getConfigModel() {
		return configModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.InvisibleConfigProvider#getMainFrameStartupHeight()
	 */
	@Override
	public int getMainFrameStartupHeight() {
		if(! configModel.containsKey(InvisibleConfig.STARTUP_MAINFRAME_HEIGHT.getConfigKey())){
			throw new IllegalStateException("��֮����ģ����û���ҵ�ָ���ļ�");
		}
		return Integer.parseInt(configModel.getValidValue(InvisibleConfig.STARTUP_MAINFRAME_HEIGHT.getConfigKey()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.InvisibleConfigProvider#getMainFrameStartupWidth()
	 */
	@Override
	public int getMainFrameStartupWidth() {
		if(! configModel.containsKey(InvisibleConfig.STARTUP_MAINFRAME_WIDTH.getConfigKey())){
			throw new IllegalStateException("��֮����ģ����û���ҵ�ָ���ļ�");
		}
		return Integer.parseInt(configModel.getValidValue(InvisibleConfig.STARTUP_MAINFRAME_WIDTH.getConfigKey()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.InvisibleConfigProvider#getMainFrameStartupExtendedState()
	 */
	@Override
	public int getMainFrameStartupExtendedState() {
		if(! configModel.containsKey(InvisibleConfig.STARTUP_MAINFRAME_EXTENDEDSTATE.getConfigKey())){
			throw new IllegalStateException("��֮����ģ����û���ҵ�ָ���ļ�");
		}
		return Integer.parseInt(configModel.getValidValue(InvisibleConfig.STARTUP_MAINFRAME_EXTENDEDSTATE.getConfigKey()));
	}
	
	
	

}
