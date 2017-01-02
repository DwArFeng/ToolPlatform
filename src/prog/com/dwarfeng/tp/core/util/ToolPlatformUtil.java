package com.dwarfeng.tp.core.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.struct.DefaultMutilangProvider;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangProvider;
import com.dwarfeng.tp.core.model.vim.MutilangModel;

/**
 * ���ڹ���ƽ̨�Ĺ����ࡣ
 * @author  DwArFeng
 * @since 1.8
 */
public final class ToolPlatformUtil {
	
	private final static String missingString = "!�ı�ȱʧ";
	private final static ResourceBundle loggerMutilangResourceBundle = ResourceBundle.getBundle(
			"com.dwarfeng.tp.resource.defaultres.mutilang.logger.default");
	private final static ResourceBundle labelMutilangResourceBundle = ResourceBundle.getBundle(
			"com.dwarfeng.tp.resource.defaultres.mutilang.label.default");

	
	/**
	 * ��ȡ��ʼ���ö����Խӿڡ�
	 * <p> �ö����Խӿ��ǳ����ڳ�ʼ���׶Σ���δͨ����������ר�õĶ����Խӿ��ṩ��֮ǰ��
	 * ���ڴ���Ķ����Խӿڣ��÷������ɵĶ����Խӿ�ֻ���ڳ����ڳ�ʼ����ǰ�ڱ���һ��ʱ�䡣
	 * <p> ʹ�ü������ģ����Ҳ���Ӧ�������Է����ͽ���������ΪĬ��ֵ������
	 * @return �µĳ�ʼ���ö����Խӿڡ�
	 */
	public final static Mutilang newInitialLoggerMutilang(){
		return new InitialLoggerMutilang();
	}
	
	/**
	 * ͨ��ָ���Ķ�����ģ������һ���µļ�¼���������ṩ����
	 * @param mutilangModel ָ���Ķ�����ģ�͡�
	 * @return ͨ��ָ���Ķ�����ģ�����ɵļ�¼���������ṩ����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public final static MutilangProvider newLoggerMutilangProvider(MutilangModel mutilangModel){
		Objects.requireNonNull(mutilangModel, "��ڲ��� mutilangModel ����Ϊ null��");
		
		return new DefaultMutilangProvider(
				mutilangModel, 
				new HashSet<>(Arrays.asList(LoggerStringKey.values())), 
				ResourceBundleUtil.toMap(loggerMutilangResourceBundle),
				missingString);
	}
	
	/**
	 * ͨ��ָ���Ķ�����ģ������һ���µı�ǩ�������ṩ����
	 * @param mutilangModel mutilangModel ָ���Ķ�����ģ�͡�
	 * @return ͨ��ָ���Ķ�����ģ�����ɵı��𰢶������ṩ����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public final static MutilangProvider newLabelMutilangProvider(MutilangModel mutilangModel){
		Objects.requireNonNull(mutilangModel, "��ڲ��� mutilangModel ����Ϊ null��");

		return new DefaultMutilangProvider(
				mutilangModel, 
				new HashSet<>(Arrays.asList(LabelStringKey.values())), 
				ResourceBundleUtil.toMap(labelMutilangResourceBundle),
				missingString);
	}
	

	/**
	 * ��ʼ�������Խӿڡ�
	 * <p> �ö����Խӿ��ǳ����ڳ�ʼ���׶Σ���δͨ����������ר�õĶ����Խӿ��ṩ��֮ǰ��
	 * ���ڴ���Ķ����Խӿڣ��÷������ɵĶ����Խӿ�ֻ���ڳ����ڳ�ʼ����ǰ�ڱ���һ��ʱ�䡣
	 * <p> ʹ�ü������ģ����Ҳ���Ӧ�������Է����ͽ���������ΪĬ��ֵ������
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private static final class InitialLoggerMutilang implements Mutilang {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
		 */
		@Override
		public String getString(Name key) {
			if(!(key instanceof LoggerStringKey)){
				throw new IllegalArgumentException("�˶����Խӿڲ�֧�ָü�");
			}
			return loggerMutilangResourceBundle.getString(key.getName());
		}
		
	}


	//��ֹ�ⲿʵ����
	private ToolPlatformUtil(){}

}
