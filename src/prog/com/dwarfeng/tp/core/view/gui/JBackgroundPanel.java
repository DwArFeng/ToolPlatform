package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.dwarfeng.dutil.basic.gui.swing.MuaListModel;
import com.dwarfeng.tp.core.model.cm.BackgroundModel;
import com.dwarfeng.tp.core.model.obv.BackgroundAdapter;
import com.dwarfeng.tp.core.model.obv.BackgroundObverser;
import com.dwarfeng.tp.core.model.struct.Flow;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

import sun.swing.DefaultLookup;

public class JBackgroundPanel extends JPanel {
	
	private BackgroundModel backgroundModel;
	
	/*
	 * final 域。
	 */
	private final MuaListModel<Flow> listModel =  new MuaListModel<>();
	private final ListCellRenderer<Flow> listCellRenderer =  new ListCellRenderer<Flow>() {

		@Override
		public Component getListCellRendererComponent(JList<? extends Flow> list, Flow value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JBackgroundItem progressItem = progressItems.get(index);

	        Color bg = null;
	        Color fg = null;

	        JList.DropLocation dropLocation = list.getDropLocation();
	        if (dropLocation != null
	                && !dropLocation.isInsert()
	                && dropLocation.getIndex() == index) {

	            bg = new Color(DefaultLookup.getColor(progressItem, ui, "List.dropCellBackground").getRGB());
	            fg = new Color(DefaultLookup.getColor(progressItem, ui, "List.dropCellForeground").getRGB());

	            isSelected = true;
	        }

	        if (isSelected) {
	        	progressItem.setBackground(bg == null ? list.getSelectionBackground() : bg);
	        	progressItem.setForeground(fg == null ? list.getSelectionForeground() : fg);
	        }
	        else {
	        	progressItem.setBackground(new Color(list.getBackground().getRGB()));
	        	progressItem.setForeground(new Color(list.getForeground().getRGB()));
	        }

	        setEnabled(list.isEnabled());

	        Border border = null;
	        if (cellHasFocus) {
	            if (isSelected) {
	                border = DefaultLookup.getBorder(progressItem, ui, "List.focusSelectedCellHighlightBorder");
	            }
	            if (border == null) {
	                border = DefaultLookup.getBorder(progressItem, ui, "List.focusCellHighlightBorder");
	            }
	        } else {
	            border = new EmptyBorder(1, 1, 1, 1);
	        }
	        progressItem.setBorder(border);

	        return progressItem;
		}
		
	};
	private final BackgroundObverser backgroundObverser = new BackgroundAdapter() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.BackgroundObverser#fireFlowTotleProgressChanged(com.dwarfeng.tp.core.model.struct.Flow, int, int)
		 */
		@Override
		public void fireFlowTotleProgressChanged(Flow flow, int oldValue, int newValue) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = listModel.indexOf(flow);
					JBackgroundItem progressItem = progressItems.get(index);
					progressItem.setTotleProgress(newValue);
					listModel.set(index, flow);
				}
			});
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.BackgroundAdapter#fireFlowRemoved(com.dwarfeng.tp.core.model.struct.Flow)
		 */
		@Override
		public void fireFlowRemoved(Flow flow) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = listModel.indexOf(flow);
					listModel.remove(index);
					progressItems.remove(index);
				}
			});
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.BackgroundAdapter#fireFlowProgressChanged(com.dwarfeng.tp.core.model.struct.Flow, int, int)
		 */
		@Override
		public void fireFlowProgressChanged(Flow flow, int oldValue, int newValue) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = listModel.indexOf(flow);
					JBackgroundItem progressItem = progressItems.get(index);
					progressItem.setProgress(newValue);
					listModel.set(index, flow);
				}
			});
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.BackgroundAdapter#fireFlowMessageChanged(com.dwarfeng.tp.core.model.struct.Flow, java.lang.String, java.lang.String)
		 */
		@Override
		public void fireFlowMessageChanged(Flow flow, String oldValue, String newValue) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = listModel.indexOf(flow);
					JBackgroundItem progressItem = progressItems.get(index);
					progressItem.setMesssge(newValue);
					listModel.set(index, flow);
				}
			});
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.BackgroundAdapter#fireFlowDone(com.dwarfeng.tp.core.model.struct.Flow)
		 */
		@Override
		public void fireFlowDone(Flow flow) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = listModel.indexOf(flow);
					JBackgroundItem progressItem = progressItems.get(index);
					progressItem.setDone(true);
					listModel.set(index, flow);
				}
			});
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.BackgroundAdapter#fireFlowDeterminateChanged(com.dwarfeng.tp.core.model.struct.Flow, boolean, boolean)
		 */
		@Override
		public void fireFlowDeterminateChanged(Flow flow, boolean oldValue, boolean newValue) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = listModel.indexOf(flow);
					JBackgroundItem progressItem = progressItems.get(index);
					progressItem.setDeterminate(newValue);
					listModel.set(index, flow);
				}
			});
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.BackgroundAdapter#fireFlowCanceled(com.dwarfeng.tp.core.model.struct.Flow)
		 */
		@Override
		public void fireFlowCanceled(Flow flow) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = listModel.indexOf(flow);
					JBackgroundItem progressItem = progressItems.get(index);
					progressItem.setCancel(true);
					listModel.set(index, flow);
				}
			});
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.BackgroundAdapter#fireFlowCancelableChanged(com.dwarfeng.tp.core.model.struct.Flow, boolean, boolean)
		 */
		@Override
		public void fireFlowCancelableChanged(Flow flow, boolean oldValue, boolean newValue) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = listModel.indexOf(flow);
					JBackgroundItem progressItem = progressItems.get(index);
					progressItem.setCancelable(newValue);
					listModel.set(index, flow);
				}
			});
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.obv.BackgroundAdapter#fireFlowAdded(com.dwarfeng.tp.core.model.struct.Flow)
		 */
		@Override
		public void fireFlowAdded(Flow flow) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					progressItems.add(new JBackgroundItem(
							flow.getProgress(),
							flow.getTotleProgress(), 
							flow.isDeterminate(), 
							flow.isCancelable(), 
							flow.isCancel(), 
							flow.isDone(), 
							flow.getMessage()
					));
					listModel.add(flow);
				}
			});
		}
	};
	private final List<JBackgroundItem> progressItems = new ArrayList<>();
	private final JList<Flow> flowList;
	
	/**
	 * 新实例。
	 */
	public JBackgroundPanel() {
		this(null);
	}
	
	/**
	 * 新实例。
	 * @param backgroundModel 指定的后台模型。
	 */
	public JBackgroundPanel(BackgroundModel backgroundModel){
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		add(scrollPane, BorderLayout.CENTER);
		
		flowList = new JList<>();
		flowList.setModel(listModel);
		flowList.setCellRenderer(listCellRenderer);
		scrollPane.setViewportView(flowList);
		
		if(Objects.nonNull(backgroundModel)){
			backgroundModel.addObverser(backgroundObverser);
			backgroundModel.getLock().readLock().lock();
			try{
				for(Flow flow : backgroundModel){
					progressItems.add(new JBackgroundItem(
							flow.getProgress(),
							flow.getTotleProgress(), 
							flow.isDeterminate(), 
							flow.isCancelable(), 
							flow.isCancel(), 
							flow.isDone(), 
							flow.getMessage()
					));
					listModel.add(flow);
				}
			}finally {
				backgroundModel.getLock().readLock().unlock();
			}
		}
		this.backgroundModel = backgroundModel;
	}
	
	/**
	 * 获取面板的后台模型。
	 * @return 面板的后台模型。
	 */
	public BackgroundModel getBackgroundModel(){
		return backgroundModel;
	}
	
	public void setBackgroundModel(BackgroundModel backgroundModel){
		if(Objects.nonNull(this.backgroundModel)){
			this.backgroundModel.removeObverser(backgroundObverser);
			listModel.clear();
			progressItems.clear();
		}
		if(Objects.nonNull(backgroundModel)){
			backgroundModel.addObverser(backgroundObverser);
			backgroundModel.getLock().readLock().lock();
			try{
				for(Flow flow : backgroundModel){
					progressItems.add(new JBackgroundItem(
							flow.getProgress(),
							flow.getTotleProgress(), 
							flow.isDeterminate(), 
							flow.isCancelable(), 
							flow.isCancel(), 
							flow.isDone(), 
							flow.getMessage()
					));
					listModel.add(flow);
				}
			}finally {
				backgroundModel.getLock().readLock().unlock();
			}
		}
		this.backgroundModel = backgroundModel;
	}
	
	/**
	 * 释放资源。
	 */
	public void dispose(){
		if(Objects.nonNull(this.backgroundModel)){
			backgroundModel.removeObverser(backgroundObverser);
			listModel.clear();
			progressItems.clear();
		}
	}

}
