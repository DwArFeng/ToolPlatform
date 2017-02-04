package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Objects;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import com.dwarfeng.dutil.basic.gui.swing.MuaListModel;
import com.dwarfeng.tp.core.model.cfg.ImageKey;
import com.dwarfeng.tp.core.model.cfg.ImageSize;
import com.dwarfeng.tp.core.model.cm.LibraryModel;
import com.dwarfeng.tp.core.model.obv.LibraryAdapter;
import com.dwarfeng.tp.core.model.obv.LibraryObverser;
import com.dwarfeng.tp.core.model.struct.Library;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

public final class JLibraryPanel extends JPanel{
	
	private final JList<String> list;
	private final Image libraryIconImage;

	private LibraryModel libraryModel;
	private ImageSize libraryIconSize = ImageSize.ICON_MEDIUM;
	

	private final LibraryObverser libraryObverser = new LibraryAdapter() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.LibraryAdapter#fireEntryAdded(java.lang.String, com.dwarfeng.tp.core.model.struct.Library)
		 */
		@Override
		public void fireEntryAdded(String key, Library value) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					listModel.add(key);
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.LibraryAdapter#fireEntryRemoved(java.lang.String)
		 */
		@Override
		public void fireEntryRemoved(String key) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					listModel.remove(key);
				}
			});
		}

		@Override
		public void fireEntryChanged(String key, Library oldValue, Library newValue) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = listModel.indexOf(key);
					listModel.set(index, key);
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.LibraryAdapter#fireCleared()
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
		
		private static final long serialVersionUID = 7784575243309713143L;

		/*
		 * (non-Javadoc)
		 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			String name = (String) value;
			this.setText(name);
			setIcon(new ImageIcon(ToolPlatformUtil.scaleImage(libraryIconImage, libraryIconSize)));
			return this;
		};
	};
	
	/**
	 * 新实例。
	 */
	public JLibraryPanel() {
		this(null);
	}
	
	/**
	 * 
	 * @param libraryModel
	 */
	public JLibraryPanel(LibraryModel libraryModel) {
		setLayout(new BorderLayout(0, 0));
		
		libraryIconImage = ToolPlatformUtil.getImage(ImageKey.LIBRARY_ICON, ImageSize.ICON_LARGE);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		add(scrollPane, BorderLayout.CENTER);
		
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
		});
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(0);
		scrollPane.setViewportView(list);
		
		list.setModel(listModel);
		list.setCellRenderer(listRenderer);
		
		if(Objects.nonNull(libraryModel)){
			libraryModel.addObverser(libraryObverser);
			libraryModel.getLock().readLock().lock();
			try{
				for(String name : libraryModel.keySet()){
					listModel.add(name);
				}
			}finally {
				libraryModel.getLock().readLock().unlock();
			}
		}
		
		this.libraryModel = libraryModel;
	}

	/**
	 * 获取面板中的库模型。
	 * @return 面板中的库模型。
	 */
	public LibraryModel getLibraryModel() {
		return libraryModel;
	}

	/**
	 * 设置面板中的库模型为指定模型。
	 * @param libraryModel 指定的模型。
	 */
	public void setLibraryModel(LibraryModel libraryModel) {
		if(Objects.nonNull(this.libraryModel)){
			this.libraryModel.removeObverser(libraryObverser);
		}
		if(Objects.nonNull(libraryModel)){
			libraryModel.addObverser(libraryObverser);
			libraryModel.getLock().readLock().lock();
			try{
				for(String name : libraryModel.keySet()){
					listModel.add(name);
				}
			}finally {
				libraryModel.getLock().readLock().unlock();
			}
		}
		this.libraryModel = libraryModel;
	}
	
	/**
	 * 获取库图标的大小。
	 * @return 库图标的大小。
	 */
	public ImageSize getLibraryIconSize() {
		return libraryIconSize;
	}

	/**
	 * 设置库图标的大小。
	 * @param libraryIconSize 库图标的大小。
	 */
	public void setLibraryIconSize(ImageSize libraryIconSize) {
		this.libraryIconSize = libraryIconSize;
		list.repaint();
	}

	/**
	 * 释放资源
	 */
	public void dispose(){
		listModel.clear();
		libraryModel.removeObverser(libraryObverser);
	}
	
}
