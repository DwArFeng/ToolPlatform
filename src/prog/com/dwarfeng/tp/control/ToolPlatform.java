package com.dwarfeng.tp.control;

import java.io.File;
import java.net.URL;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.model.init.InitFactory;
import com.dwarfeng.tp.model.io.ProgramLogger;
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

	/*
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 *                                                                                              静态字段
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	/**国际化资源的基名称*/
	public static final String MUTILANG_BASENAME= "com/dwarfeng/tp/resource/mutilang/stringField";
	/**记录国际化的支持语言的路径*/
	public static final URL URL_MUTILANG_SUPPORT = 
			ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/mutilang/supported.xml");
	/**外观配置文件*/
	public static File FILE_CONFIG_APPEARANCE = new File("configuration" + File.separatorChar + "appearance.cfg") ;
	/**程序配置文件*/
	public static File FILE_CONFIG_PROGRAM = new File("configuration" + File.separatorChar + "program.cfg");
	/**程序的固定属性*/
	public static final ProgramAttributes ATTRIBUTES = new ProgramAttributes();
	
	
	
	public static void main(String[] args) {
		new ToolPlatform(ATTRIBUTES.LOGGER_PATH, true);
	}
	
	/*
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 *                                                                                             非静态
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 */
	
//	private final ModelManager modelManager;
//	private final ViewManager viewManager;
	
	/**
	 * 生成一个默认的工具平台实例。
	 */
	public ToolPlatform() {
		//this(ATTRIBUTES.LOGGER_PATH, false);
		
	}
	
//	/**
//	 * 生成一个具有指定 TODO
//	 * @param loggerPath
//	 * @param forceOverride
//	 */
//	public ToolPlatform(URL loggerPath, boolean forceOverride){
//		ProgramLogger logger = null;
//		
//		try {
//			logger = InitFactory.newProgramLogger(loggerPath, forceOverride);
//		} catch (LoadFailedException e) {
//			ViewUtil.showEmergentMessage(ATTRIBUTES.LOGGER_FAIL_TITLE, e.getMessage());
//			System.exit(1);
//		}
//		
//	}
	
}
