package com.dwarfeng.tp.model.cfg;

/**
 * 程序的启动时外观。
 * @author  DwArFeng
 * @since 1.8
 */
public interface StartupAper {
	
	/**
	 * 获取程序启动时界面的高度。
	 * @return 高度。
	 */
	public int getHeight();
	
	/**
	 * 获取程序启动时界面的宽度。
	 * @return 宽度。
	 */
	public int getWidth();
	
	/**
	 * 获取程序启动时界面的延伸状态。
	 * @return 延伸状态。
	 */
	public int getExtendedState();

}
