package com.dwarfeng.tp.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 主界面观察器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface MainFrameObverser extends Obverser{
	
	/**
	 * 通知程序被关闭。
	 */
	public void fireProgramClosed();
	
	

}
