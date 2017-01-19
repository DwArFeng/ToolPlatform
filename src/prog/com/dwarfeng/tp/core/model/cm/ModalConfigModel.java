package com.dwarfeng.tp.core.model.cm;

/**
 * 模态配置模型。
 * @author DwArFeng
 * @since 1.8
 */
public interface ModalConfigModel extends SyncConfigModel {
	
	/**
	 * 获取主界面的初始化高度。
	 * @return 主界面的初始化高度。
	 */
	public int getMainFrameStartupHeight();
	
	/**
	 * 获取主界面的初始化宽度。
	 * @return 主界面的初始化宽度。
	 */
	public int getMainFrameStartupWidth();
	
	/**
	 * 获取主界面的初始化扩展状态。
	 * @return 主界面的初始化扩展状态。
	 */
	public int getMainFrameStartupExtendedState();

}
