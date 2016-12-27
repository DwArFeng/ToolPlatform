package com.dwarfeng.tp.control;

import java.util.Map;
import java.util.Objects;

import com.dwarfeng.tp.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.model.cfg.Mutilang;
import com.dwarfeng.tp.model.struct.EmergencyException;
import com.dwarfeng.tp.model.struct.ProgramLogger;
import com.dwarfeng.tp.model.struct.ProgramResource;
import com.dwarfeng.tp.view.ViewUtil;

import sun.util.logging.resources.logging;

/**
 * ToolPlatform��DwArFeng �Ĺ���ƽ̨����
 * <p> �ù���ƽ̨���������� DwArFeng ��д���ڶ�Ĺ��ߵġ�
 * �ù���ƽ̨���÷����ȡ�乤��Ŀ¼�µ����й��ߣ�����ӵ�н���Щ���߽��зֱ�ǩ��������������Ĺ��ܡ�
 * <p> TODO ��Ҫ������ϸ��������
 * @author  DwArFeng
 * @since 1.8
 */
public final class ToolPlatform {
	
	public static void main(String[] args) {
		new ToolPlatform();
	}
	
	/**����Ĺ̶�����*/
	public static final ProgramAttributes ATTRIBUTES = new ProgramAttributes();
	
	
//	private final ModelManager modelManager;
//	private final ViewManager viewManager;
	
	/**
	 * ����һ��Ĭ�ϵĹ���ƽ̨ʵ����
	 * ����һ������ָ�� TODO
	 */
	public ToolPlatform() {
		this(ATTRIBUTES.defaultProgramLogger, ATTRIBUTES.defaultLoggerMutilang);
	}
	
	/**
	 * 
	 * @param preLogger
	 * @param preLoggerMutilang
	 */
	public ToolPlatform(ProgramLogger preLogger, Mutilang<LoggerStringKey> preLoggerMutilang){
		Objects.requireNonNull(preLogger, "��ڲ��� preLogger ����Ϊ null��");
		Objects.requireNonNull(preLoggerMutilang, "��ڲ��� preLoggerMutilang ����Ϊ null��");

		try{
			Map<String, ProgramResource> resourceMap = null;
			ProgramLogger logger = null;
			
			try {
				resourceMap = ATTRIBUTES.resourceLoader.loadResources();
			} catch (EmergencyException e) {
				ViewUtil.showEmergentMessage(e.getTitle(), e.getMessage());
				System.exit(100);
			}
			
			try {
				logger = ATTRIBUTES.loggerGenerator.newInstance(resourceMap, preLogger, preLoggerMutilang);
			} catch (EmergencyException e) {
				ViewUtil.showEmergentMessage(e.getTitle(), e.getMessage());
				System.exit(101);
			}
			
			
			
			
			logger.info("���ڶ�ȡ��������");
			
		}finally {
			preLogger.stop();
		}
	}
	
}
