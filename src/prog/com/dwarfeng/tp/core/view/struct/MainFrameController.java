package com.dwarfeng.tp.core.view.struct;

import java.awt.Component;

import com.dwarfeng.tp.core.view.gui.MainFrame;

/**
 * 主界面控制器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface MainFrameController extends MutilangSupportedGuiController<MainFrame>{
	
	/**
	 * 获取主界面的高度。
	 * <p> 如果主界面还未初始化，则返回 <code>-1</code>
	 * @return 主界面的高度。
	 */
	public int getHeight();
	
	/**
	 * 获取主界面的宽度。
	 * <p> 如果主界面还未初始化，则返回 <code>-1</code>。
	 * @return 主界面的宽度。
	 */
	public int getWidth();
	
	/**
	 * 设置主界面的高度。
	 * @param height 主界面的高度。
	 * @return 该操作是否对主界面造成了改变。
	 */
	public boolean setHeight(int height);
	
	/**
	 * 设置主界面的宽度。
	 * @param width 主界面的宽度。
	 * @return 该操作是否对主界面造成了改变。
	 */
	public boolean setWidth(int width);

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
}
