package com.dwarfeng.tp.control;

import java.io.File;
import java.util.Locale;

import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;

/**
 * ToolPlatform（DwArFeng 的工具平台）。
 * <p> 该工具平台是用来管理 DwArFeng 编写的众多的工具的。
 * 该工具平台利用反射读取其工具目录下的所有工具，并且拥有将这些工具进行分标签管理、搜索、分类的功能。
 * <p> TODO 需要进行详细的描述。
 * @author  DwArFeng
 * @since 1.8
 */
public final class ToolPlatform {

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
	
	/**程序支持的所有语言*/
	public final static Locale[] SUPPORTED_LOCALES = new Locale[]{
			Locale.CHINESE,
	};
	
	/**外观配置文件*/
	public static File FILE_CONFIG_APPEARANCE = new File("configuration" + File.separatorChar + "appearance.cfg") ;
	
	/**程序配置文件*/
	public static File FILE_CONFIG_PROGRAM = new File("configuration" + File.separatorChar + "program.cfg");
	
	
	
}
