package com.dwarfeng.tp.core.view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.dwarfeng.dutil.basic.gui.swing.JExconsole;
import com.dwarfeng.dutil.basic.gui.swing.JMenuItemAction;
import com.dwarfeng.tp.core.model.cfg.ImageKey;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

public final class JTpconsole extends JExconsole implements MutilangSupported{
	
	/**右键菜单*/
	private JPopupMenu popup;
	/**多语言接口*/
	private Mutilang mutilang;
	
	public JTpconsole() {
		this(ToolPlatformUtil.newDefaultLabelMutilang());
	}
	
	public JTpconsole(Mutilang mutilang){
		super();
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.gui.swing.JExconsole#createPopup()
	 */
	@Override
	protected JPopupMenu createPopup() {
		this.popup = new InnerPopupMenu();
		return this.popup;
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
		//TODO
		return true;
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	};
	
	
	private final class InnerPopupMenu extends JPopupMenu{
		
		private final JMenuItem selectAllMenuItem;
		private final JMenuItem cleanScreenMenuItem;
		private final JCheckBoxMenuItem lineWrapMenuItem;
		
		public InnerPopupMenu() {
			super();
			selectAllMenuItem = add(
					new JMenuItemAction.Builder()
					.icon(new ImageIcon(ToolPlatformUtil.getImage(ImageKey.SelectAll)))
					.name("")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK))
					.mnemonic('A')
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(Objects.isNull(textArea)) return;
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									textArea.requestFocus();
									textArea.select(0, textArea.getText().length());
								}
							});
						}
					})
					.build()
			);
			
			cleanScreenMenuItem = add(
					new JMenuItemAction.Builder()
					.icon(new ImageIcon(ToolPlatformUtil.getImage(ImageKey.ClearScreen)))
					.name("")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK))
					.mnemonic('E')
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(Objects.isNull(textArea)) return;
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									textArea.requestFocus();
									textArea.setText("");
								}
							});
						}
					})
					.build()
			);
			
			addSeparator();
			
			lineWrapMenuItem = new JCheckBoxMenuItem("");
			lineWrapMenuItem.setIcon(null);
			lineWrapMenuItem.setMnemonic('W');
			lineWrapMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(Objects.isNull(textArea)) return;
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							textArea.setLineWrap(lineWrapMenuItem.getState());
						}
					});
				}
			});
			add(lineWrapMenuItem);
		}
	}

}
