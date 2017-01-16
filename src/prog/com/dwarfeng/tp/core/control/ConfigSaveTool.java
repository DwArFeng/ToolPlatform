package com.dwarfeng.tp.core.control;

import java.io.File;
import java.io.FileOutputStream;

import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.dutil.develop.cfg.DefaultConfigModel;
import com.dwarfeng.dutil.develop.cfg.io.PropertiesConfigSaver;
import com.dwarfeng.dutil.develop.cfg.io.StreamConfigSaver;
import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.model.cfg.InvisibleConfig;

/**
 * ”√”⁄≤‚ ‘
 * @author  DwArFeng
 * @since 1.8
 */
final class ConfigSaveTool {

	public static void main(String[] args) throws Exception {
		ConfigModel coreConfigModel = new DefaultConfigModel(CoreConfig.values());
		ConfigModel invisibleConfigModel = new DefaultConfigModel(InvisibleConfig.values());
		File file = new File("src\\prog\\com\\dwarfeng\\tp\\resource\\defaultres\\configuration\\core.properties");
		FileUtil.createFileIfNotExists(file);
		StreamConfigSaver saver = null;
		try{
			saver = new PropertiesConfigSaver(new FileOutputStream(file));
			saver.save(coreConfigModel);
		}finally {
			saver.close();
		}
		
		file = new File("src\\prog\\com\\dwarfeng\\tp\\resource\\defaultres\\configuration\\invisible.properties");
		FileUtil.createFileIfNotExists(file);
		saver = null;
		try{
			saver = new PropertiesConfigSaver(new FileOutputStream(file));
			saver.save(invisibleConfigModel);
		}finally {
			saver.close();
		}
		
		file = new File("Attributes\\");
		FileUtil.deleteFile(file);
	}

}
