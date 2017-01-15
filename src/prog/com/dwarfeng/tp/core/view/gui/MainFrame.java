package com.dwarfeng.tp.core.view.gui;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.dwarfeng.tp.core.control.ToolPlatform;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;

/**
 * 程序的主界面。
 * @author  DwArFeng
 * @since 1.8
 */
public final class MainFrame extends JFrame implements MutilangSupported {
	
	
	/**
	 * 调试的时候使用。 
	 */
	public MainFrame() {
		
		try {
			setIconImage(ImageIO.read(ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/image/icon.png")));
		} catch (IOException ignore) {
			//图片资源在包内部，不可能抛出异常。
		}
	}

	@Override
	public Mutilang getMutilang() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setMutilang(Mutilang mutilang) {
		// TODO Auto-generated method stub
		return false;
	}

}
