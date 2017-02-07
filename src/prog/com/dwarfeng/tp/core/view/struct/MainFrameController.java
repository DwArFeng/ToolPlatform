package com.dwarfeng.tp.core.view.struct;

import java.awt.Component;

import com.dwarfeng.tp.core.model.struct.RunningTool;
import com.dwarfeng.tp.core.view.gui.MainFrame;

/**
 * 主界面控制器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface MainFrameController extends MutilangSupportedGuiController<MainFrame>{
	
	/**
	 * 获取主界面最后的正常状态的高度。
	 * <p> 如果主界面还未初始化，则返回 <code>-1</code>
	 * @return 主界面最后的正常状态的高度。
	 */
	public int getLastNormalHeight();
	
	/**
	 * 获取主界面的最后的正常状态的宽度。
	 * <p> 如果主界面还未初始化，则返回 <code>-1</code>。
	 * @return 主界面的最后的正常状态的宽度。
	 */
	public int getLastNormalWidth();
	
	/**
	 * 设置主界面的最后的正常状态的高度。
	 * @param height 主界面的最后的正常状态的高度。
	 * @return 该操作是否对主界面造成了改变。
	 */
	public boolean setLastNormalHeight(int height);
	
	/**
	 * 设置主界面的最后的正常状态的宽度。
	 * @param width 主界面的最后的正常状态的宽度。
	 * @return 该操作是否对主界面造成了改变。
	 */
	public boolean setLastNormalWidth(int width);

	/**
	 * 获取主界面的扩展模式。
	 * <p> 如果主界面还未实例化，则返回 <code>-1</code>。
	 * @return 主界面的扩展模式。
	 */
	public int getExtendedState();
	
	/**
	 * 设置主界面的扩展模式。
	 * @param state 主界面的扩展模式。
	 * @return 该操作是否对主界面造成了改变。
	 */
	public boolean setExtendedState(int state);
	
	/**
	 * 设置窗口相对于指定组件的位置。
	 * @param component 指定的组件。
	 * @return 该操作是否改变了控制器中的组件。
	 */
	public boolean setLocationRelativeTo(Component component);
	
	/**
	 * 为指定的运行中工具指定输入流和输出流。
	 * <p> 当且仅当入口参数不为 <code>null</code>，且输入当前的 toolRuntimeModel的时候，才能够指派成功。
	 * @param runningTool 指定的运行中工具。
	 * @return 是否接受该指派。
	 */
	public boolean assignStream(RunningTool runningTool);
	
	/**
	 * 获取南方面板的高度。
	 * @return 南方面板的高度。
	 */
	public int getSouthHeight();
	
	/**
	 * 设置南方面板的高度。
	 * @param height 指定的高度。
	 * @return 该操作是否改变了控制器中的组件。
	 */
	public boolean setSouthHeight(int height);
}
