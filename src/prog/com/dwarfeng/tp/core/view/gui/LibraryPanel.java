package com.dwarfeng.tp.core.view.gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class LibraryPanel extends JPanel {
	
	

	/**
	 * Create the panel.
	 */
	public LibraryPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JList list = new JList();
		scrollPane.setViewportView(list);

	}

}
