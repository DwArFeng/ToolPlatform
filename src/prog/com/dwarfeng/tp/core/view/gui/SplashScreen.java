package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.dwarfeng.dutil.basic.gui.swing.JImagePanel;
import com.dwarfeng.tp.core.control.ToolPlatform;
import com.dwarfeng.tp.core.view.ctrl.SplashScreenController;

public class SplashScreen extends JFrame{
	
	private static final long serialVersionUID = 17078290519655271L;

	private final JLabel label;
	
	private final SplashScreenController splashScreenController = new SplashScreenController() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.view.ctrl.SplashScreenController#setText(java.lang.String)
		 */
		@Override
		public void setText(String text) {
			if(Objects.isNull(text)) text = "";
			label.setText(text);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.view.ctrl.SplashScreenController#dispose()
		 */
		@Override
		public void dispose() {
			SplashScreen.this.dispose();
		}
	};

	/**
	 * 新实例。
	 */
	public SplashScreen() {
		setAlwaysOnTop(true);
		setUndecorated(true);
		setBounds(new Rectangle(0, 0, 400, 250));
		setLocationRelativeTo(null);
		
		JImagePanel imagePanel = new JImagePanel();
		//imagePanel.setImage(Toolkit.getDefaultToolkit().getImage(SplashScreen.class.getResource("/com/dwarfeng/tp/resource/image/splash-screen.png")));
		imagePanel.setAutoResize(true);
		setContentPane(imagePanel);
		try {
			imagePanel.setImage(ImageIO.read(ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/image/splash-screen.png")));
		} catch (IOException ignore) {
			//由于图片在内部，不可能抛出异常。
		}
		imagePanel.setLayout(new BorderLayout(0, 0));
		
		label = new JLabel();
		label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		imagePanel.add(label, BorderLayout.SOUTH);
	}

	/**
	 * @return the splashScreenController
	 */
	public SplashScreenController getSplashScreenController() {
		return splashScreenController;
	}

}
