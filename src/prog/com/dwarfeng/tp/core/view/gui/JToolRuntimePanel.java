package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dwarfeng.dutil.basic.gui.swing.JMenuItemAction;
import com.dwarfeng.dutil.basic.gui.swing.MuaListModel;
import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.prog.RuntimeState;
import com.dwarfeng.tp.core.model.cfg.ImageKey;
import com.dwarfeng.tp.core.model.cfg.ImageSize;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cm.ToolRuntimeModel;
import com.dwarfeng.tp.core.model.obv.ToolRuntimeAdapter;
import com.dwarfeng.tp.core.model.obv.ToolRuntimeObverser;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;
import com.dwarfeng.tp.core.model.struct.RunningTool;
import com.dwarfeng.tp.core.util.DateUtil;
import com.dwarfeng.tp.core.util.ImageUtil;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;
import com.dwarfeng.tp.core.view.obv.ToolRuntimePanelObverser;
import com.dwarfeng.tp.core.view.struct.JListMouseListener4Selection;
import com.dwarfeng.tp.core.view.struct.JListMouseMotionListener4Selection;

public class JToolRuntimePanel extends JPanel implements MutilangSupported, ObverserSet<ToolRuntimePanelObverser>{
	
	/**观察器集合*/
	private final Set<ToolRuntimePanelObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	/*
	 * final 域。
	 */
	private final JList<RunningTool> list;
	private final JListPopupMenu listPopup;
	private final JToolRuntimeConsoleContainer consoleContainer;
	private final Image notStartImage;
	private final Image runningImage;
	private final Image exitedImage;
	private final Image removeImage;
	private final Image clearImage;

	/*
	 * 其它非 final 域。
	 */
	private ToolRuntimeModel toolRuntimeModel;
	private ImageSize toolInfoIconSize = ImageSize.ICON_MEDIUM;
	private Mutilang mutilang;
	
	private final ToolRuntimeObverser toolRuntimeObverser = new ToolRuntimeAdapter() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolRuntimeAdapter#fireRunningToolAdded(com.dwarfeng.tp.core.model.struct.RunningTool)
		 */
		@Override
		public void fireRunningToolAdded(RunningTool runningTool) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					listModel.add(runningTool);
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolRuntimeAdapter#fireRunningToolRemoved(com.dwarfeng.tp.core.model.struct.RunningTool)
		 */
		@Override
		public void fireRunningToolRemoved(RunningTool runningTool) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					listModel.remove(runningTool);
					JTpconsole console = toolConsoleMap.get(runningTool);
					console.dispose();
					if(Objects.nonNull(console) && console.equals(((BorderLayout) consoleContainer.getLayout()).getLayoutComponent(consoleContainer, BorderLayout.CENTER))){
						consoleContainer.removeAll();
						consoleContainer.repaint();
					}
				}
			});
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolRuntimeAdapter#fireRunningToolStarted(com.dwarfeng.tp.core.model.struct.RunningTool)
		 */
		@Override
		public void fireRunningToolStarted(RunningTool runningTool) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = listModel.indexOf(runningTool);
					listModel.set(index, runningTool);
				}
			});
		};
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolRuntimeAdapter#fireRunningToolExited(com.dwarfeng.tp.core.model.struct.RunningTool)
		 */
		@Override
		public void fireRunningToolExited(RunningTool runningTool) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					fireLogRunningTool(runningTool);
					int index = listModel.indexOf(runningTool);
					listModel.set(index, runningTool);
				}
			});
			JTpconsole console = toolConsoleMap.get(runningTool);
			try {
				ToolPlatformUtil.invokeAndWaitInEventQueue(new Runnable() {
					@Override
					public void run() {
						console.input("\n");
					}
				});
			} catch (InvocationTargetException | InterruptedException ignore) {
				//不可能抛出异常。
				//中断也要按照基本法。
			}
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					if(Objects.nonNull(console)){
						console.out.println(formatLabel(LabelStringKey.JToolRuntimePanel_1, DateUtil.formatDate(runningTool.getExitedDate()), runningTool.getExitCode()));
					}
				}
			});
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int leadIndex;
					if((leadIndex = list.getSelectionModel().getLeadSelectionIndex()) >= 0 &&
							listModel.size() > 0 &&
							list.getSelectionModel().isSelectedIndex(leadIndex)
					){
						RunningTool runningTool = listModel.get(leadIndex);
						if(runningTool.getRuntimeState().equals(RuntimeState.ENDED)){
							removeButton.setEnabled(true);
						}else{
							removeButton.setEnabled(false);
						}
					}
				}
			});
		};
		
	};
	private final MuaListModel<RunningTool> listModel = new MuaListModel<>();
	private final ListCellRenderer<Object> listRenderer = new DefaultListCellRenderer(){

		private static final long serialVersionUID = 2411713996425187343L;

		/*
		 * (non-Javadoc)
		 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			RunningTool runningTool = (RunningTool) value;
			this.setText(runningTool.getName());
			Image toolIcon = runningTool.getImage();
			Image stateIcon = null;
			switch (runningTool.getRuntimeState()) {
			case ENDED:
				stateIcon = exitedImage;
				break;
			case NOT_START:
				stateIcon = notStartImage;
				break;
			case RUNNING:
				stateIcon = runningImage;
				break;
			}
			setIcon(new ImageIcon(ImageUtil.overlayImage(toolIcon, stateIcon, toolInfoIconSize)));
			return this;
		}
		
	};
	private final Map<RunningTool, JTpconsole> toolConsoleMap = new HashMap<>();
	private JButton removeButton;
	private JButton clearButton;
	/**
	 * 新实例。
	 */
	public JToolRuntimePanel() {
		this(ToolPlatformUtil.newDefaultLabelMutilang(), null);
	}
	
	/**
	 * 新实例。
	 * @param toolRuntimeModel
	 */
	public JToolRuntimePanel(Mutilang mutilang, ToolRuntimeModel toolRuntimeModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
		
		runningImage = ImageUtil.getImage(ImageKey.RUNNING, toolInfoIconSize);
		notStartImage = ImageUtil.getImage(ImageKey.NOT_START, toolInfoIconSize);
		exitedImage = ImageUtil.getImage(ImageKey.EXITED, toolInfoIconSize);
		removeImage = ImageUtil.getImage(ImageKey.REMOVE, ImageSize.ICON_SMALL);
		clearImage = ImageUtil.getImage(ImageKey.CLEAR, ImageSize.ICON_SMALL);

		setLayout(new BorderLayout(0, 0));

		this.toolRuntimeModel = toolRuntimeModel;
		
		JAdjustableBorderPanel adjustableBorderPanel = new JAdjustableBorderPanel();
		adjustableBorderPanel.setWestEnabled(true);
		adjustableBorderPanel.setSeperatorThickness(5);
		adjustableBorderPanel.setSeperatorColor(new Color(100, 149, 237));
		add(adjustableBorderPanel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		adjustableBorderPanel.add(scrollPane, BorderLayout.WEST);
		
		list = new JList<>();
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){
					//清空工作台区域
					consoleContainer.removeAll();
					
					//确定选择的下标
					int leadIndex;
					if((leadIndex = list.getSelectionModel().getLeadSelectionIndex()) >= 0 &&
							listModel.size() > 0 &&
							list.getSelectionModel().isSelectedIndex(leadIndex)
					){
						//显示控制台
						RunningTool runningTool = listModel.get(leadIndex);
						JTpconsole console = toolConsoleMap.get(runningTool);
						if(Objects.nonNull(console)){
							consoleContainer.add(console, BorderLayout.CENTER);
							console.revalidate();
						}
						//根据情况更改removeButton是否启用
						if(runningTool.getRuntimeState().equals(RuntimeState.ENDED)){
							removeButton.setEnabled(true);
						}else{
							removeButton.setEnabled(false);
						}
					}else{
						removeButton.setEnabled(false);
					}
					consoleContainer.repaint();
				}
			}
		});
		list.addMouseMotionListener(new JListMouseMotionListener4Selection(list));
		list.addMouseListener(new JListMouseListener4Selection(list));
		
		listPopup = new JListPopupMenu();
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int mouseIndex = list.locationToIndex(e.getPoint());
				if(mouseIndex != -1){
					mouseIndex = list.getCellBounds(mouseIndex, mouseIndex).contains(e.getPoint()) ? mouseIndex : -1;
				}
				if (e.isPopupTrigger() && mouseIndex >= 0) {
					RunningTool runningTool = listModel.get(mouseIndex);
					listPopup.adjust(runningTool);
					listPopup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int mouseIndex = list.locationToIndex(e.getPoint());
				if(mouseIndex != -1){
					mouseIndex = list.getCellBounds(mouseIndex, mouseIndex).contains(e.getPoint()) ? mouseIndex : -1;
				}
				if (e.isPopupTrigger() && mouseIndex >= 0) {
					RunningTool runningTool = listModel.get(mouseIndex);
					listPopup.adjust(runningTool);
					listPopup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		scrollPane.setViewportView(list);
		
		list.setModel(listModel);
		list.setCellRenderer(listRenderer);
		
		consoleContainer = new JToolRuntimeConsoleContainer();
		adjustableBorderPanel.add(consoleContainer, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);
		
		removeButton = new JButton();
		removeButton.setToolTipText(getLabel(LabelStringKey.JToolRuntimePanel_3));
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int leadIndex;
				if((leadIndex = list.getSelectionModel().getLeadSelectionIndex()) >= 0 &&
						listModel.size() > 0 &&
						list.getSelectionModel().isSelectedIndex(leadIndex)
				){
					RunningTool runningTool = listModel.get(leadIndex);
					if(runningTool.getRuntimeState().equals(RuntimeState.ENDED)){
						fireRemoveExitedRunningTool(runningTool);
						removeButton.setEnabled(false);
						list.getSelectionModel().setValueIsAdjusting(false);
						list.getSelectionModel().clearSelection();
						list.getSelectionModel().setLeadSelectionIndex(0);
						list.getSelectionModel().setAnchorSelectionIndex(0);
					}
				}
			}
		});
		removeButton.setEnabled(false);
		removeButton.setIconTextGap(0);
		removeButton.setBorder(null);
		removeButton.setIcon(new ImageIcon(removeImage));
		toolBar.add(removeButton);
		
		clearButton = new JButton();
		clearButton.setToolTipText(getLabel(LabelStringKey.JToolRuntimePanel_4));
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<RunningTool> runningTools = new ArrayList<>(listModel);
				for(RunningTool runningTool : runningTools){
					if(runningTool.getRuntimeState().equals(RuntimeState.ENDED)){
						fireRemoveExitedRunningTool(runningTool);
					}
				}
				removeButton.setEnabled(false);
				list.getSelectionModel().clearSelection();
				list.getSelectionModel().setLeadSelectionIndex(-1);
				list.getSelectionModel().setAnchorSelectionIndex(-1);
			}
		});
		clearButton.setIconTextGap(0);
		clearButton.setBorder(null);
		clearButton.setIcon(new ImageIcon(clearImage));
		toolBar.add(clearButton);
		
		if(Objects.nonNull(toolRuntimeModel)){
			toolRuntimeModel.addObverser(toolRuntimeObverser);
			toolRuntimeModel.getLock().readLock().lock();
			try{
				for(RunningTool runningTool : toolRuntimeModel){
					listModel.add(runningTool);
				}
			}finally {
				toolRuntimeModel.getLock().readLock().unlock();
			}
		}
	}

	/**
	 * 获取面板中的工具运行时模型。
	 * @return 面板中的工具运行时模型。
	 */
	public ToolRuntimeModel getToolRuntimeModel() {
		return toolRuntimeModel;
	}

	/**
	 * 设置面板中的工具运行时模型为指定的模型。
	 * <p> 如果当前的工具运行时模型不为 <code>null</code>，则当且仅当当前的模型中没有任何元素后，才能设置新的模型。
	 * @param toolRuntimeModel 指定的工具运行时模型。
	 */
	public boolean setToolRuntimeModel(ToolRuntimeModel toolRuntimeModel) {
		if(Objects.nonNull(this.toolRuntimeModel) && this.toolRuntimeModel.size() > 0){
			return false;
		}
		
		if(Objects.nonNull(this.toolRuntimeModel)){
			this.toolRuntimeModel.removeObverser(toolRuntimeObverser);
			for(JTpconsole console : toolConsoleMap.values()){
				console.dispose();
			}
			toolConsoleMap.clear();
		}
		if(Objects.nonNull(toolRuntimeModel)){
			toolRuntimeModel.addObverser(toolRuntimeObverser);
			toolRuntimeModel.getLock().readLock().lock();
			try{
				for(RunningTool runningTool : toolRuntimeModel){
					listModel.add(runningTool);
				}
			}finally {
				toolRuntimeModel.getLock().readLock().unlock();
			}
		}
		this.toolRuntimeModel = toolRuntimeModel;
		return true;
	}
	
	/**
	 * 获取运行中工具图标的大小。
	 * @return 工具图标的大小。
	 */
	public ImageSize getToolInfoIconSize() {
		return toolInfoIconSize;
	}

	/**
	 * 设置运行中工具图标的大小。
	 * @param toolInfoIconSize 工具图标的大小。
	 */
	public void setToolInfoIconSize(ImageSize toolInfoIconSize) {
		this.toolInfoIconSize = toolInfoIconSize;
		list.repaint();
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
		for(JTpconsole console : toolConsoleMap.values()){
			console.setMutilang(mutilang);
		}
		removeButton.setToolTipText(getLabel(LabelStringKey.JToolRuntimePanel_3));
		clearButton.setToolTipText(getLabel(LabelStringKey.JToolRuntimePanel_4));
		return true;
	}
	
	/**
	 * 为指定的运行中工具指定输入流和输出流。
	 * <p> 当且仅当入口参数不为 <code>null</code>，且输入当前的 toolRuntimeModel的时候，才能够指派成功。
	 * @param runningTool 指定的运行中工具。
	 * @return 是否接受该指派。
	 */
	public boolean assignStream(RunningTool runningTool){
		if(Objects.isNull(runningTool)) return false;
		if(Objects.isNull(toolRuntimeModel)) return false;
		if(! toolRuntimeModel.contains(runningTool)) return false;
		
		JTpconsole console = new JTpconsole();
		runningTool.setInputStream(console.in);
		runningTool.setOutputStream(console.out);
		toolConsoleMap.put(runningTool, console);
		return true;
	}
	
	/**
	 * 释放资源。
	 */
	public void dispose(){
		try{
			listModel.clear();
		}catch (Exception e) {
			e.printStackTrace();
		}
		consoleContainer.removeAll();
		for(JTpconsole console : toolConsoleMap.values()){
			console.dispose();
		}
		toolConsoleMap.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<ToolRuntimePanelObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(ToolRuntimePanelObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(ToolRuntimePanelObverser obverser) {
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

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}
	
	private String formatLabel(LabelStringKey labelStringKey, Object... args){
		return String.format(mutilang.getString(labelStringKey.getName()), args);
	}
	
	private void fireLogRunningTool(RunningTool runningTool){
		for(ToolRuntimePanelObverser obverser : obversers){
			if(Objects.nonNull(obversers)) obverser.fireLogRunningTool(runningTool);
		}
	}
	private void fireRemoveExitedRunningTool(RunningTool runningTool){
		for(ToolRuntimePanelObverser obverser : obversers){
			if(Objects.nonNull(obversers)) obverser.fireRemoveExitedRunningTool(runningTool);
		}
	}
	
	private final class JListPopupMenu extends JPopupMenu{
		
		private final JMenuItem removeMenuItem;
		
		private RunningTool runningTool;
		
		public JListPopupMenu() {
			removeMenuItem = add(new JMenuItemAction.Builder()
					.name(JToolRuntimePanel.this.getLabel(LabelStringKey.JToolRuntimePanel_2))
					.icon(new ImageIcon(removeImage))
					.mnemonic('E')
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							fireRemoveExitedRunningTool(runningTool);
						}
					})
					.build()
			);
		}
		
		public void adjust(RunningTool runningTool){
			this.runningTool = runningTool;
			if(runningTool.getRuntimeState().equals(RuntimeState.ENDED)){
				removeMenuItem.setEnabled(true);
			}else{
				removeMenuItem.setEnabled(false);
			}
		}
		
	}

}
