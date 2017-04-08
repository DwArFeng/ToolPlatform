package com.dwarfeng.tp.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.eum.ImageSize;

public interface IconSizeProcessorObverser extends Obverser{

	/**
	 * 通知图标的尺寸发生了改变。
	 * @param oldValue 旧的图标尺寸。
	 * @param newValue 新的图标尺寸。
	 */
	public void fireIconSizeChanged(ImageSize oldValue, ImageSize newValue);
	
}
