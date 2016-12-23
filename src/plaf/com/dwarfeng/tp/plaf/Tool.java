package com.dwarfeng.tp.plaf;

import java.awt.Image;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.plaf.core.ToolIconSize;
import com.dwarfeng.tp.plaf.core.ToolObverser;
import com.dwarfeng.tp.plaf.core.ToolStopMode;

/**
 * 工具。
 * <p> 工具是工具的核心接口，实现该接口的公共非抽象类会被当做一个有效的工具。
 * <br> 程序开始运行的时候，会在工具目录下解析其发现的每一个jar包，jar包中如果有类复合上述条件，
 * 即被添加进工具列表中。
 * <p> 注意：需要保证此方法永远不返回 <code>null</code>。
 * @author DwArFeng
 * @since 1.8
 */
public interface Tool extends ObverserSet<ToolObverser>, Name{
	
	/**
	 * 获取工具中作为图标的图片。
	 * <p> 图片应具有小、中、大三个类型，返回的图片最好符合指定类型的大小，否则的话，图像在显示的时候将进行缩放，
	 * 造成失真。
	 * @param size 图片的大小枚举。
	 * @return 工具中指定大小的图片。
	 */
	public Image getIconImage(ToolIconSize size);
	
	/**
	 * 返回工具的头部图像。
	 * <p> 头部图像用在工具的详细信息窗口框中的标题处，是用来对工具进行描述与宣传的。
	 * 该图像是一个横幅图像 TODO 具体的尺寸。
	 * @return 工具的头部图像。
	 */
	public Image getHeadImage();
	
	/**
	 * 返回作者的名称数组。
	 * <p> 排在数组前方的名字会显示在工具详细信息以及工具
	 * @return 作者名称组成的数组。
	 */
	public String[] getAuthors();
	
	/**
	 * 获取软件的版本。
	 * @return 软件的版本。
	 */
	public Version getVersion();
	
	/**
	 * 以指定的停止方式停止程序。
	 * <p> 工具除了能够自己停止（如用户点击了“关闭”按钮）之外，还应该能够被工具平台停止。
	 * 工具平台的停止应该具有实时性，即无论程序处于何种状态，都应该可以响应该停止操作。
	 * <p> 根据停止方式的不同，该方法还可能需要在一些时候具有强制性，即立即中断工具操作并且退出，无论工作是否完成或者数据是否保存。
	 * @param stopMode 停止方式。
	 */
	public void stop(ToolStopMode stopMode);
	
}
