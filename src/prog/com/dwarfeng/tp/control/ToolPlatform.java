package com.dwarfeng.tp.control;

import java.io.File;
import java.net.URL;
import java.util.Map;

import org.dom4j.DocumentException;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.tp.model.io.ProgramLogger;
import com.dwarfeng.tp.model.io.ProgramResource;
import com.dwarfeng.tp.model.struct.EmergencyException;
import com.dwarfeng.tp.view.ViewUtil;

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
	
	/**���ʻ���Դ�Ļ�����*/
	public static final String MUTILANG_BASENAME= "com/dwarfeng/tp/resource/mutilang/stringField";
	/**��¼���ʻ���֧�����Ե�·��*/
	public static final URL URL_MUTILANG_SUPPORT = 
			ToolPlatform.class.getResource("/com/dwarfeng/tp/resource/mutilang/supported.xml");
	/**��������ļ�*/
	public static File FILE_CONFIG_APPEARANCE = new File("configuration" + File.separatorChar + "appearance.cfg") ;
	/**���������ļ�*/
	public static File FILE_CONFIG_PROGRAM = new File("configuration" + File.separatorChar + "program.cfg");
	/**����Ĺ̶�����*/
	public static final ProgramAttributes ATTRIBUTES = new ProgramAttributes();
	
	
	
	public static void main(String[] args) {
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
	
	/**
	 * ����һ��Ĭ�ϵĹ���ƽ̨ʵ����
	 * ����һ������ָ�� TODO
	 */
	public ToolPlatform() {
		//this(ATTRIBUTES.LOGGER_PATH, false);
		Map<String, ProgramResource> resourceMap = null;
		ProgramLogger logger = null;
		
		try {
			resourceMap = ATTRIBUTES.resourceLoader.loadResources();
		} catch (EmergencyException e) {
			ViewUtil.showEmergentMessage(e.getTitle(), e.getMessage());
			System.exit(100);
		}
		
		try {
			logger = ATTRIBUTES.loggerGenerator.newInstance(resourceMap);
		} catch (EmergencyException e) {
			ViewUtil.showEmergentMessage(e.getTitle(), e.getMessage());
			System.exit(101);
		}
		
		logger.info("��¼���ɹ�����!");
		logger.info("���ڶ�ȡ��������");
		
		
		
	}
	
//	/**
//	 * ����һ������ָ�� TODO
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
