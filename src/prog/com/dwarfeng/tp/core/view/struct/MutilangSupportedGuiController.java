package com.dwarfeng.tp.core.view.struct;

import java.awt.Component;

import com.dwarfeng.tp.core.model.struct.MutilangSupported;

/**
 * ������֧��ͼ�ν��������������
 * <p>֧�ֶ����Ե�ͼ�ν��������������
 * @author DwArFeng
 * @since 1.8
 */
public interface MutilangSupportedGuiController<T extends Component & MutilangSupported> 
extends GuiController<T>, MutilangSupported{

}