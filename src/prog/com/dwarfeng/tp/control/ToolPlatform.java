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
 * ToolPlatform��DwArFeng �Ĺ���ƽ̨����
 * <p> �ù���ƽ̨���������� DwArFeng ��д���ڶ�Ĺ��ߵġ�
 * �ù���ƽ̨���÷����ȡ�乤��Ŀ¼�µ����й��ߣ�����ӵ�н���Щ���߽��зֱ�ǩ��������������Ĺ��ܡ�
 * <p> TODO ��Ҫ������ϸ��������
 * @author  DwArFeng
 * @since 1.8
 */
public final class ToolPlatform {

	/*
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 *                                                                                              ��̬�ֶ�
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	/**����İ汾*/
	public final static Version VERSION = new DefaultVersion.Builder()
			.type(VersionType.RELEASE)
			.firstVersion((byte) 0)
			.secondVersion((byte) 0)
			.thirdVersion((byte) 0)
			.buildDate("20161222")
			.buildVersion('A')
			.build();
	/**���������*/
	public static final String AUTHOR = "DwArFeng";
	/**���ʻ���Դ�Ļ�����*/
	public static final String INTERNATIONAL_BASENAME= "com/dwarfeng/tp/resource/international/stringField";
	/**��¼���ʻ���֧�����Ե�·��*/
	public static final URL URL_INTERNATIONAL_SUPPORT = 
			ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/international/supported.xml");
	/**��������ļ�*/
	public static File FILE_CONFIG_APPEARANCE = new File("configuration" + File.separatorChar + "appearance.cfg") ;
	/**���������ļ�*/
	public static File FILE_CONFIG_PROGRAM = new File("configuration" + File.separatorChar + "program.cfg");
	
	
	
	
	public static void main(String[] args) throws IOException {
		new ToolPlatform();
	}
	
	/*
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 *                                                                                             �Ǿ�̬
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 */
	
//	private final ModelManager modelManager;
//	private final ViewManager viewManager;
	
	public ToolPlatform() throws IOException {
		//InitProcessor initProcessor = new InitProcessor();
		
	}
	
}
