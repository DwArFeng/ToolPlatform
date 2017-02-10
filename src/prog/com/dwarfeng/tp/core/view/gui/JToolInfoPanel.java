package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
import com.dwarfeng.tp.core.model.struct.ToolInfo;
import com.dwarfeng.tp.core.util.ImageUtil;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;
import com.dwarfeng.tp.core.view.obv.ToolInfoPanelObverser;

public class JToolInfoPanel extends JPanel implements ObverserSet<ToolInfoPanelObverser>{
	
	/**�۲�������*/
	private final Set<ToolInfoPanelObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	private final JList<ToolInfo> list;
	
	private ToolInfoModel toolInfoModel;
	private ImageSize toolInfoIconSize = ImageSize.ICON_MEDIUM;
	
	private final ToolInfoObverser toolObverser = new ToolInfoAdapter() {

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
	 * ��ȡ�����Ĺ�����Ϣģ�͡�
	 * @return �����Ĺ�����Ϣģ�͡�
	 */
	public ToolInfoModel getToolInfoModel() {
		return toolInfoModel;
	}

	/**
	 * ���ù�����Ϣģ��Ϊָ����ģ�͡�
	 * @param toolInfoModel ָ���Ĺ�����Ϣģ�͡�
	 */
	public void setToolInfoModel(ToolInfoModel toolInfoModel) {
		if(Objects.nonNull(this.toolInfoModel)){
			this.toolInfoModel.removeObverser(toolObverser);
		}
		if(Objects.nonNull(toolInfoModel)){
			toolInfoModel.addObverser(toolObverser);
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
	 * ��ȡ����ͼ��Ĵ�С��
	 * @return ����ͼ��Ĵ�С��
	 */
	public ImageSize getToolInfoIconSize() {
		return toolInfoIconSize;
	}

	/**
	 * ���ù���ͼ��Ĵ�С��
	 * @param toolInfoIconSize ����ͼ��Ĵ�С��
	 */
	public void setToolInfoIconSize(ImageSize toolInfoIconSize) {
		this.toolInfoIconSize = toolInfoIconSize;
		list.repaint();
	}

	/**
	 * �ͷ���Դ��
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

	private void fireRunTool(ToolInfo toolInfo) {
		for(ToolInfoPanelObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireRunTool(toolInfo);
		}
	}
	
}