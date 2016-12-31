package com.dwarfeng.tp.core.control;

import java.util.Objects;

import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.tp.core.control.proc.Initializer;
import com.dwarfeng.tp.core.model.ModelManager;
import com.dwarfeng.tp.core.model.struct.InitializeFailedException;
import com.dwarfeng.tp.core.view.ViewManager;
import com.dwarfeng.tp.core.view.ViewUtil;

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
	
	/**
	 * �����е����Լ��ϡ�
	 * <p> �����Լ����ṩ�����е�һЩ�������ԣ������������ơ���������ߡ�����İ汾�ȵȡ�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Attributes{
		
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
		public final static String author = "DwArFeng";
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**���������õ�ģ�͹�������*/
	private final ModelManager modelManager;
	private final ViewManager viewManager;
	
	/**
	 * ����һ��Ĭ�ϵĹ���ƽ̨ʵ����
	 * ����һ������ָ�� TODO
	 */
	public ToolPlatform() {
		this(ToolPlatformHelper.newDefaultInitializer());
	}
	
	/**
	 * 
	 * @param preLogger
	 * @param preMutilang
	 */
	public ToolPlatform(Initializer initializer){
		Objects.requireNonNull(initializer, "��ڲ��� initializer ����Ϊ null��");
		
		try {
			initializer.init();
		} catch (InitializeFailedException e) {
			ViewUtil.showEmergentMessage(e.getDialogTitle(), e.getDialogMessage());
			System.exit(100);
		}
		
		this.modelManager = initializer.getModelManager();
		this.viewManager = initializer.getViewManager();
		
		
	}
	
}
