package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Date;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.dwarfeng.tp.core.model.cm.ToolHistoryModel;
import com.dwarfeng.tp.core.model.cm.ToolInfoModel;
import com.dwarfeng.tp.core.model.eum.ImageKey;
import com.dwarfeng.tp.core.model.eum.ImageSize;
import com.dwarfeng.tp.core.model.eum.LabelStringKey;
import com.dwarfeng.tp.core.model.obv.ToolHistoryAdapter;
import com.dwarfeng.tp.core.model.obv.ToolHistoryObverser;
import com.dwarfeng.tp.core.model.obv.ToolInfoAdapter;
import com.dwarfeng.tp.core.model.obv.ToolInfoObverser;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;
import com.dwarfeng.tp.core.model.struct.ToolHistory;
import com.dwarfeng.tp.core.model.struct.ToolInfo;
import com.dwarfeng.tp.core.util.Constants;
import com.dwarfeng.tp.core.util.DateUtil;
import com.dwarfeng.tp.core.util.ImageUtil;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

public class JToolHistoryPanel extends JPanel implements MutilangSupported{
	
	/*
	 * final 域
	 */
	private final JTable table;
	private final Image unknownToolIcon;
	
	
	/*
	 * 其它非 final 域。
	 */
	/**多语言接口*/
	private Mutilang mutilang;
	private ImageSize toolIconSize = ImageSize.ICON_MEDIUM;
	private ToolHistoryModel toolHistoryModel;
	private ToolInfoModel toolInfoModel;
	
	private final DefaultTableModel tableModel = new DefaultTableModel(){
		
		private static final long serialVersionUID = -5018361256651102316L;

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		};
		
	};

	private final ToolHistoryObverser toolHistoryObverser = new ToolHistoryAdapter() {

		/* (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolHistoryAdapter#fireToolHistoryAdded(int, com.dwarfeng.tp.core.model.struct.ToolHistory)
		 */
		@Override
		public void fireToolHistoryAdded(int index, ToolHistory toolHistory) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.insertRow(index, new Object[]{
							toolHistory.getName(),
							toolHistory.getRanDate(),
							toolHistory.getExitedDate(),
							toolHistory.getExitedCode()
							}
					);
				}
			});
		}

		/* (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolHistoryAdapter#fireToolHistoryRemoved(int, com.dwarfeng.tp.core.model.struct.ToolHistory)
		 */
		@Override
		public void fireToolHistoryRemoved(int index, ToolHistory toolHistory) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.removeRow(index);
				}
			});
		}

		/* (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolHistoryAdapter#fireCleared()
		 */
		@Override
		public void fireCleared() {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					for(int i = 0 ; i < tableModel.getRowCount() ; i ++){
						tableModel.removeRow(0);
					}
				}
			});
		}
		
	};
	private final ToolInfoObverser toolInfoObverser = new ToolInfoAdapter() {
		//TODO 如果这样做性能较差的话，则优化效率。

		/* (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolInfoAdapter#fireToolInfoAdded(com.dwarfeng.tp.core.model.struct.ToolInfo)
		 */
		@Override
		public void fireToolInfoAdded(ToolInfo toolInfo) {
			ToolPlatformUtil.invokeInEventQueue(runnable);
		}

		/* (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolInfoAdapter#fireToolInfoRemoved(com.dwarfeng.tp.core.model.struct.ToolInfo)
		 */
		@Override
		public void fireToolInfoRemoved(ToolInfo toolInfo) {
			ToolPlatformUtil.invokeInEventQueue(runnable);
		}

		/* (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.ToolInfoAdapter#fireCleared()
		 */
		@Override
		public void fireCleared() {
			ToolPlatformUtil.invokeInEventQueue(runnable);
		}
		
		private final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				table.repaint();
			}
		};
		
	};
	
	private DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer(){
		
		private static final long serialVersionUID = -4783430386389562891L;

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		@Override
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if((int) tableModel.getValueAt(row, 3) != 0){
				setForeground(Color.RED);
			}else{
				setForeground(null);
			}
			switch (column) {
			case 0:
				String name = (String) value;
				setText(name);
				if(toolInfoModel.contains(name)){
					setIcon(new ImageIcon(ImageUtil.scaleImage(toolInfoModel.get(name).getImage(), toolIconSize)));
				}else{
					setIcon(new ImageIcon(ImageUtil.scaleImage(unknownToolIcon, toolIconSize)));
				}
				break;
			case 1:
				setIcon(null);
				Date ranDate = (Date) value;
			    setText(DateUtil.formatDate(ranDate));
				break;
			case 2:
				setIcon(null);
				Date exitedDate = (Date) value;
			    setText(DateUtil.formatDate(exitedDate));
				break;
			case 3:
				setIcon(null);
				int exitedCode = (Integer)value;
			    setText(exitedCode + "");
				break;
			}
			return this;
		};
	};
	/**
	 * 新实例。
	 */
	public JToolHistoryPanel() {
		this(Constants.getDefaultLabelMutilang(), null, null);
	}
	
	/**
	 * 
	 * @param mutilang
	 */
	public JToolHistoryPanel(Mutilang mutilang, ToolHistoryModel toolHistoryModel, ToolInfoModel toolInfoModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");

		unknownToolIcon = ImageUtil.getImage(ImageKey.UNKNOWN, ImageSize.ICON_LARGE);
		
		this.mutilang = mutilang;
		
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		add(scrollPane);
		
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		table.setFillsViewportHeight(true);
		table.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int mouseRowIndex = table.rowAtPoint(e.getPoint());
				if(mouseRowIndex == -1){
					table.getSelectionModel().clearSelection();
					table.getSelectionModel().setAnchorSelectionIndex(-1);
					table.getSelectionModel().setLeadSelectionIndex(-1);
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int mouseRowIndex = table.rowAtPoint(e.getPoint());
				if(mouseRowIndex == -1){
					table.getSelectionModel().clearSelection();
					table.getSelectionModel().setAnchorSelectionIndex(-1);
					table.getSelectionModel().setLeadSelectionIndex(-1);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int mouseRowIndex = table.rowAtPoint(e.getPoint());
				if(mouseRowIndex == -1){
					table.getSelectionModel().clearSelection();
					table.getSelectionModel().setAnchorSelectionIndex(-1);
					table.getSelectionModel().setLeadSelectionIndex(-1);
				}
			}
		});
		table.getTableHeader().setReorderingAllowed(false);
		
		tableModel.setColumnIdentifiers(new String[]{
				getLabel(LabelStringKey.JToolHistoryPanel_1),
				getLabel(LabelStringKey.JToolHistoryPanel_2),
				getLabel(LabelStringKey.JToolHistoryPanel_3),
				getLabel(LabelStringKey.JToolHistoryPanel_4),
		});
		
		table.setModel(tableModel);
		
		for(int i = 0 ; i < table.getColumnCount() ; i ++){
			table.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer );
		}
		
		int iconHeight = toolIconSize.getHeight();
		int textHeight = tableCellRenderer.getFontMetrics(getFont()).getHeight();
		table.setRowHeight(Math.max(iconHeight, textHeight));
		table.repaint();
		
		scrollPane.setViewportView(table);
		
		if(Objects.nonNull(toolHistoryModel)){
			toolHistoryModel.addObverser(toolHistoryObverser);
			toolHistoryModel.getLock().readLock().lock();
			try{
				for(ToolHistory toolHistory : toolHistoryModel){
					tableModel.addRow(new Object[]{toolHistory.getName(), toolHistory.getRanDate(), toolHistory.getExitedDate()});
				}
			}finally {
				toolHistoryModel.getLock().readLock().unlock();
			}
		}
		
		this.toolHistoryModel = toolHistoryModel;
		
		if(Objects.nonNull(toolInfoModel)){
			toolInfoModel.addObverser(toolInfoObverser);
			toolInfoModel.getLock().readLock().lock();
			try{
				//TODO 如果这样做性能较差的话，则优化效率。
				table.repaint();
			}finally {
				toolInfoModel.getLock().readLock().unlock();
			}
		}
		
		this.toolInfoModel = toolInfoModel;
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
		tableModel.setColumnIdentifiers(new String[]{
				getLabel(LabelStringKey.JToolHistoryPanel_1),
				getLabel(LabelStringKey.JToolHistoryPanel_2),
				getLabel(LabelStringKey.JToolHistoryPanel_3),
		});
		return true;
	}

	/**
	 * 获取面板的工具历史模型。
	 * @return 面板的工具历史模型。
	 */
	public ToolHistoryModel getToolHistoryModel() {
		return toolHistoryModel;
	}

	/**
	 * 设置面板的工具历史模型。
	 * @param toolHistoryModel 指定的工具历史模型。
	 */
	public void setToolHistoryModel(ToolHistoryModel toolHistoryModel) {
		if(Objects.nonNull(this.toolHistoryModel)){
			this.toolHistoryModel.removeObverser(toolHistoryObverser);
		}
		if(Objects.nonNull(toolHistoryModel)){
			toolHistoryModel.addObverser(toolHistoryObverser);
			toolHistoryModel.getLock().readLock().lock();
			try{
				for(ToolHistory toolHistory : toolHistoryModel){
					tableModel.addRow(new Object[]{toolHistory.getName(), toolHistory.getRanDate(), toolHistory.getExitedDate()});
				}
			}finally {
				toolHistoryModel.getLock().readLock().unlock();
			}
		}
		this.toolHistoryModel = toolHistoryModel;
	}

	/**
	 * 返回面板的工具信息模型。
	 * @return 面板的工具信息模型。
	 */
	public ToolInfoModel getToolInfoModel() {
		return toolInfoModel;
	}

	/**
	 * 设置面板的工具信息模型。
	 * @param toolInfoModel 指定的模型。
	 */
	public void setToolInfoModel(ToolInfoModel toolInfoModel) {
		if(Objects.nonNull(this.toolInfoObverser)){
			this.toolHistoryModel.removeObverser(toolHistoryObverser);
		}
		if(Objects.nonNull(toolInfoModel)){
			toolInfoModel.addObverser(toolInfoObverser);
			toolInfoModel.getLock().readLock().lock();
			try{
				//TODO 如果这样做性能较差的话，则优化效率。
				table.repaint();
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
	public ImageSize getToolIconSize() {
		return toolIconSize;
	}

	/**
	 * 设置工具图标的大小。
	 * @param toolIconSize 工具图标的大小。
	 */
	public void setToolIconSize(ImageSize toolIconSize) {
		this.toolIconSize = toolIconSize;
		int iconHeight = toolIconSize.getHeight();
		int textHeight = tableCellRenderer.getFontMetrics(getFont()).getHeight();
		table.setRowHeight(Math.max(iconHeight, textHeight));
		table.repaint();
	}

	/**
	 * 释放资源。
	 */
	public void dispose(){
		tableModel.setDataVector(new Objects[0][4], new Object[4]);
		if(Objects.nonNull(toolHistoryModel)){
			toolHistoryModel.removeObverser(toolHistoryObverser);
		}
		if(Objects.nonNull(toolInfoModel)){
			toolInfoModel.removeObverser(toolInfoObverser);
		}
	}
	
	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}
	
}
