package com.dwarfeng.tp.control;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.tp.model.ModelManager;
import com.dwarfeng.tp.model.init.InitProcessor;
import com.dwarfeng.tp.plaf.Tool;
import com.dwarfeng.tp.view.ViewManager;

import sun.applet.Main;

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
	
	/**程序的版本*/
	public final static Version VERSION = new DefaultVersion.Builder()
			.type(VersionType.RELEASE)
			.firstVersion((byte) 0)
			.secondVersion((byte) 0)
			.thirdVersion((byte) 0)
			.buildDate("20161222")
			.buildVersion('A')
			.build();
	/**程序的作者*/
	public static final String AUTHOR = "DwArFeng";
	/**国际化资源的基名称*/
	public static final String INTERNATIONAL_BASENAME= "com/dwarfeng/tp/resource/international/stringField";
	/**记录国际化的支持语言的路径*/
	public static final URL URL_INTERNATIONAL_SUPPORT = 
			ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/international/supported.xml");
	/**外观配置文件*/
	public static File FILE_CONFIG_APPEARANCE = new File("configuration" + File.separatorChar + "appearance.cfg") ;
	/**程序配置文件*/
	public static File FILE_CONFIG_PROGRAM = new File("configuration" + File.separatorChar + "program.cfg");
	
	
	
	
	public static void main(String[] args) throws IOException {
		new ToolPlatform();
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
	
	public ToolPlatform() throws IOException {
		//InitProcessor initProcessor = new InitProcessor();
		
	}
	
}
