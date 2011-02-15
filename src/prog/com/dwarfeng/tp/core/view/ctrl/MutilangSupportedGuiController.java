package com.dwarfeng.tp.core.view.ctrl;

import java.awt.Component;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;

/**
 * 多语言支持图形交互界面控制器。
 * <p>支持多语言的图形交互界面控制器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface MutilangSupportedGuiController<T extends Component & MutilangSupported> 
extends GuiController<T>, MutilangSupported{
	
}
