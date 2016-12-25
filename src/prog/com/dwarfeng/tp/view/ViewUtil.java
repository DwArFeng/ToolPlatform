package com.dwarfeng.tp.view;

import java.util.Objects;

import com.dwarfeng.tp.model.setting.InternationalObverser;
import com.dwarfeng.tp.view.gui.InternationalAper;

/**
 * 视图的一些功能。
 * @author DwArFeng
 * @since 1.8
 */
public final class ViewUtil {

	/**
	 * 获取一个通过指定国际化外观生成的国际化观察器。
	 * @param aper 指定的国际化外观。
	 * @return 通过指定的国际化外观生成的国际化观察器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static InternationalObverser newInternationalObverser(InternationalAper aper){
		Objects.requireNonNull(aper, "入口参数 aper 不能为 null");
		return new InternationalAperObverser(aper);
	}
	
	private static final class InternationalAperObverser implements InternationalObverser{

		private final InternationalAper aper;
		
		public InternationalAperObverser(InternationalAper aper) {
			this.aper = aper;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.model.setting.sub.InternationalObverser#fireLanguageChanged()
		 */
		@Override
		public void fireLanguageChanged() {
			aper.refreshLabels();
		}
		
	}
	
	//禁止外部实例化
	private ViewUtil(){}
}
