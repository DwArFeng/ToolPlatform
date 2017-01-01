package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.dwarfeng.dutil.basic.gui.swing.JImagePanel;

public class SplashScreen extends JFrame {
	private JLabel label;

	/**
	 * 新实例。
	 */
	public SplashScreen() {
		setAlwaysOnTop(true);
		setUndecorated(true);
		setBounds(new Rectangle(0, 0, 400, 250));
		
		JImagePanel imagePanel = new JImagePanel();
		imagePanel.setAutoResize(true);
		setContentPane(imagePanel);
		imagePanel.setImage(Toolkit.getDefaultToolkit().getImage(SplashScreen.class.getResource("/com/dwarfeng/tp/resource/image/splash-screen.png")));
		imagePanel.setLayout(new BorderLayout(0, 0));
		
		label = new JLabel("New label中文效果");
		label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		imagePanel.add(label, BorderLayout.SOUTH);
	}

}
