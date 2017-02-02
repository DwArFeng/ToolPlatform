package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import com.dwarfeng.dutil.basic.gui.swing.MuaListModel;
import com.dwarfeng.tp.core.model.cfg.ImageSize;
import com.dwarfeng.tp.core.model.cm.ToolInfoModel;
import com.dwarfeng.tp.core.model.obv.ToolAdapter;
import com.dwarfeng.tp.core.model.obv.ToolInfoObverser;
import com.dwarfeng.tp.core.model.struct.ProcessException;
import com.dwarfeng.tp.core.model.struct.ToolInfo;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

public class JToolInfoPanel extends JPanel{
	
	private final JList<String> list;
	
	private ToolInfoModel toolInfoModel;
	private ImageSize toolInfoIconSize = ImageSize.ICON_MEDIUM;
	
	private final ToolInfoObverser toolObverser = new ToolAdapter() {

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
	
}
