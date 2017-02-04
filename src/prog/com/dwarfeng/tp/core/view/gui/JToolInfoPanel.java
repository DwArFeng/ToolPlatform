package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import com.dwarfeng.dutil.basic.gui.swing.MuaListModel;
import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.cfg.ImageSize;
import com.dwarfeng.tp.core.model.cm.ToolInfoModel;
import com.dwarfeng.tp.core.model.obv.ToolInfoAdapter;
import com.dwarfeng.tp.core.model.obv.ToolInfoObverser;
import com.dwarfeng.tp.core.model.struct.ProcessException;
import com.dwarfeng.tp.core.model.struct.ToolInfo;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;
import com.dwarfeng.tp.core.view.obv.ToolInfoPanelObverser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class JToolInfoPanel extends JPanel implements ObverserSet<ToolInfoPanelObverser>{
	
	/**观察器集合*/
	private final Set<ToolInfoPanelObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	private final JList<String> list;
	
	private ToolInfoModel toolInfoModel;
	private ImageSize toolInfoIconSize = ImageSize.ICON_MEDIUM;
	
	private final ToolInfoObverser toolObverser = new ToolInfoAdapter() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolAdapter#fireEntryAdded(java.lang.String, com.dwarfeng.tp.core.model.struct.ToolInfo)
		 */
		@Override
		public void fireEntryAdded(String name, ToolInfo info) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					listModel.add(name);
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolAdapter#fireEntryRemoved(java.lang.String)
		 */
		@Override
		public void fireEntryRemoved(String name) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					listModel.remove(name);
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolAdapter#fireEntryChanged(java.lang.String, com.dwarfeng.tp.core.model.struct.ToolInfo, com.dwarfeng.tp.core.model.struct.ToolInfo)
		 */
		@Override
		public void fireEntryChanged(String name, ToolInfo oldOne, ToolInfo newOne) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = listModel.indexOf(name);
					listModel.set(index, name);
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolAdapter#fireCleared()
		 */
		@Override
		public void fireCleared() {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					listModel.clear();
				}
			});
		}
		
	};
	private final MuaListModel<String> listModel = new MuaListModel<>();
	private final ListCellRenderer<Object> listRenderer = new DefaultListCellRenderer(){
		
		private static final long serialVersionUID = 8956658572067462043L;

		/*
		 * (non-Javadoc)
		 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			String name = (String) value;
			this.setText(name);
			try {
				setIcon(new ImageIcon(ToolPlatformUtil.scaleImage(toolInfoModel.get(name).getImage(), toolInfoIconSize)));
			} catch (ProcessException ignore) {}
			return this;
		}
		
	};
	/**
	 * Create the panel.
	 */
	public JToolInfoPanel() {
		this(null);
	}
	
	public JToolInfoPanel(ToolInfoModel toolInfoModel){
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		add(scrollPane);
		
		list = new JList<>();
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
			@Override
			public void mouseClicked(MouseEvent e) {
				int mouseIndex = list.locationToIndex(e.getPoint());
				if(mouseIndex != -1){
					mouseIndex = list.getCellBounds(mouseIndex, mouseIndex).contains(e.getPoint()) ? mouseIndex : -1;
				}
				if(e.getClickCount()==2 && mouseIndex >= 0){
					fireRunTool(listModel.get(mouseIndex));
				}
			}
		});
		list.setVisibleRowCount(0);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		scrollPane.setViewportView(list);
		
		list.setModel(listModel);
		list.setCellRenderer(listRenderer);

		if(Objects.nonNull(toolInfoModel)){
			toolInfoModel.addObverser(toolObverser);
			toolInfoModel.getLock().readLock().lock();
			try{
				for(String name : toolInfoModel.keySet()){
					listModel.add(name);
				}
			}finally {
				toolInfoModel.getLock().readLock().unlock();
			}
		}
		
		this.toolInfoModel = toolInfoModel;
	}

	/**
	 * 获取该面板的工具信息模型。
	 * @return 该面板的工具信息模型。
	 */
	public ToolInfoModel getToolInfoModel() {
		return toolInfoModel;
	}

	/**
	 * 设置工具信息模型为指定的模型。
	 * @param toolInfoModel 指定的工具信息模型。
	 */
	public void setToolInfoModel(ToolInfoModel toolInfoModel) {
		if(Objects.nonNull(this.toolInfoModel)){
			this.toolInfoModel.removeObverser(toolObverser);
		}
		if(Objects.nonNull(toolInfoModel)){
			toolInfoModel.addObverser(toolObverser);
			toolInfoModel.getLock().readLock().lock();
			try{
				for(String name : toolInfoModel.keySet()){
					listModel.add(name);
				}
			}finally {
				toolInfoModel.getLock().readLock().unlock();
			}
		}
		this.toolInfoModel = toolInfoModel;
	}
	
	/**
	 * 获取工具图标的大小。
	 * @return 工具图标的大小。
	 */
	public ImageSize getToolInfoIconSize() {
		return toolInfoIconSize;
	}

	/**
	 * 设置工具图标的大小。
	 * @param toolInfoIconSize 工具图标的大小。
	 */
	public void setToolInfoIconSize(ImageSize toolInfoIconSize) {
		this.toolInfoIconSize = toolInfoIconSize;
		list.repaint();
	}

	/**
	 * 释放资源。
	 */
	public void dispose(){
		listModel.clear();
		toolInfoModel.removeObverser(toolObverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<ToolInfoPanelObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(ToolInfoPanelObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(ToolInfoPanelObverser obverser) {
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

	private void fireRunTool(String name) {
		for(ToolInfoPanelObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireRunTool(name);
		}
	}
	
}
