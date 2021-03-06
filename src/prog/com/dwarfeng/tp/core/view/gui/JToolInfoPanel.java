package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import com.dwarfeng.tp.core.model.cm.ToolInfoModel;
import com.dwarfeng.tp.core.model.eum.ImageSize;
import com.dwarfeng.tp.core.model.obv.ToolInfoAdapter;
import com.dwarfeng.tp.core.model.obv.ToolInfoObverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;
import com.dwarfeng.tp.core.util.ImageUtil;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;
import com.dwarfeng.tp.core.view.obv.ToolInfoPanelObverser;
import com.dwarfeng.tp.core.view.struct.JListMouseListener4Selection;
import com.dwarfeng.tp.core.view.struct.JListMouseMotionListener4Selection;

public class JToolInfoPanel extends JPanel implements ObverserSet<ToolInfoPanelObverser>{
	
	/**观察器集合*/
	private final Set<ToolInfoPanelObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	private final JList<ToolInfo> list;
	
	private ToolInfoModel toolInfoModel;
	private ImageSize toolInfoIconSize = ImageSize.ICON_MEDIUM;
	
	private final ToolInfoObverser toolInfoObverser = new ToolInfoAdapter() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolInfoAdapter#fireToolInfoAdded(com.dwarfeng.tp.core.model.struct.ToolInfo)
		 */
		@Override
		public void fireToolInfoAdded(ToolInfo toolInfo) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					listModel.add(toolInfo);
				}
			});
		};
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolInfoAdapter#fireToolInfoRemoved(com.dwarfeng.tp.core.model.struct.ToolInfo)
		 */
		@Override
		public void fireToolInfoRemoved(ToolInfo toolInfo) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					listModel.remove(toolInfo);
				}
			});
		};

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
	private final MuaListModel<ToolInfo> listModel = new MuaListModel<>();
	private final ListCellRenderer<Object> listRenderer = new DefaultListCellRenderer(){
		
		private static final long serialVersionUID = 8956658572067462043L;

		/*
		 * (non-Javadoc)
		 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			ToolInfo toolInfo = (ToolInfo) value;
			this.setText(toolInfo.getName());
			setIcon(new ImageIcon(ImageUtil.scaleImage(toolInfo.getImage(), toolInfoIconSize)));
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
		list.addMouseMotionListener(new JListMouseMotionListener4Selection(list));
		list.addMouseListener(new JListMouseListener4Selection(list));
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int mouseIndex = list.locationToIndex(e.getPoint());
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
			toolInfoModel.addObverser(toolInfoObverser);
			toolInfoModel.getLock().readLock().lock();
			try{
				for(ToolInfo toolInfo : toolInfoModel){
					listModel.add(toolInfo);
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
			this.toolInfoModel.removeObverser(toolInfoObverser);
		}
		if(Objects.nonNull(toolInfoModel)){
			toolInfoModel.addObverser(toolInfoObverser);
			toolInfoModel.getLock().readLock().lock();
			try{
				for(ToolInfo toolInfo : toolInfoModel){
					listModel.add(toolInfo);
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
		if(Objects.nonNull(toolInfoModel)){
			toolInfoModel.removeObverser(toolInfoObverser);
		}
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

	private void fireRunTool(ToolInfo toolInfo) {
		for(ToolInfoPanelObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireRunTool(toolInfo);
		}
	}
	
}
