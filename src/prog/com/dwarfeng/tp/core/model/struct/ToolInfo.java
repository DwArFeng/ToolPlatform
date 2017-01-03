package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;

import com.dwarfeng.dutil.basic.prog.Version;

/**
 * 工具信息。
 * <p> 注意：返回的所有值均不能为 <code>null</code>。
 * @author DwArFeng
 * @since 1.8
 */
public interface ToolInfo {

	/**
	 * 返回工具中指定类型的图片。
	 * @param type 图片类型。
	 * @return 工具中指定类型的图片。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public Image getImage(ToolImageType type);
	
	/**
	 * 返回工具的版本。
	 * @return 工具的版本。
	 */
	public Version getVersion();
	
	/**
	 * TODO 是否要以 Doucument 的形式来返回描述？？
	 * 获取工具的描述。
	 * @return 工具的描述。
	 */
	public String getDescription();
	
	/**
	 * 获取工具的作者数组。
	 * <p> 作者应该按照贡献程度的大小排序，因为返回的作者数组中，
	 * 靠前的作者更有可能显示在概要界面上。
	 * @return 工具的作者数组。
	 */
	public String[] getAuthors();
	
	/**
	 * 获取工具的主类类名。
	 * @return 工具的主类类名。
	 */
	public String getToolClass();
	
	/**
	 * 获取工具的主文件（jar包）名称。
	 * <p> 文件名称为jar包的文件名，不可带上路径。
	 * @return 工具的主文件（jar包）名称。
	 */
	public String getToolFile();
	
	/**
	 * 获取工具的库列表。
	 * <p> 返回的字符串应该是所需要使用的库文件的文件名，不可带路径。
	 * @return 工具的库列表。
	 */
	public String[] getToolLibs();
	
}
