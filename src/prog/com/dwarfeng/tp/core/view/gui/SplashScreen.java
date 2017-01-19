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
 * �������ڡ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class SplashScreen extends JFrame{
	
	private static final long serialVersionUID = 7808586865358726896L;
	
	private final JLabel label;

	/**
	 * ��ʵ����
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
			//����ͼƬ���ڲ����������׳��쳣��
		}
		imagePanel.setLayout(new BorderLayout(0, 0));
		
		label = new JLabel();
		label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		imagePanel.add(label, BorderLayout.SOUTH);
	}
	
	/**
	 * ��ȡ�������ڵ���Ϣ�ı���
	 * @return �������ڵ���Ϣ�ı���
	 */
	public String getMessage(){
		return label.getText();
	}
	
	/**
	 * �����������ڵ���Ϣ�ı���
	 * @param text ָ������Ϣ�ı���<code>null</code> �൱�� <code>""</code>��
	 * @return �ò����Ƿ�ı����������ڡ�
	 */
	public boolean setMessage(String text){
		text = text == null ? "" : text;
		if(Objects.equals(label.getText(), text)) return false;
		this.label.setText(text);
		return true;
	}

}
