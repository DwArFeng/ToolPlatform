package com.dwarfeng.tp.control;

import java.net.URL;

import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;

/**
 * 记录程序的一些固定不变的属性。
 * @author  DwArFeng
 * @since 1.8
 */
public class ProgramAttributes {
	
	/**程序的版本*/
	public final Version VERSION = new DefaultVersion.Builder()
			.type(VersionType.RELEASE)
			.firstVersion((byte) 0)
			.secondVersion((byte) 0)
			.thirdVersion((byte) 0)
			.buildDate("20161222")
			.buildVersion('A')
			.build();
	
	/**程序的作者*/
	public final String AUTHOR = "DwArFeng";
	
	/**程序的记录器路径的默认位置*/
	public final URL LOGGER_PATH = ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/logger-path.xml");
	
	/**Logger生成失败的标题*/
	public final String LOGGER_FAIL_TITLE = "Logger 生成失败";
	
	/**Logger生成失败信息0*/
	public final String LOGGER_FAIL_MESSAGE_0 = "xml文件解析失败，请检查系统配置或程序的完整性。可能的原因是：\n"
			+ "\t1. 指定的xml路径无效。\n"
			+ "\t2. xml中的内容损坏。";
	
	/**Logger生成失败信息1*/
	public final String LOGGER_FAIL_MESSAGE_1 = "xml文件解析失败，请检配置文件的正确性。可能的原因是：\n"
			+ "\t1. xml中的内容损坏。\n"
			+ "\t2. xml中找不到含有属性为 key=\"logger-cfg\" 的 info 元素。";

	public final String LOGGER_FAIL_MESSAGE_2 = "xml文件解析失败，请检配置文件的正确性。可能的原因是：\n"
			+ "\t1. xml中 info 元素下子节点缺失。\n"
			+ "\t2. xml中 default-path 中的 type 属性含有未知的值。\n"
			+ "\t3. xml中 release-path 中的 type 属性含有未知的值。\n"
			+ "\t4. xml中 release-path 中的 type 属性程序尚不支持。";
	
	public final String LOGGER_FAIL_MESSAGE_3 = "文件无法被正确的释放。可能的原因是：\n"
			+ "\t1. 文件被占用。\n"
			+ "\t2. xml中相应的配置处文件名、目录名或卷标语法不正确。\n";
	
	public final String LOGGER_FAIL_MESSAGE_4 = "Logger配置无法被正确的读取。可能的原因是：\n"
			+ "\tLogger配置不正确";
	
	public final String LOGGER_FAIL_MESSAGE_5 = "Logger无法正确生成。";
	
	public final String MISSING_STRING = "!文本域丢失";
	
}
