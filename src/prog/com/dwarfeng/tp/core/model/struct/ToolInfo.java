package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.util.Locale;
import java.util.Map;
import com.dwarfeng.dutil.basic.prog.Version;

/**
 * 工具信息。
 * <p> 注意：返回的所有值均不能为 <code>null</code>。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolInfo {
	
	/**
	 * 返回工具中指定类型的图片。
	 * @return 工具中指定类型的图片。
	 * @throws ProcessException 过程异常。
	 */
	public Image getImage() throws ProcessException;
	
	/**
	 * 返回工具的版本。
	 * @return 工具的版本。
	 * @throws ProcessException 过程异常。
	 */
	public Version getVersion() throws ProcessException;
	
	/**
	 * 获取工具中的语言-描述映射。
	 * @return 工具中的语言-描述映射。
	 * @throws ProcessException 过程异常。
	 */
	public Map<Locale, String> getDescriptionMap() throws ProcessException;
	
	/**
	 * TODO 是否要以 Doucument 的形式来返回描述？？
	 * 获取工具的描述。
	 * @param 指定的语言，可以为 <code>null</code>，代表默认语言。
	 * @return 指定的语言下工具的描述。
	 * @throws ProcessException 过程异常。
	 */
	public String getDescription(Locale locale) throws ProcessException;
	
	/**
	 * 获取工具的作者数组。
	 * <p> 作者应该按照贡献程度的大小排序，因为返回的作者数组中，
	 * 靠前的作者更有可能显示在概要界面上。
	 * @return 工具的作者数组。
	 * @throws ProcessException 过程异常。
	 */
	public String[] getAuthors() throws ProcessException;
	
	/**
	 * 获取工具的库列表。
	 * <p> 返回的字符串应该是所需要使用的库文件的文件名，不可带路径。
	 * @return 工具的库列表。
	 * @throws ProcessException 过程异常。
	 */
	public String[] getToolLibs() throws ProcessException;
	
	/**
	 * 获取工具的主类类名。
	 * @return 工具的主类类名。
	 * @throws ProcessException 过程异常。
	 */
	public String getToolClass() throws ProcessException;
	
	/**
	 * 获取工具类的信息类名。
	 * @return 工具类的信息类名。
	 * @throws ProcessException 过程异常。
	 */
	public String getInfoClass() throws ProcessException;
	
	/**
	 * 获取工具的主文件（jar包）名称。
	 * <p> 文件名称为jar包的文件名，不可带上路径。
	 * @return 工具的主文件（jar包）名称。
	 * @throws ProcessException 过程异常。
	 */
	public String getToolFile() throws ProcessException;
	
}
