package com.dwarfeng.tp.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.RunningTool;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * 主界面观察器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface MainFrameObverser extends Obverser{
	
	/**
	 * 通知界面的关闭按钮被点击。
	 */
	public void fireWindowClosing();

	/**
	 * 通知界面被激活。
	 */
	public void fireFireWindowActivated();
	
	/**
	 * 通知需要运行指定的工具。
	 * @param toolInfo 指定的工具。
	 */
	public void fireRunTool(ToolInfo toolInfo);
	
	/**
	 * 记录指定运行中工具的运行日志。
	 * @param runningTool 指定的运行中工具
	 */
	public void fireLogRunningTool(RunningTool runningTool);
	
}
