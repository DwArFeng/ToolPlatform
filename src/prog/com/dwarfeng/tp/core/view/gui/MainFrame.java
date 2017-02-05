package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.cfg.ImageKey;
import com.dwarfeng.tp.core.model.cfg.ImageSize;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cm.BackgroundModel;
import com.dwarfeng.tp.core.model.cm.LibraryModel;
import com.dwarfeng.tp.core.model.cm.ToolInfoModel;
import com.dwarfeng.tp.core.model.cm.ToolRuntimeModel;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;
import com.dwarfeng.tp.core.model.struct.RunningTool;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;
import com.dwarfeng.tp.core.view.obv.MainFrameObverser;
import com.dwarfeng.tp.core.view.obv.ToolInfoPanelObverser;

/**
 * 程序的主界面。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class MainFrame extends JFrame implements MutilangSupported, ObverserSet<MainFrameObverser>{
	
	/**观察器集合*/
	private final Set<MainFrameObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	/*
	 * 所有与多语言有关的对象
	 */
	private final TitledBorder north_border;
	private final JTabbedPane southTabbedPane;
	private final JTabbedPane centerTabbedPane;
	private final JTpconsole console;
	private final JBackgroundPanel backgroundPanel;
	private final JLibraryPanel libraryPanel;
	private final JToolInfoPanel toolInfoPanel;
	private final JToolRuntimePanel toolRuntimePanel;

	/*
	 * 其它final域
	 */
	private final InputStream sysIn = System.in;
	private final PrintStream sysOut = System.out;
	private final PrintStream sysErr = System.err;
	
	/**多语言接口*/
	private Mutilang mutilang;

	/**其它面板的观察器*/
	private ToolInfoPanelObverser toolInfoPanelObverser = new ToolInfoPanelObverser() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.view.obv.ToolInfoPanelObverser#fireRunTool(java.lang.String)
		 */
		@Override
		public void fireRunTool(String name) {
			MainFrame.this.fireRunTool(name);
		}
	};

	/**
	 * 新实例。
	 */
	public MainFrame() {
		this(ToolPlatformUtil.newDefaultLabelMutilang(), null, null, null, null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口。
	 */
	public MainFrame(Mutilang mutilang, 
			BackgroundModel backgroundModel, 
			ToolInfoModel toolInfoModel, 
			LibraryModel libraryModel,
			ToolRuntimeModel toolRuntimeModel
			) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");

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
		
		setIconImage(ToolPlatformUtil.getImage(ImageKey.MAINFRAME_ICON, ImageSize.ICON_SUPER_LARGE));
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
		
		southTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		southTabbedPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		adjustableBorderPanel_1.add(southTabbedPane, BorderLayout.SOUTH);
		
		console = new JTpconsole(mutilang);
		southTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_2),
				new ImageIcon(ToolPlatformUtil.getImage(ImageKey.CONSOLE, ImageSize.ICON_SMALL)), 
				console, null);
		System.setIn(console.in);
		System.setOut(console.out);
		System.setErr(console.out);
		
		backgroundPanel = new JBackgroundPanel(backgroundModel);
		southTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_3),
				new ImageIcon(ToolPlatformUtil.getImage(ImageKey.PROGRESS, ImageSize.ICON_SMALL)), 
				backgroundPanel, null);
		
		centerTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		adjustableBorderPanel_1.add(centerTabbedPane, BorderLayout.CENTER);
		
		toolInfoPanel = new JToolInfoPanel(toolInfoModel);
		toolInfoPanel.addObverser(toolInfoPanelObverser);
		centerTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_4),
				new ImageIcon(ToolPlatformUtil.getImage(ImageKey.TOOL, ImageSize.ICON_SMALL)), 
				toolInfoPanel, null);
		
		libraryPanel = new JLibraryPanel(libraryModel);
		centerTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_5),
				new ImageIcon(ToolPlatformUtil.getImage(ImageKey.LIBRARY, ImageSize.ICON_SMALL)), 
				libraryPanel, null);
		
		toolRuntimePanel = new JToolRuntimePanel(toolRuntimeModel);
		centerTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_6),
				new ImageIcon(ToolPlatformUtil.getImage(ImageKey.RUNTIME, ImageSize.ICON_SMALL)), 
				toolRuntimePanel, null);
		
		JPanel panel = new JPanel();
		
		north_border = new TitledBorder(null, getLabel(LabelStringKey.MainFrame_1), TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0));
		panel.setBorder(north_border);
		adjustableBorderPanel.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{30, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setPreferredSize(new Dimension(30, 30));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		panel.add(btnNewButton, gbc_btnNewButton);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		adjustableBorderPanel.add(tabbedPane_1, BorderLayout.SOUTH);
		
		//pack();
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
		Objects.requireNonNull(mutilang , "入口参数 mutilang 不能为 null。");
		
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
	
	/**
	 * 为指定的运行中工具指定输入流和输出流。
	 * <p> 当且仅当入口参数不为 <code>null</code>，且输入当前的 toolRuntimeModel的时候，才能够指派成功。
	 * @param runningTool 指定的运行中工具。
	 * @return 是否接受该指派。
	 */
	public boolean assignStream(RunningTool runningTool) {
		return toolRuntimePanel.assignStream(runningTool);
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.Window#dispose()
	 */
	@Override
	public void dispose() {
		setVisible(false);
		console.dispose();
		backgroundPanel.dispose();
		toolInfoPanel.removeObverser(toolInfoPanelObverser);
		toolInfoPanel.dispose();
		libraryPanel.dispose();
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

	private void fireRunTool(String name) {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireRunTool(name);
		}
	}

}
