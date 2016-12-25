package com.dwarfeng.tp.control;

import java.io.File;
import java.net.URL;
import java.util.Locale;

import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.tp.model.ModelManager;
import com.dwarfeng.tp.view.ViewManager;

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
	
	/**��¼*/
	public static final URL URL_LANG_SUPPORT_INFO = ToolPlatform.class.getResource("/com/dwarfeng/to/resource/lang/supported.xml");
	
	/**��������ļ�*/
	public static File FILE_CONFIG_APPEARANCE = new File("configuration" + File.separatorChar + "appearance.cfg") ;
	
	/**���������ļ�*/
	public static File FILE_CONFIG_PROGRAM = new File("configuration" + File.separatorChar + "program.cfg");
	
	
	
	
	
	
	/*
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 *                                                                                             �Ǿ�̬
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	private final ModelManager modelManager;
	private final ViewManager viewManager;
	
	public ToolPlatform() {
		
	}
	
}
