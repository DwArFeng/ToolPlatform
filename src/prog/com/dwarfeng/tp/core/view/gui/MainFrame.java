package com.dwarfeng.tp.core.view.gui;

import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.dwarfeng.tp.core.control.ToolPlatform;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

/**
 * 程序的主界面。
 * @author  DwArFeng
 * @since 1.8
 */
public final class MainFrame extends JFrame implements MutilangSupported {
	
	private Mutilang mutilang;
	
	/**
	 * 新实例。
	 */
	public MainFrame() {
		this(ToolPlatformUtil.newDefaultLabelMutilang());
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口。
	 */
	public MainFrame(Mutilang mutilang) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		try {
			setIconImage(ImageIO.read(ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/image/icon.png")));
		} catch (IOException ignore) {
			//图片资源在包内部，不可能抛出异常。
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangSupported#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		return this.mutilang;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangSupported#setMutilang(com.dwarfeng.tp.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setMutilang(Mutilang mutilang) {
		if(Objects.equals(mutilang, this.mutilang)) return false;
		this.mutilang = mutilang;
		return true;
	}

}
