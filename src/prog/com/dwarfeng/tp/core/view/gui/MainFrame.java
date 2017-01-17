package com.dwarfeng.tp.core.view.gui;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.dwarfeng.tp.core.control.ToolPlatform;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;
import com.dwarfeng.tp.core.view.obv.MainFrameObverser;
import com.dwarfeng.dutil.basic.gui.swing.JAdjustableBorderPanel;
import com.dwarfeng.dutil.basic.prog.ObverserSet;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 程序的主界面。
 * @author  DwArFeng
 * @since 1.8
 */
public final class MainFrame extends JFrame implements MutilangSupported, ObverserSet<MainFrameObverser>{
	
	/**观察器集合*/
	private final Set<MainFrameObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	/**多语言接口*/
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
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fireProgramToClose();
			}
		});
		
		try {
			setIconImage(ImageIO.read(ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/image/icon.png")));
		} catch (IOException ignore) {
			//图片资源在包内部，不可能抛出异常。
		}
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JAdjustableBorderPanel adjustableBorderPanel = new JAdjustableBorderPanel();
		adjustableBorderPanel.setSeperatorColor(new Color(100, 149, 237));
		adjustableBorderPanel.setNorthMinValue(60);
		adjustableBorderPanel.setNorthPreferredValue(60);
		adjustableBorderPanel.setNorthSeparatorEnabled(false);
		adjustableBorderPanel.setNorthEnabled(true);
		adjustableBorderPanel.setSeperatorThickness(5);
		adjustableBorderPanel.setWestEnabled(true);
		adjustableBorderPanel.setEastEnabled(true);
		getContentPane().add(adjustableBorderPanel, BorderLayout.CENTER);
		
		JAdjustableBorderPanel adjustableBorderPanel_1 = new JAdjustableBorderPanel();
		adjustableBorderPanel_1.setSeperatorColor(new Color(30, 144, 255));
		adjustableBorderPanel_1.setNorthSeparatorEnabled(false);
		adjustableBorderPanel_1.setSouthEnabled(true);
		adjustableBorderPanel_1.setSeperatorThickness(5);
		adjustableBorderPanel.add(adjustableBorderPanel_1, BorderLayout.CENTER);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		adjustableBorderPanel_1.add(tabbedPane, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_3, null);
		
		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "\u5DE5\u5177\u680F", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0))
		);
		adjustableBorderPanel.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{30, 0};
		gbl_panel.rowHeights = new int[]{30, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setPreferredSize(new Dimension(30, 30));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		panel.add(btnNewButton, gbc_btnNewButton);
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

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<MainFrameObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(MainFrameObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(MainFrameObverser obverser) {
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
	
	private void fireProgramToClose() {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireProgramToClose();
		}
	}

}
