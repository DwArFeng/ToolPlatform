package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Calendar;
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

import com.dwarfeng.dutil.basic.gui.swing.MuaListModel;
import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.tp.core.model.cfg.ImageSize;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cm.ToolRuntimeModel;
import com.dwarfeng.tp.core.model.obv.ToolRuntimeAdapter;
import com.dwarfeng.tp.core.model.obv.ToolRuntimeObverser;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;
import com.dwarfeng.tp.core.model.struct.RunningTool;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

public class JToolRuntimePanel extends JPanel implements MutilangSupported{
	
	private final JList<RunningTool> list;
	private final JToolRuntimeConsoleContainer consoleContainer;

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
		 * @see com.dwarfeng.tp.core.model.obv.ToolRuntimeAdapter#fireRunningToolExited(com.dwarfeng.tp.core.model.struct.RunningTool)
		 */
		@Override
		public void fireRunningToolExited(RunningTool runningTool) {
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					JTpconsole console = toolConsoleMap.get(runningTool);
					if(Objects.nonNull(console)){
						console.out.println(formatLabel(LabelStringKey.JToolRuntimePanel_1, Calendar.getInstance().getTime(), runningTool.getExitCode()));
						console.input("\n");
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
			setIcon(new ImageIcon(ToolPlatformUtil.scaleImage(runningTool.getImage(), toolInfoIconSize)));
			return this;
		}
		
	};
	private final Map<RunningTool, JTpconsole> toolConsoleMap = new HashMap<>();
	/**
	 * ��ʵ����
	 */
	public JToolRuntimePanel() {
		this(ToolPlatformUtil.newDefaultLabelMutilang(), null);
	}
	
	/**
	 * ��ʵ����
	 * @param toolRuntimeModel
	 */
	public JToolRuntimePanel(Mutilang mutilang, ToolRuntimeModel toolRuntimeModel) {
		Objects.requireNonNull(mutilang, "��ڲ��� mutilang ����Ϊ null��");
		
		this.mutilang = mutilang;
		
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
					int leadIndex;
					consoleContainer.removeAll();
					if((leadIndex = list.getSelectionModel().getLeadSelectionIndex()) >= 0 && listModel.size() > 0){
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
	 * ��ȡ����еĹ�������ʱģ�͡�
	 * @return ����еĹ�������ʱģ�͡�
	 */
	public ToolRuntimeModel getToolRuntimeModel() {
		return toolRuntimeModel;
	}

	/**
	 * ��������еĹ�������ʱģ��Ϊָ����ģ�͡�
	 * <p> �����ǰ�Ĺ�������ʱģ�Ͳ�Ϊ <code>null</code>�����ҽ�����ǰ��ģ����û���κ�Ԫ�غ󣬲��������µ�ģ�͡�
	 * @param toolRuntimeModel ָ���Ĺ�������ʱģ�͡�
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
	 * ��ȡ�����й���ͼ��Ĵ�С��
	 * @return ����ͼ��Ĵ�С��
	 */
	public ImageSize getToolInfoIconSize() {
		return toolInfoIconSize;
	}

	/**
	 * ���������й���ͼ��Ĵ�С��
	 * @param toolInfoIconSize ����ͼ��Ĵ�С��
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
		Objects.requireNonNull(mutilang , "��ڲ��� mutilang ����Ϊ null��");
		
		if(Objects.equals(mutilang, this.mutilang)) return false;
		this.mutilang = mutilang;
		for(JTpconsole console : toolConsoleMap.values()){
			console.setMutilang(mutilang);
		}
		return true;
	}
	
	/**
	 * Ϊָ���������й���ָ�����������������
	 * <p> ���ҽ�����ڲ�����Ϊ <code>null</code>�������뵱ǰ�� toolRuntimeModel��ʱ�򣬲��ܹ�ָ�ɳɹ���
	 * @param runningTool ָ���������й��ߡ�
	 * @return �Ƿ���ܸ�ָ�ɡ�
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
	 * �ͷ���Դ��
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

//	private String getLabel(LabelStringKey labelStringKey){
//		return mutilang.getString(labelStringKey.getName());
//	}
	
	private String formatLabel(LabelStringKey labelStringKey, Object... args){
		return String.format(mutilang.getString(labelStringKey.getName()), args);
	}

}
