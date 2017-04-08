package com.dwarfeng.tp.core.control;

import java.io.File;
import java.io.FileOutputStream;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.dutil.develop.cfg.DefaultConfigModel;
import com.dwarfeng.dutil.develop.cfg.io.PropConfigSaver;
import com.dwarfeng.tp.core.model.eum.CoreConfig;
import com.dwarfeng.tp.core.model.eum.ModalConfig;

/**
 * 用于测试
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
final class ConfigSaveTool {

	public static void main(String[] args) throws Exception {
		ConfigModel coreConfigModel = new DefaultConfigModel(CoreConfig.values());
		ConfigModel modalConfigModel = new DefaultConfigModel(ModalConfig.values());
		File file = new File("src\\prog\\com\\dwarfeng\\tp\\resource\\defaultres\\configuration\\core.properties");
		FileUtil.createFileIfNotExists(file);
		PropConfigSaver saver = null;
		try{
			saver = new PropConfigSaver(new FileOutputStream(file));
			saver.save(coreConfigModel);
		}finally {
			saver.close();
		}
		
		file = new File("src\\prog\\com\\dwarfeng\\tp\\resource\\defaultres\\configuration\\modal.properties");
		FileUtil.createFileIfNotExists(file);
		saver = null;
		try{
			saver = new PropConfigSaver(new FileOutputStream(file));
			saver.save(modalConfigModel);
		}finally {
			saver.close();
		}
		
		file = new File("Attributes\\");
		FileUtil.deleteFile(file);
		
		CT.trace("配置保存完成 !");
	}

}
