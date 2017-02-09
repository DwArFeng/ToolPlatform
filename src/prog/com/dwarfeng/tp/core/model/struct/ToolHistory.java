package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.util.Date;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 工具历史。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolHistory extends Name{

	/**
	 * 返回工具历史中的图片。
	 * @return 工具中的图片。
	 */
	public Image getImage();
	
	/**
	 * 获取工具的运行时间。
	 * @return 工具的运行时间。
	 */
	public Date getRunedTime();
	
	/**
	 * 获取工具的结束时间。
	 * @return 工具的结束时间。
	 */
	public Date getExitedTime();
	
}
