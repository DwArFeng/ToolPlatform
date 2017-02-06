package com.dwarfeng.tp.core.control;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.dwarfeng.dutil.basic.io.CT;

public final class Foo {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				CT.trace(JOptionPane.showConfirmDialog(null, "123"));
			}
		});
		CT.trace("end");
	}

}

