package com.dwarfeng.tp.core.view.gui;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.dwarfeng.tp.core.control.ToolPlatform;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;

/**
 * ����������档
 * @author  DwArFeng
 * @since 1.8
 */
public final class MainFrame extends JFrame implements MutilangSupported {
	
	
	/**
	 * ���Ե�ʱ��ʹ�á� 
	 */
	public MainFrame() {
		
		try {
			setIconImage(ImageIO.read(ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/image/icon.png")));
		} catch (IOException ignore) {
			//ͼƬ��Դ�ڰ��ڲ����������׳��쳣��
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
