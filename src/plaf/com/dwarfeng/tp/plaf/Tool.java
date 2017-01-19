package com.dwarfeng.tp.plaf;

import java.awt.Image;
import java.util.Locale;
import java.util.Map;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.struct.ToolImageType;
import com.dwarfeng.tp.plaf.core.FileManager;
import com.dwarfeng.tp.plaf.core.ToolObverser;
import com.dwarfeng.tp.plaf.core.ToolStopMode;

/**
 * 工具。
 * <p> 工具是工具的核心接口，实现该接口的公共非抽象类会被当做一个有效的工具。
 * <br> 程序开始运行的时候，会在工具目录下解析其发现的每一个jar包，jar包中如果有类复合上述条件，
 * 即被添加进工具列表中。
 * <p> 注意：需要保证此方法永远不返回 <code>null</code>。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface Tool extends ObverserSet<ToolObverser>, Name{
	
	/**
	 * 返回工具中指定类型的图片。
	 * @param type 图片类型。
	 * @return 工具中指定类型的图片。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public Image getImage(ToolImageType type);
	
	/**
	 * 获取软件的版本。
	 * @return 软件的版本。
	 */
	public Version getVersion();
	
	/**
	 * TODO 是否要以 Doucument 的形式来返回描述？？
	 * 获取工具的描述。
	 * <p> 返回的映射中应包含一个键为 <code>null</code>的入口，这个入口代表着默认的描述。
	 * @param 指定的语言，可以为 <code>null</code>，代表默认语言。
	 * @return 指定的语言下工具的描述。
	 */
	public Map<Locale, String> getDescriptions();
	
	/**
	 * 获取工具的默认描述。
	 * @return 工具的默认描述。
	 */
	public String getDefaultDescription();
	
	/**
	 * 获取工具的作者数组。
	 * <p> 作者应该按照贡献程度的大小排序，因为返回的作者数组中，
	 * 靠前的作者更有可能显示在概要界面上。
	 * @return 工具的作者数组。
	 */
	public String[] getAuthors();
	
	/**
	 * 获取工具的库列表。
	 * <p> 返回的字符串应该是所需要使用的库文件的文件名，不可带路径。
	 * @return 工具的库列表。
	 */
	public String[] getToolLibs();
	
	/**
	 * 以指定的停止方式停止程序。
	 * <p> 工具除了能够自己停止（如用户点击了“关闭”按钮）之外，还应该能够被工具平台停止。
	 * 工具平台的停止应该具有实时性，即无论程序处于何种状态，都应该可以响应该停止操作。
	 * <p> 根据停止方式的不同，该方法还可能需要在一些时候具有强制性，即立即中断工具操作并且退出，无论工作是否完成或者数据是否保存。
	 * @param stopMode 停止方式。
	 */
	public void stop(ToolStopMode stopMode);
	
	/**
	 * 启动工具。
	 * @param fileManager 指定的文件管理器。
	 * 
	 */
	public void start(FileManager fileManager);
	
}
