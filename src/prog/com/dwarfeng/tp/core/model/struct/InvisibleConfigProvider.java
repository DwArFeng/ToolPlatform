package com.dwarfeng.tp.core.model.struct;

import com.dwarfeng.tp.core.model.cm.SyncConfigModel;

/**
 * 不可见配置提供器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface InvisibleConfigProvider {
	
	/**
	 * 获取该提供器所引用的配置模型。
	 * @return 该提供器所引用的配置模型。
	 */
	public SyncConfigModel getConfigModel();
	
	/**
	 * 获取主界面的初始化高度。
	 * @return 主界面的初始化高度。
	 * @throws IllegalStateException 如果模型中没有相应的键。
	 */
	public int getMainFrameStartupHeight();
	
	/**
	 * 获取主界面的初始化宽度。
	 * @return 主界面的初始化宽度。
	 * @throws IllegalStateException 如果模型中没有相应的键。
	 */
	public int getMainFrameStartupWidth();
	
	/**
	 * 获取主界面的初始化扩展状态。
	 * @return 主界面的初始化扩展状态。
	 * @throws IllegalStateException 如果模型中没有相应的键。
	 */
	public int getMainFrameStartupExtendedState();

}
