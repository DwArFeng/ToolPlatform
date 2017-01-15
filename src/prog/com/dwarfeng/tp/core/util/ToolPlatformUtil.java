package com.dwarfeng.tp.core.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.control.ToolPlatform;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.cm.LoggerModel;
import com.dwarfeng.tp.core.model.struct.Logger;
import com.dwarfeng.tp.core.model.struct.Mutilang;

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
	 * ��ȡĬ�ϵļ�¼�������ġ�
	 * @return Ĭ�ϵļ�¼�������ġ�
	 */
	public final static LoggerContext getDefaultLoggerContext(){
		try {
			ConfigurationSource cs = new ConfigurationSource(ToolPlatform.class.getResourceAsStream("/com/dwarfeng/tp/resource/defaultres/logger/setting.xml"));
			return Configurator.initialize(null, cs);
		} catch (IOException e) {
			e.printStackTrace();
			return (LoggerContext) LogManager.getContext();
		}
		
	}
	
	/**
	 * ��ȡĬ�ϵļ�¼�����Ƽ��ϡ�
	 * @return Ĭ�ϵļ�¼�����Ƽ��ϡ�
	 */
	public final static Set<String> getDefaultLoggerNames(){
		return new HashSet<>(Arrays.asList(new String[]{"std.all"}));
	}
	
	/**
	 * ��ȡ�ı�ȱʧ�ֶΡ�
	 * @return �ı�ȱʧ�ֶΡ�
	 */
	public final static String getMissingString(){
		return missingString;
	}
	
	/**
	 * ����Ĭ�ϵļ�¼�������Խӿڵ�Ĭ�ϼ�ֵӳ�䡣
	 * @return Ĭ�ϼ�ֵӳ�䡣
	 */
	public final static Map<Name, String> getLoggerMutilangDefaultMap(){
		Map<Name, String> map = new HashMap<>();
		for(Name name : LoggerStringKey.values()){
			try{
				map.put(name, loggerMutilangResourceBundle.getString(name.getName()));
			}catch (MissingResourceException e) {
				map.put(name, missingString);
			}
		}
		return map;
	}
	
	/**
	 * ����Ĭ�ϵı�ǩ�����Խӿڵ�Ĭ�ϼ�ֵӳ�䡣
	 * @return Ĭ�ϼ�ֵӳ�䡣
	 */
	public final static Map<Name, String> getLabelMutilangDefaultMap(){
		Map<Name, String> map = new HashMap<>();
		for(Name name : LabelStringKey.values()){
			try{
				map.put(name, labelMutilangResourceBundle.getString(name.getName()));
			}catch (MissingResourceException e) {
				map.put(name, missingString);
			}
		}
		return map;
	}
	
	/**
	 * ��ȡ��ʼ����¼���ӿڡ�
	 * <p> �ü�¼���ǳ����ڳ�ʼ���׶Σ���δͨ����������ר�õļ�¼���ṩ��֮ǰ��
	 * ���ڴ���ļ�¼�����÷������ɵļ�¼��ֻ���ڳ����ڳ�ʼ����ǰ�ڱ���һ��ʱ�䡣
	 * <p> �ü�¼���������κβ�����
	 * @return �µĳ�ʼ����¼���ӿڡ�
	 */
	public final static Logger newInitialLogger(){
		return new InitialLogger();
	}
	
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
	 * ���¼����������һ��ָ���Ŀ����ж���
	 * @param runnable ָ���Ŀ����ж���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public final static void invokeInEventQueue(Runnable runnable){
		Objects.requireNonNull(runnable, "��ڲ��� runnable ����Ϊ null��");
		
		if(SwingUtilities.isEventDispatchThread()){
			runnable.run();
		}else{
			SwingUtilities.invokeLater(runnable);
		}
	}
	
//	/**
//	 * ͨ��ָ���Ķ�����ģ������һ���µļ�¼���������ṩ����
//	 * @param mutilangModel ָ���Ķ�����ģ�͡�
//	 * @return ͨ��ָ���Ķ�����ģ�����ɵļ�¼���������ṩ����
//	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
//	 */
//	public final static MutilangProvider newLoggerMutilangProvider(MutilangModel mutilangModel){
//		Objects.requireNonNull(mutilangModel, "��ڲ��� mutilangModel ����Ϊ null��");
//		
//		return new DefaultMutilangProvider(
//				mutilangModel, 
//				new HashSet<>(Arrays.asList(LoggerStringKey.values())), 
//				ResourceBundleUtil.toMap(loggerMutilangResourceBundle),
//				missingString);
//	}
//	
//	/**
//	 * ͨ��ָ���Ķ�����ģ������һ���µı�ǩ�������ṩ����
//	 * @param mutilangModel mutilangModel ָ���Ķ�����ģ�͡�
//	 * @return ͨ��ָ���Ķ�����ģ�����ɵı��𰢶������ṩ����
//	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
//	 */
//	public final static MutilangProvider newLabelMutilangProvider(MutilangModel mutilangModel){
//		Objects.requireNonNull(mutilangModel, "��ڲ��� mutilangModel ����Ϊ null��");
//
//		return new DefaultMutilangProvider(
//				mutilangModel, 
//				new HashSet<>(Arrays.asList(LabelStringKey.values())), 
//				ResourceBundleUtil.toMap(labelMutilangResourceBundle),
//				missingString);
//	}
	

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

	/**
	 * ��ʼ����¼���ӿڡ�
	 * <p> �ü�¼���ǳ����ڳ�ʼ���׶Σ���δͨ����������ר�õļ�¼���ṩ��֮ǰ��
	 * ���ڴ���ļ�¼�����÷������ɵļ�¼��ֻ���ڳ����ڳ�ʼ����ǰ�ڱ���һ��ʱ�䡣
	 * <p> �ü�¼���������κβ�����
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private static final class InitialLogger implements Logger{

		@Override
		public void trace(String message) {}
		@Override
		public void debug(String message) {}
		@Override
		public void info(String message) {}
		@Override
		public void warn(String message) {}
		@Override
		public void warn(String message, Throwable t) {}
		@Override
		public void error(String message, Throwable t) {}
		@Override
		public void fatal(String message, Throwable t) {}
		
	}

	//��ֹ�ⲿʵ����
	private ToolPlatformUtil(){}

}
