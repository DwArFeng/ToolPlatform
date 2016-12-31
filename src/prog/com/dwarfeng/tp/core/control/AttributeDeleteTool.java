package com.dwarfeng.tp.core.control;

import java.io.File;

import com.dwarfeng.dutil.basic.io.FileUtil;

public final class AttributeDeleteTool {

	public static void main(String[] args) {
		File file = new File("Attributes\\");
		FileUtil.deleteFile(file);
	}

}
