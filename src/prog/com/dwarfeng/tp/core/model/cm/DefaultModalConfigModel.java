package com.dwarfeng.tp.core.model.cm;

import com.dwarfeng.tp.core.model.cfg.ModalConfig;

/**
 * 默认模态配置模型。
 * <p> 模态配置模型的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultModalConfigModel extends DefaultSyncConfigModel implements ModalConfigModel {
	
	/**
	 * 新实例。
	 */
	public DefaultModalConfigModel() {
		super(ModalConfig.values());
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ModalConfigModel#getMainFrameStartupHeight()
	 */
	@Override
	public int getMainFrameStartupHeight() {
		lock.readLock().lock();
		try{
			return Integer.parseInt(getValidValue(ModalConfig.STARTUP_MAINFRAME_HEIGHT.getConfigKey()));
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ModalConfigModel#getMainFrameStartupWidth()
	 */
	@Override
	public int getMainFrameStartupWidth() {
		lock.readLock().lock();
		try{
			return Integer.parseInt(getValidValue(ModalConfig.STARTUP_MAINFRAME_WIDTH.getConfigKey()));
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ModalConfigModel#getMainFrameStartupExtendedState()
	 */
	@Override
	public int getMainFrameStartupExtendedState() {
		lock.readLock().lock();
		try{
			return Integer.parseInt(getValidValue(ModalConfig.STARTUP_MAINFRAME_EXTENDEDSTATE.getConfigKey()));
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ModalConfigModel#getMainFrameStartupSouthHeight()
	 */
	@Override
	public int getMainFrameStartupSouthHeight() {
		lock.readLock().lock();
		try{
			return Integer.parseInt(getValidValue(ModalConfig.STARTUP_MAINFRAME_SOUTHHEIGHT.getConfigKey()));
		}finally {
			lock.readLock().unlock();
		}
	}

}
