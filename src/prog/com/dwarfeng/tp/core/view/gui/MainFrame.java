package com.dwarfeng.tp.core.view.gui;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.dwarfeng.tp.core.control.ToolPlatform;

/**
 * ����������档
 * @author  DwArFeng
 * @since 1.8
 */
public final class MainFrame extends JFrame {
	
	
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

}
