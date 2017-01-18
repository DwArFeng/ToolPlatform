package com.dwarfeng.tp.core.control;

import java.io.File;

import com.dwarfeng.dutil.basic.io.CT;

public final class Foo {
	
	public static void main(String[] args) {
		File dir = new File("lib/");
		File[] jars = dir.listFiles();
		
		for(File file : jars){
			CT.trace(file.getName().substring(0, file.getName().length()-4));
		}
	}

}

