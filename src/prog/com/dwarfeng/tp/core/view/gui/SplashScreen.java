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

/**
 * 启动窗口。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class SplashScreen extends JFrame{
	
	private static final long serialVersionUID = 7808586865358726896L;
	
	private final JLabel label;

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
	 * 获取启动窗口的信息文本。
	 * @return 启动窗口的信息文本。
	 */
	public String getMessage(){
		return label.getText();
	}
	
	/**
	 * 设置启动窗口的信息文本。
	 * @param text 指定的信息文本，<code>null</code> 相当于 <code>""</code>。
	 * @return 该操作是否改变了启动窗口。
	 */
	public boolean setMessage(String text){
		text = text == null ? "" : text;
		if(Objects.equals(label.getText(), text)) return false;
		this.label.setText(text);
		return true;
	}

}
