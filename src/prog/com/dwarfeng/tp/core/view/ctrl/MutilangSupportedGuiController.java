package com.dwarfeng.tp.core.view.ctrl;

import java.awt.Component;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;

/**
 * ������֧��ͼ�ν��������������
 * <p>֧�ֶ����Ե�ͼ�ν��������������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface MutilangSupportedGuiController<T extends Component & MutilangSupported> 
extends GuiController<T>, MutilangSupported{
	
}
