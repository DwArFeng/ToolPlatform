package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.cfg.ImageKey;
import com.dwarfeng.tp.core.model.cfg.ImageSize;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cm.BackgroundModel;
import com.dwarfeng.tp.core.model.cm.LibraryModel;
import com.dwarfeng.tp.core.model.cm.ToolHistoryModel;
import com.dwarfeng.tp.core.model.cm.ToolInfoModel;
import com.dwarfeng.tp.core.model.cm.ToolRuntimeModel;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;
import com.dwarfeng.tp.core.model.struct.RunningTool;
import com.dwarfeng.tp.core.model.struct.ToolInfo;
import com.dwarfeng.tp.core.util.ImageUtil;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;
import com.dwarfeng.tp.core.view.obv.MainFrameObverser;
import com.dwarfeng.tp.core.view.obv.ToolInfoPanelObverser;
import com.dwarfeng.tp.core.view.obv.ToolRuntimePanelObverser;

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
	private final JToolHistoryPanel toolHistoryPanel;

	/*
	 * 其它final域
	 */
	private final InputStream sysIn = System.in;
	private final PrintStream sysOut = System.out;
	private final PrintStream sysErr = System.err;
	private final JAdjustableBorderPanel mainAdjPanel;
	private final JAdjustableBorderPanel centerAdjPanel;

	/**多语言接口*/
	private Mutilang mutilang;

	/**其它面板的观察器*/
	private final ToolInfoPanelObverser toolInfoPanelObverser = new ToolInfoPanelObverser() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.view.obv.ToolInfoPanelObverser#fireRunTool(com.dwarfeng.tp.core.model.struct.ToolInfo)
		 */
		@Override
		public void fireRunTool(ToolInfo toolInfo) {
			MainFrame.this.fireRunTool(toolInfo);
		}
	};
	private final ToolRuntimePanelObverser runtimePanelObverser = new ToolRuntimePanelObverser() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.view.obv.ToolRuntimePanelObverser#fireLogRunningTool(com.dwarfeng.tp.core.model.struct.RunningTool)
		 */
		@Override
		public void fireLogRunningTool(RunningTool runningTool) {
			MainFrame.this.fireLogRunningTool(runningTool);
		}
	};
	
	/*
	 * 其它非final域
	 */
	private int lastNormalHeight;
	private int lastNormalWidth;
	private JTabbedPane tabbedPane;
	private JButton btnNewButton_1;
	private JPopupMenu popupMenu;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem menuItem;
	private JMenuItem menuItem_1;
	private JMenuItem menuItem_2;
	private JMenuItem menuItem_3;
	private JMenuItem menuItem_4;
	private JMenuItem menuItem_5;
	private JMenuItem menuItem_6;
	
	/**
	 * 新实例。
	 */
	public MainFrame() {
		this(ToolPlatformUtil.newDefaultLabelMutilang(), null, null, null, null, null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口。
	 */
	public MainFrame(Mutilang mutilang, 
			BackgroundModel backgroundModel, 
			ToolInfoModel toolInfoModel, 
			LibraryModel libraryModel,
			ToolRuntimeModel toolRuntimeModel,
			ToolHistoryModel toolHistoryModel
			) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");

		this.mutilang = mutilang;
		
		addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				int newState = e.getNewState();
				int oldState = e.getOldState();
				
				if((newState & MAXIMIZED_HORIZ) == 0 && (oldState & MAXIMIZED_HORIZ) == 0){
					lastNormalWidth = getWidth();
				}
				if((newState & MAXIMIZED_VERT) == 0 && (oldState & MAXIMIZED_VERT) == 0){
					lastNormalHeight = getHeight();
				}
				if((newState & MAXIMIZED_HORIZ) == 0 && (oldState & MAXIMIZED_HORIZ) > 0){
					setSize(lastNormalWidth, getHeight());
				}
				if((newState & MAXIMIZED_VERT) == 0 && (oldState & MAXIMIZED_VERT) > 0){
					lastNormalHeight = getHeight();
					setSize(getWidth(), lastNormalHeight);
				}
			}
		});
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
		
		setIconImage(ImageUtil.getImage(ImageKey.MAINFRAME_ICON, ImageSize.ICON_SUPER_LARGE));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		mainAdjPanel = new JAdjustableBorderPanel();
		mainAdjPanel.setSeperatorColor(new Color(100, 149, 237));
		mainAdjPanel.setNorthMinValue(60);
		mainAdjPanel.setNorthPreferredValue(60);
		mainAdjPanel.setNorthSeparatorEnabled(false);
		mainAdjPanel.setNorthEnabled(true);
		mainAdjPanel.setSeperatorThickness(5);
		mainAdjPanel.setWestEnabled(true);
		mainAdjPanel.setEastEnabled(true);
		getContentPane().add(mainAdjPanel, BorderLayout.CENTER);
		
		centerAdjPanel = new JAdjustableBorderPanel();
		centerAdjPanel.setSouthPreferredValue(100);
		centerAdjPanel.setSeperatorColor(new Color(30, 144, 255));
		centerAdjPanel.setNorthSeparatorEnabled(false);
		centerAdjPanel.setSouthEnabled(true);
		centerAdjPanel.setSeperatorThickness(5);
		mainAdjPanel.add(centerAdjPanel, BorderLayout.CENTER);
		
		southTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		southTabbedPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		centerAdjPanel.add(southTabbedPane, BorderLayout.SOUTH);
		
		console = new JTpconsole(mutilang);
		southTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_2),
				new ImageIcon(ImageUtil.getImage(ImageKey.CONSOLE, ImageSize.ICON_SMALL)), 
				console, null);
		
		System.setIn(console.in);
		System.setOut(console.out);
		System.setErr(console.out);
		
		backgroundPanel = new JBackgroundPanel(backgroundModel);
		southTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_3),
				new ImageIcon(ImageUtil.getImage(ImageKey.PROGRESS, ImageSize.ICON_SMALL)), 
				backgroundPanel, null);
		
		centerTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		centerAdjPanel.add(centerTabbedPane, BorderLayout.CENTER);
		
		toolInfoPanel = new JToolInfoPanel(toolInfoModel);
		toolInfoPanel.addObverser(toolInfoPanelObverser);
		centerTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_4),
				new ImageIcon(ImageUtil.getImage(ImageKey.TOOL, ImageSize.ICON_SMALL)), 
				toolInfoPanel, null);
		
		libraryPanel = new JLibraryPanel(libraryModel);
		centerTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_5),
				new ImageIcon(ImageUtil.getImage(ImageKey.LIBRARY, ImageSize.ICON_SMALL)), 
				libraryPanel, null);
		
		toolRuntimePanel = new JToolRuntimePanel(mutilang, toolRuntimeModel);
		toolRuntimePanel.addObverser(runtimePanelObverser);
		centerTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_6),
				new ImageIcon(ImageUtil.getImage(ImageKey.RUNTIME, ImageSize.ICON_SMALL)), 
				toolRuntimePanel, null);
		
		toolHistoryPanel = new JToolHistoryPanel(mutilang, toolHistoryModel, toolInfoModel);
		centerTabbedPane.addTab(
				getLabel(LabelStringKey.MainFrame_7),
				new ImageIcon(ImageUtil.getImage(ImageKey.HISTORY, ImageSize.ICON_SMALL)), 
				toolHistoryPanel, null);
		
		JPanel panel = new JPanel();
		
		north_border = new TitledBorder(null, getLabel(LabelStringKey.MainFrame_1), TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0));
		panel.setBorder(north_border);
		mainAdjPanel.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{30, 540, 30, 0};
		gbl_panel.rowHeights = new int[]{30, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
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
		
		btnNewButton_1 = new JButton("");
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setSize(new Dimension(30, 30));
		btnNewButton_1.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNewButton_1.setIcon(new ImageIcon(MainFrame.class.getResource("/com/dwarfeng/tp/resource/image/menu.png")));
		btnNewButton_1.setRolloverEnabled(false);
		btnNewButton_1.setBounds(new Rectangle(0, 0, 30, 30));
		btnNewButton_1.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton_1.setIconTextGap(0);
		btnNewButton_1.setOpaque(true);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setPreferredSize(new Dimension(30, 30));
		
		popupMenu = new JPopupMenu();
		addPopup(btnNewButton_1, popupMenu);
		
		menuItem_6 = new JMenuItem("New menu item");
		popupMenu.add(menuItem_6);
		
		menuItem_5 = new JMenuItem("New menu item");
		popupMenu.add(menuItem_5);
		
		menuItem_4 = new JMenuItem("New menu item");
		popupMenu.add(menuItem_4);
		
		menuItem_3 = new JMenuItem("New menu item");
		popupMenu.add(menuItem_3);
		
		menuItem_2 = new JMenuItem("New menu item");
		popupMenu.add(menuItem_2);
		
		menuItem_1 = new JMenuItem("New menu item");
		popupMenu.add(menuItem_1);
		
		menuItem = new JMenuItem("New menu item");
		popupMenu.add(menuItem);
		
		mntmNewMenuItem = new JMenuItem("New menu item");
		popupMenu.add(mntmNewMenuItem);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 0;
		panel.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		mainAdjPanel.add(tabbedPane_1, BorderLayout.SOUTH);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		mainAdjPanel.add(tabbedPane, BorderLayout.WEST);
		
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
		toolHistoryPanel.setMutilang(mutilang);
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
		System.setIn(sysIn);
		System.setOut(sysOut);
		System.setErr(sysErr);
		console.dispose();
		backgroundPanel.dispose();
		toolInfoPanel.removeObverser(toolInfoPanelObverser);
		toolInfoPanel.dispose();
		libraryPanel.dispose();
		toolRuntimePanel.removeObverser(runtimePanelObverser);
		toolRuntimePanel.dispose();
		toolHistoryPanel.dispose();
		super.dispose();
	}

	/**
	 * 获取南方面板的高度。
	 * @return 南方面板的高度。
	 */
	public int getSouthHeight() {
		return centerAdjPanel.getSouthPreferredValue();
	}

	/**
	 * 设置南方面板的高度。
	 * @param height 指定的高度。
	 * @return 该操作是否改变了控制器中的组件。
	 */
	public boolean setSouthHeight(int height) {
		centerAdjPanel.setSouthPreferredValue(height);
		return true;
	}
	
	/**
	 * 获取主界面的最后的正常状态的宽度。
	 * <p> 如果主界面还未初始化，则返回 <code>-1</code>。
	 * @return 主界面的最后的正常状态的宽度。
	 */
	public int getLastNormalWidth() {
		return lastNormalWidth;
	}

	/**
	 * 设置主界面的最后的正常状态的宽度。
	 * @param width 主界面的最后的正常状态的宽度。
	 * @return 该操作是否对主界面造成了改变。
	 */
	public boolean setLastNormalWidth(int width) {
		if((getExtendedState() & MAXIMIZED_HORIZ) == 0){
			setSize(width, getHeight());
		}
		lastNormalWidth = width;
		return true;
	}

	/**
	 * 获取主界面最后的正常状态的高度。
	 * <p> 如果主界面还未初始化，则返回 <code>-1</code>
	 * @return 主界面最后的正常状态的高度。
	 */
	public int getLastNormalHeight() {
		return lastNormalHeight;
	}

	/**
	 * 设置主界面的最后的正常状态的高度。
	 * @param height 主界面的最后的正常状态的高度。
	 * @return 该操作是否对主界面造成了改变。
	 */
	public boolean setLastNormalHeight(int height) {
		if((getExtendedState() & MAXIMIZED_VERT) == 0){
			lastNormalHeight = getHeight();
			setSize(getWidth(), height);
		}
		lastNormalHeight = height;
		return true;
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

	private void fireRunTool(ToolInfo toolInfo) {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireRunTool(toolInfo);
		}
	}
	
	private void fireLogRunningTool(RunningTool runningTool){
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLogRunningTool(runningTool);
		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
