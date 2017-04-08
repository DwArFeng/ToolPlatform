package com.dwarfeng.tp.core.view.struct;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.eum.ImageSize;
import com.dwarfeng.tp.core.model.eum.LabelStringKey;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;
import com.dwarfeng.tp.core.util.Constants;
import com.dwarfeng.tp.core.view.obv.IconSizeProcessorObverser;

/**
 * 图标大小处理器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class IconSizeProcessor implements MutilangSupported, ObverserSet<IconSizeProcessorObverser>{
	
	private final Set<IconSizeProcessorObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	private final Action iconSizeAction = new IconSizeAction();
	
	private Mutilang mutilang;
	private ImageSize iconSize = ImageSize.ICON_MEDIUM;
	
	private final JMenu viewMenu;
	private final ButtonGroup buttonGroup;
	private final JRadioButtonMenuItem small;
	private final JRadioButtonMenuItem medium;
	private final JRadioButtonMenuItem large;

	
	public IconSizeProcessor() {
		this(Constants.getDefaultLabelMutilang());
	}
	
	public IconSizeProcessor(Mutilang mutilang) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
		
		viewMenu = new JMenu();
		viewMenu.setName(getLabel(LabelStringKey.IconSizeProcessor_1));
		viewMenu.setMnemonic('V');
		
		small = new JRadioButtonMenuItem();
		small.setName(getLabel(LabelStringKey.IconSizeProcessor_2));
		small.setMnemonic('S');
		small.setAction(iconSizeAction);
		
		viewMenu.add(small);
		
		medium = new JRadioButtonMenuItem();
		medium.setName(getLabel(LabelStringKey.IconSizeProcessor_3));
		medium.setMnemonic('M');
		medium.setAction(iconSizeAction);
		
		viewMenu.add(medium);
		
		large = new JRadioButtonMenuItem();
		large.setName(getLabel(LabelStringKey.IconSizeProcessor_4));
		large.setMnemonic('L');
		large.setAction(iconSizeAction);
		
		viewMenu.add(large);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(small);
		buttonGroup.add(medium);
		buttonGroup.add(large);
	}

	/**
	 * 获得图标的大小。
	 * @return 图标的大小。
	 */
	public ImageSize getIconSize() {
		return iconSize;
	}

	/**
	 * 设置图标的大小。
	 * @param iconSize 图标的大小。
	 * @return 是否设置成功。
	 */
	public boolean setIconSize(ImageSize iconSize) {
		switch (iconSize) {
		case ICON_LARGE:
			break;
		case ICON_MEDIUM:
			break;
		case ICON_SMALL:
			break;
		default:
			return false;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangSupported#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		return mutilang;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangSupported#setMutilang(com.dwarfeng.tp.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setMutilang(Mutilang mutilang) {
		this.mutilang = mutilang;
		viewMenu.setName(getLabel(LabelStringKey.IconSizeProcessor_1));
		small.setName(getLabel(LabelStringKey.IconSizeProcessor_2));
		medium.setName(getLabel(LabelStringKey.IconSizeProcessor_3));
		large.setName(getLabel(LabelStringKey.IconSizeProcessor_4));
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<IconSizeProcessorObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(IconSizeProcessorObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(IconSizeProcessorObverser obverser) {
		return obversers.remove(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#clearObverser()
	 */
	@Override
	public void clearObverser() {
		obversers.clear();
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}
	
	private void fireIconSizeChanged(ImageSize oldValue, ImageSize newValue){
		for(IconSizeProcessorObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireIconSizeChanged(oldValue, newValue);
		}
	}
	
	private final class IconSizeAction extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
