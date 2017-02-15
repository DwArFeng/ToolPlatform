package com.dwarfeng.tp.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.eum.ImageSize;

public interface IconSizeProcessorObverser extends Obverser{

	/**
	 * ֪ͨͼ��ĳߴ緢���˸ı䡣
	 * @param oldValue �ɵ�ͼ��ߴ硣
	 * @param newValue �µ�ͼ��ߴ硣
	 */
	public void fireIconSizeChanged(ImageSize oldValue, ImageSize newValue);
	
}
