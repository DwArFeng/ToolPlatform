package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dwarfeng.dutil.basic.gui.swing.JExconsole;
import com.dwarfeng.dutil.basic.gui.swing.MuaListModel;
import com.dwarfeng.tp.core.model.cfg.ImageSize;
import com.dwarfeng.tp.core.model.cm.ToolRuntimeModel;
import com.dwarfeng.tp.core.model.obv.ToolRuntimeAdapter;
import com.dwarfeng.tp.core.model.obv.ToolRuntimeObverser;
import com.dwarfeng.tp.core.model.struct.RunningTool;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

public class JToolRuntimePanel extends JPanel{
	
	private final JList<RunningTool> list;
	private final JToolRuntimeConsoleContainer consoleContainer;

	private ToolRuntimeModel toolRuntimeModel;
	private ImageSize toolInfoIconSize = ImageSize.ICON_MEDIUM;
	
	private final ToolRuntimeObverser toolRuntimeObverser = new ToolRuntimeAdapter() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolRuntimeAdapter#fireRunningToolAdded(com.dwarfeng.tp.core.model.struct.RunningTool)
		 */
		@Override
		public void fireRunningToolAdded(RunningTool runningTool) {
			listModel.add(runningTool);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolRuntimeAdapter#fireRunningToolRemoved(com.dwarfeng.tp.core.model.struct.RunningTool)
		 */
		@Override
		public void fireRunningToolRemoved(RunningTool runningTool) {
			listModel.remove(runningTool);
			//TODO 之后的工作台也要一并移除。
		}
		
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
			setIcon(new ImageIcon(ToolPlatformUtil.scaleImage(runningTool.getImage(), toolInfoIconSize)));
			return this;
		}
		
	};
	private final Map<RunningTool, JTpconsole> toolConsoleMap = new HashMap<>();
	/**
	 * 新实例。
	 */
	public JToolRuntimePanel() {
		this(null);
	}
	
	/**
	 * 新实例。
	 * @param toolRuntimeModel
	 */
	public JToolRuntimePanel(ToolRuntimeModel toolRuntimeModel) {
		setLayout(new BorderLayout(0, 0));

		this.toolRuntimeModel = toolRuntimeModel;
		
		JAdjustableBorderPanel adjustableBorderPanel = new JAdjustableBorderPanel();
		adjustableBorderPanel.setWestEnabled(true);
		adjustableBorderPanel.setSeperatorThickness(5);
		adjustableBorderPanel.setSeperatorColor(new Color(100, 149, 237));
		add(adjustableBorderPanel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		adjustableBorderPanel.add(scrollPane, BorderLayout.WEST);
		
		list = new JList<>();
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){
					int leadIndex;
					consoleContainer.removeAll();
					if((leadIndex = list.getSelectionModel().getLeadSelectionIndex()) >= 0){
						RunningTool runningTool = listModel.get(leadIndex);
						JTpconsole console = toolConsoleMap.get(runningTool);
						if(console == null){
							consoleContainer.repaint();
						}else{
							consoleContainer.add(console, BorderLayout.CENTER);
							console.revalidate();
							consoleContainer.repaint();
						}
					}else{
						consoleContainer.repaint();
					}
				}
			}
		});
		list.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int mouseIndex = list.locationToIndex(e.getPoint());
				if(mouseIndex != -1){
					mouseIndex = list.getCellBounds(mouseIndex, mouseIndex).contains(e.getPoint()) ? mouseIndex : -1;
				}
				if(mouseIndex == -1){
					list.getSelectionModel().clearSelection();
					list.getSelectionModel().setAnchorSelectionIndex(-1);
					list.getSelectionModel().setLeadSelectionIndex(-1);
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int mouseIndex = list.locationToIndex(e.getPoint());
				if(mouseIndex != -1){
					mouseIndex = list.getCellBounds(mouseIndex, mouseIndex).contains(e.getPoint()) ? mouseIndex : -1;
				}
				if(mouseIndex == -1){
					list.getSelectionModel().clearSelection();
					list.getSelectionModel().setAnchorSelectionIndex(-1);
					list.getSelectionModel().setLeadSelectionIndex(-1);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int mouseIndex = list.locationToIndex(e.getPoint());
				if(mouseIndex != -1){
					mouseIndex = list.getCellBounds(mouseIndex, mouseIndex).contains(e.getPoint()) ? mouseIndex : -1;
				}
				if(mouseIndex == -1){
					list.getSelectionModel().clearSelection();
					list.getSelectionModel().setAnchorSelectionIndex(-1);
					list.getSelectionModel().setLeadSelectionIndex(-1);
				}
			}
		});
		scrollPane.setViewportView(list);
		
		list.setModel(listModel);
		list.setCellRenderer(listRenderer);
		
		consoleContainer = new JToolRuntimeConsoleContainer();
		adjustableBorderPanel.add(consoleContainer, BorderLayout.CENTER);
		
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
			for(JExconsole console : toolConsoleMap.values()){
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
		listModel.clear();
		consoleContainer.removeAll();
		for(JTpconsole console : toolConsoleMap.values()){
			console.dispose();
		}
		toolConsoleMap.clear();
	}

}
