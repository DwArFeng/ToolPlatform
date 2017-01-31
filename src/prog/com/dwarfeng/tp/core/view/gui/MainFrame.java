package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import com.dwarfeng.dutil.basic.gui.swing.JAdjustableBorderPanel;
import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.cfg.ImageKey;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cm.BackgroundModel;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;
import com.dwarfeng.tp.core.view.obv.MainFrameObverser;

/**
 * ����������档
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class MainFrame extends JFrame implements MutilangSupported, ObverserSet<MainFrameObverser>{
	
	/**�۲�������*/
	private final Set<MainFrameObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	
	/*
	 * ������������йصĶ���
	 */
	private final TitledBorder north_border;
	private final JTabbedPane southTabbedPane;
	private final JTabbedPane centerTabbedPane;
	private final JTpconsole console;
	private final JBackgroundPanel backgroundPanel;
	private final JLibraryPanel libraryPanel;
	
	/*
	 * ����final��
	 */
	private final InputStream sysIn = System.in;
	private final PrintStream sysOut = System.out;
	private final PrintStream sysErr = System.err;
	
	/**�����Խӿ�*/
	private Mutilang mutilang;
	
	/**
	 * ��ʵ����
	 */
	public MainFrame() {
		this(ToolPlatformUtil.newDefaultLabelMutilang(), null);
	}
	
	/**
	 * ��ʵ����
	 * @param mutilang ָ���Ķ����Խӿڡ�
	 */
	public MainFrame(Mutilang mutilang, BackgroundModel backgroundModel) {
		Objects.requireNonNull(mutilang, "��ڲ��� mutilang ����Ϊ null��");

		this.mutilang = mutilang;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fireFireWindowClosing();
			}
			@Override
			public void windowActivated(WindowEvent e) {
				fireFireWindowActivated();
			}
		});
		
		setIconImage(ToolPlatformUtil.getImage(ImageKey.MainFrame_Icon));
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
		adjustableBorderPanel_1.setSouthPreferredValue(200);
		adjustableBorderPanel_1.setSeperatorColor(new Color(30, 144, 255));
		adjustableBorderPanel_1.setNorthSeparatorEnabled(false);
		adjustableBorderPanel_1.setSouthEnabled(true);
		adjustableBorderPanel_1.setSeperatorThickness(5);
		adjustableBorderPanel.add(adjustableBorderPanel_1, BorderLayout.CENTER);
		
		southTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		southTabbedPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		adjustableBorderPanel_1.add(southTabbedPane, BorderLayout.SOUTH);
		
		console = new JTpconsole(mutilang);
		southTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_2),
				new ImageIcon(ToolPlatformUtil.getImage(ImageKey.Console)), 
				console, null);
		System.setIn(console.in);
		System.setOut(console.out);
		System.setErr(console.out);
		
		backgroundPanel = new JBackgroundPanel(backgroundModel);
		southTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_3),
				new ImageIcon(ToolPlatformUtil.getImage(ImageKey.Progress)), 
				backgroundPanel, null);
		
		JPanel panel_3 = new JPanel();
		southTabbedPane.addTab("New tab", null, panel_3, null);
		
		centerTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		adjustableBorderPanel_1.add(centerTabbedPane, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		centerTabbedPane.addTab("New tab", null, panel_4, null);
		
		libraryPanel = new JLibraryPanel();
		centerTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_5),
				new ImageIcon(ToolPlatformUtil.getImage(ImageKey.Library)), 
				libraryPanel, null);
		
		JPanel panel = new JPanel();
		
		north_border = new TitledBorder(null, getLabel(LabelStringKey.MainFrame_1), TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0));
		panel.setBorder(north_border);
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
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		adjustableBorderPanel.add(tabbedPane_1, BorderLayout.SOUTH);
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
		Objects.requireNonNull(mutilang , "��ڲ��� mutilang ����Ϊ null��");
		
		if(Objects.equals(mutilang, this.mutilang)) return false;
		this.mutilang = mutilang;
		north_border.setTitle(getLabel(LabelStringKey.MainFrame_1));
		southTabbedPane.setTitleAt(0, getLabel(LabelStringKey.MainFrame_2));
		southTabbedPane.setTitleAt(1, getLabel(LabelStringKey.MainFrame_3));
		centerTabbedPane.setTitleAt(1, getLabel(LabelStringKey.MainFrame_5));
		console.setMutilang(mutilang);
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
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.Window#dispose()
	 */
	@Override
	public void dispose() {
		console.dispose();
		System.setIn(sysIn);
		System.setOut(sysOut);
		System.setErr(sysErr);
		super.dispose();
	}
	
	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}
	
	private void fireFireWindowClosing() {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireWindowClosing();
		}
	}

	private void fireFireWindowActivated() {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireFireWindowActivated();
		}
	}

}
