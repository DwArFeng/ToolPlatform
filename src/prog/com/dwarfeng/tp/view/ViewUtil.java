package com.dwarfeng.tp.view;

import java.util.Objects;

import com.dwarfeng.tp.model.setting.InternationalObverser;
import com.dwarfeng.tp.view.gui.InternationalAper;

/**
 * ��ͼ��һЩ���ܡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class ViewUtil {

	/**
	 * ��ȡһ��ͨ��ָ�����ʻ�������ɵĹ��ʻ��۲�����
	 * @param aper ָ���Ĺ��ʻ���ۡ�
	 * @return ͨ��ָ���Ĺ��ʻ�������ɵĹ��ʻ��۲�����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static InternationalObverser newInternationalObverser(InternationalAper aper){
		Objects.requireNonNull(aper, "��ڲ��� aper ����Ϊ null");
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
	
	//��ֹ�ⲿʵ����
	private ViewUtil(){}
}
