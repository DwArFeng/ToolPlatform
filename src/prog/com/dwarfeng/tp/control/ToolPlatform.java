package com.dwarfeng.tp.control;

import java.util.Map;
import java.util.Objects;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.tp.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.model.cfg.Mutilang;
import com.dwarfeng.tp.model.struct.EmergencyException;
import com.dwarfeng.tp.model.struct.ProgramLogger;
import com.dwarfeng.tp.model.struct.ProgramResource;
import com.dwarfeng.tp.view.ViewUtil;

/**
 * ToolPlatform（DwArFeng 的工具平台）。
 * <p> 该工具平台是用来管理 DwArFeng 编写的众多的工具的。
 * 该工具平台利用反射读取其工具目录下的所有工具，并且拥有将这些工具进行分标签管理、搜索、分类的功能。
 * <p> TODO 需要进行详细的描述。
 * @author  DwArFeng
 * @since 1.8
 */
public final class ToolPlatform {
	
	public static void main(String[] args) {
		new ToolPlatform();
	}
	
	/**程序的固定属性*/
	public static final ProgramAttributes ATTRIBUTES = new ProgramAttributes();
	
	
//	private final ModelManager modelManager;
//	private final ViewManager viewManager;
	
	/**
	 * 生成一个默认的工具平台实例。
	 * 生成一个具有指定 TODO
	 */
	public ToolPlatform() {
		this(ATTRIBUTES.defaultProgramLogger, ATTRIBUTES.defaultLoggerMutilang);
	}
	
	/**
	 * 
	 * @param preLogger
	 * @param preMutilang
	 */
	public ToolPlatform(ProgramLogger preLogger, Mutilang<LoggerStringKey> preMutilang){
		Objects.requireNonNull(preLogger, "入口参数 preLogger 不能为 null。");
		Objects.requireNonNull(preMutilang, "入口参数 preLoggerMutilang 不能为 null。");

		try{
			Map<String, ProgramResource> resourceMap = null;
			ProgramLogger logger = null;
			ConfigModel programConfigModel = null;
			
			try {
				resourceMap = ATTRIBUTES.resourceLoader.loadResources();
			} catch (EmergencyException e) {
				ViewUtil.showEmergentMessage(e.getTitle(), e.getMessage());
				System.exit(100);
			}
			
			try {
				logger = ATTRIBUTES.loggerGenerator.newInstance(resourceMap, preLogger, preMutilang);
			} catch (EmergencyException e) {
				ViewUtil.showEmergentMessage(e.getTitle(), e.getMessage());
				System.exit(101);
			}
			
			try {
				programConfigModel = ATTRIBUTES.programConfigGenerator.newInstance(resourceMap, logger, preMutilang);
			} catch (EmergencyException e) {
				ViewUtil.showEmergentMessage(e.getTitle(), e.getMessage());
				System.exit(102);
			}
			
			
		}finally {
			preLogger.stop();
		}
	}
	
}
