package com.dwarfeng.tp.core.view.gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JProgressBar;

import com.dwarfeng.tp.core.model.cfg.ImageKey;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Dimension;

public class JBackgroundItem extends JPanel {
	
	private static final long serialVersionUID = 5867014465163068792L;
	
	private int progress;
	private int totleProgress;
	private boolean determinateFlag;
	private boolean cancelable;
	private boolean cancleFlag;
	private boolean doneFlag;
	private String message;
	
	/*
	 * final ��
	 */
	private final JLabel iconLabel;
	private final JProgressBar progressBar;
	private final JButton cancelButton;
	private final JLabel messageLabel;
	

	/**
	 * ��ʵ����
	 */
	public JBackgroundItem() {
		this(0,0,false,false,false,false,"");
	}
	
	/**
	 * ��ʵ����
	 * @param progress
	 * @param totleProgress
	 * @param determinateFlag
	 * @param cancelable
	 * @param cancelFlag
	 * @param doneFlag
	 * @param message
	 */
	public JBackgroundItem(
			int progress,
			int totleProgress,
			boolean determinateFlag,
			boolean cancelable,
			boolean cancelFlag,
			boolean doneFlag,
			String message
		) {
		setPreferredSize(new Dimension(400, 50));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 28, 0};
		gridBagLayout.rowHeights = new int[]{28, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		iconLabel = new JLabel();
		iconLabel.setPreferredSize(new Dimension(28, 28));
		GridBagConstraints gbc_iconLabel = new GridBagConstraints();
		gbc_iconLabel.insets = new Insets(0, 0, 0, 5);
		gbc_iconLabel.gridx = 0;
		gbc_iconLabel.gridy = 0;
		add(iconLabel, gbc_iconLabel);
		
		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 0;
		add(progressBar, gbc_progressBar);
		
		cancelButton = new JButton("");
		cancelButton.setPreferredSize(new Dimension(28, 28));
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.gridheight = 2;
		gbc_cancelButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_cancelButton.insets = new Insets(0, 0, 5, 0);
		gbc_cancelButton.gridx = 2;
		gbc_cancelButton.gridy = 0;
		add(cancelButton, gbc_cancelButton);
		
		messageLabel = new JLabel("New label");
		GridBagConstraints gbc_messageLabel = new GridBagConstraints();
		gbc_messageLabel.fill = GridBagConstraints.VERTICAL;
		gbc_messageLabel.anchor = GridBagConstraints.WEST;
		gbc_messageLabel.insets = new Insets(0, 0, 0, 5);
		gbc_messageLabel.gridx = 1;
		gbc_messageLabel.gridy = 1;
		add(messageLabel, gbc_messageLabel);
		
		this.progress = progress;
		this.totleProgress = totleProgress;
		this.determinateFlag = determinateFlag;
		this.cancelable = cancelable;
		this.cancleFlag = cancelFlag;
		this.doneFlag = doneFlag;
		this.message = message;
		
		progressBar.setValue(progress);
		progressBar.setMaximum(totleProgress);
		progressBar.setIndeterminate(! determinateFlag);
		cancelButton.setEnabled(cancelable);
		if(cancelFlag){
			iconLabel.setIcon(new ImageIcon(ToolPlatformUtil.getImage(ImageKey.Canceled)));
		}else{
			if(doneFlag){
				iconLabel.setIcon(new ImageIcon(ToolPlatformUtil.getImage(ImageKey.Done)));
			}else{
				iconLabel.setIcon(new ImageIcon(ToolPlatformUtil.getImage(ImageKey.Processing)));
			}
		}
		messageLabel.setText(message);
	}

	/**
	 * ���ع��̵Ľ��ȡ�
	 * @return ���̵Ľ��ȡ�
	 */
	public int getProgress() {
		return progress;
	}
	
	/**
	 * ���ù��̵Ľ��ȡ�
	 * @param progress ָ���Ľ��ȡ�
	 */
	public void setProgress(int progress){
		this.progress = progress;
		progressBar.setValue(progress);
	}

	/**
	 * ���ع��̵��ܽ��ȡ�
	 * @return ���̵��ܽ��ȡ�
	 */
	public int getTotleProgress() {
		return totleProgress;
	}
	
	/**
	 * ���ù��̵��ܽ��ȡ�
	 * @param totleProgress ���̵��ܽ��ȡ�
	 */
	public void setTotleProgress(int totleProgress){
		this.totleProgress = totleProgress;
		progressBar.setMaximum(totleProgress);
	}

	/**
	 * ���ظù�����ȷ�����̻��ǲ�ȷ�����̡�
	 * @return �ù����Ƿ�Ϊȷ�����̡�
	 */
	public boolean isDeterminate() {
		return determinateFlag;
	}
	
	/**
	 * ���øù����Ƿ��ǲ�ȷ�����̡�
	 * @param aFlag �ù����Ƿ��ǲ�ȷ�����̡�
	 */
	public void setDeterminate(boolean aFlag){
		determinateFlag = aFlag;
		progressBar.setIndeterminate(! aFlag);
		revalidate();
	}

	/**
	 * ָʾ�ù����Ƿ��ܱ�ȡ����
	 * @return �ù����ܷ�ȡ����
	 */
	public boolean isCancelable() {
		return cancelable;
	}
	
	/**
	 * ���øù����Ƿ��ܹ���ȡ����
	 * @param aFlag �ù����Ƿ��ܹ���ȡ����
	 */
	public void setCancelable(boolean aFlag){
		cancelable = aFlag;
		cancelButton.setEnabled(aFlag);
	}

	/**
	 * ���ظù����Ƿ�ȡ����
	 * @return �ù����Ƿ�ȡ����
	 */
	public boolean isCancel() {
		return cancleFlag;
	}
	
	public void setCancel(boolean aFlag){
		cancleFlag = aFlag;
		if(aFlag){
			iconLabel.setIcon(new ImageIcon(ToolPlatformUtil.getImage(ImageKey.Canceled)));
		}else{
			if(doneFlag){
				iconLabel.setIcon(new ImageIcon(ToolPlatformUtil.getImage(ImageKey.Done)));
			}else{
				iconLabel.setIcon(new ImageIcon(ToolPlatformUtil.getImage(ImageKey.Processing)));
			}
		}
	}

	/**
	 * ���ظù����Ƿ���ɡ�
	 * @return �ù����Ƿ���ɡ�
	 */
	public boolean isDone() {
		return doneFlag;
	}
	
	/**
	 * ���ù����Ƿ���ɡ�
	 * @param aFlag �����Ƿ���ɡ�
	 */
	public void setDone(boolean aFlag){
		doneFlag = aFlag;
		if(!cancleFlag){
			if(aFlag){
				iconLabel.setIcon(new ImageIcon(ToolPlatformUtil.getImage(ImageKey.Done)));
			}else{
				iconLabel.setIcon(new ImageIcon(ToolPlatformUtil.getImage(ImageKey.Processing)));
			}
		}
	}

	/**
	 * ���ظù��̵���Ϣ��
	 * @return �ù��̵���Ϣ��
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * ���ù��̵���Ϣ��
	 * @param message ָ������Ϣ��
	 */
	public void setMesssge(String message){
		this.message = message;
		messageLabel.setText(message);
	}

}
