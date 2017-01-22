package com.dwarfeng.tp.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
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

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.control.ToolPlatform;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.struct.Logger;
import com.dwarfeng.tp.core.model.struct.LoggerInfo;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangInfo;
import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * ���ڹ���ƽ̨�Ĺ����ࡣ
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class ToolPlatformUtil {
	
	private final static Set<LoggerInfo> defaultLoggerInfos;
	private final static String mutilangLabel = "��������";
	private final static MutilangInfo defaultMutilangInfo = new InnerMutilangInfo(mutilangLabel, new HashMap<>());
	private final static String missingString = "!�ı�ȱʧ";
	private final static ResourceBundle loggerMutilangResourceBundle = ResourceBundle.getBundle(
			"com.dwarfeng.tp.resource.defaultres.mutilang.logger.default");
	private final static ResourceBundle labelMutilangResourceBundle = ResourceBundle.getBundle(
			"com.dwarfeng.tp.resource.defaultres.mutilang.label.default");
	private final static MutilangInfo defaultLoggerMutilangInfo;
	private final static MutilangInfo defaultLabelMutilangInfo;
	
	static{
		
		defaultLoggerInfos = new HashSet<>(Arrays.asList(new LoggerInfo[]{new InnerLoggerInfo("std.all")}));
		
		Map<String, String> loggerMutilangDefaultMap = new HashMap<>();
		for(Name name : LoggerStringKey.values()){
			try{
				loggerMutilangDefaultMap.put(name.getName(), loggerMutilangResourceBundle.getString(name.getName()));
			}catch (MissingResourceException e) {
				loggerMutilangDefaultMap.put(name.getName(), missingString);
			}
		}
		
		Map<String, String> labelMutilangDefaultMap = new HashMap<>();
		for(Name name : LabelStringKey.values()){
			try{
				labelMutilangDefaultMap.put(name.getName(), labelMutilangResourceBundle.getString(name.getName()));
			}catch (MissingResourceException e) {
				labelMutilangDefaultMap.put(name.getName(), missingString);
			}
		}
		
		defaultLoggerMutilangInfo = new InnerMutilangInfo(mutilangLabel, loggerMutilangDefaultMap);
		defaultLabelMutilangInfo = new InnerMutilangInfo(mutilangLabel, labelMutilangDefaultMap);
	}
	
	/**
	 * ��ȡһ���µ��赲�ֵ�ָʾ����������
	 * @return �µ��赲�ֵ�ָʾ����������
	 */
	public final InputStream newBlockDictionary(){
		return ToolPlatform.class.getResourceAsStream("/com/dwarfeng/tp/resource/block_dictionary.xml");
	}
	
	/**
	 * ��ȡĬ�ϵļ�¼�������ġ�
	 * @return Ĭ�ϵļ�¼�������ġ�
	 */
	public final static LoggerContext newDefaultLoggerContext(){
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
	public final static Set<LoggerInfo> getDefaultLoggerInfos(){
		return defaultLoggerInfos;
	}
	
	public final static MutilangInfo getDefaultMutilangInfo(){
		return defaultMutilangInfo;
	}
	
	/**
	 * ��ȡ�ı�ȱʧ�ֶΡ�
	 * @return �ı�ȱʧ�ֶΡ�
	 */
	public final static String getDefaultMissingString(){
		return missingString;
	}
	
	/**
	 * ��ȡĬ�ϵļ�¼����������Ϣ��
	 * @return Ĭ�ϵļ�¼����������Ϣ��
	 */
	public final static MutilangInfo getDefaultLoggerMutilangInfo(){
		return defaultLoggerMutilangInfo;
	}
	
	/**
	 * ��ȡĬ�ϵı�ǩ��������Ϣ��
	 * @return Ĭ�ϵı�ǩ��������Ϣ��
	 */
	public final static MutilangInfo getDefaultLabelMutilangInfo(){
		return defaultLoggerMutilangInfo;
	}
	
	/**
	 * ��ȡ��¼�������Խӿڵ�֧�ּ����ϡ�
	 * @return ��¼�������Խӿڵ�֧�ּ����ϡ�
	 */
	public static Set<String> getLoggerMutilangSupportedKeys() {
		try {
			return Collections.unmodifiableSet(defaultLoggerMutilangInfo.getMutilangMap().keySet());
		} catch (ProcessException ignore) {
			//�����׳��쳣
			return null;
		}
	}

	/**
	 * ��ȡ��ǩ�����Խӿڵ�֧�ּ����ϡ�
	 * @return ��ǩ�����Խӿڵ�֧�ּ����ϡ�
	 */
	public static Set<String> getLabelMutilangSupportedKeys() {
		try {
			return Collections.unmodifiableSet(defaultLabelMutilangInfo.getMutilangMap().keySet());
		} catch (ProcessException ignore) {
			//�����׳��쳣
			return null;
		}
	}
	
	/**
	 * ��ȡĬ�ϼ�¼���ӿڡ�
	 * <p> �ü�¼���������κβ�����
	 * @return �µĳ�ʼ����¼���ӿڡ�
	 */
	public final static Logger newDefaultLogger(){
		return new InitialLogger();
	}
	
	/**
	 * ��ȡĬ�ϵļ�¼�������Խӿڡ�
	 * <p> ʹ�ó��������õļ������ġ�
	 * @return �µ�Ĭ�ϼ�¼�������Խӿڡ�
	 */
	public final static Mutilang newDefaultLoggerMutilang(){
		return new DefaultLoggerMutilang();
	}
	
	/**
	 * ��ȡĬ�ϵı�ǩ�����Խӿڡ�
	 * <p> ʹ�ó��������õļ������ġ�
	 * @return �µ�Ĭ�ϱ�ǩ�����Խӿڡ�
	 */
	public final static Mutilang newDefaultLabelMutilang(){
		return new DefaultLabelMutilang();
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
	
	/**
	 * ��ʵ�����������һ��ָ���Ŀ����ж���
	 * <p> ��ָ���Ŀ����ж������н���֮ǰ����ǰ�߳̽���������״̬��
	 * @param runnable ָ���Ŀ����ж���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 * @throws InvocationTargetException <code>runnable</code>	����ʱ�׳��쳣��
	 * @throws InterruptedException ����ȴ��¼�ָ���߳�ִ���ִ�� <code>runnable.run()</code>ʱ���ж� 
	 */
	public final static void invokeAndWaitInEventQueue(Runnable runnable) throws InvocationTargetException, InterruptedException{
		Objects.requireNonNull(runnable, "��ڲ��� runnable ����Ϊ null��");
		
		if(SwingUtilities.isEventDispatchThread()){
			runnable.run();
		}else{
			SwingUtilities.invokeAndWait(runnable);
		}
	}
	
	
	

	/**
	 * Ĭ�ϼ�¼�������Խӿڡ�
	 * <p> ʹ�ó��������õļ������ġ�
	 * @author  DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class DefaultLoggerMutilang implements Mutilang {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(java.lang.String)
		 */
		@Override
		public String getString(String key) {
			try {
				if(! defaultLoggerMutilangInfo.getMutilangMap().containsKey(key)){
					throw new IllegalArgumentException("�˶����Խӿڲ�֧�ָü�");
				}
			} catch (ProcessException ignore) {
				//�����׳��쳣
			}
			try{
				return loggerMutilangResourceBundle.getString(key);
			}catch (MissingResourceException e) {
				return missingString;
			}
		}
		
	}
	
	/**
	 * Ĭ�ϼ�¼�������Խӿڡ�
	 * <p> ʹ�ó��������õļ������ġ�
	 * @author DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class DefaultLabelMutilang implements Mutilang{

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(java.lang.String)
		 */
		@Override
		public String getString(String key) {
			try {
				if(! defaultLabelMutilangInfo.getMutilangMap().containsKey(key)){
					throw new IllegalArgumentException("�˶����Խӿڲ�֧�ָü�");
				}
			} catch (ProcessException ignore) {
				//�����׳��쳣
			}
			try{
				return labelMutilangResourceBundle.getString(key);
			}catch (MissingResourceException e) {
				return missingString;
			}
		}
		
	}
	
	

	/**
	 * Ĭ�ϼ�¼���ӿڡ�
	 * <p> �ü�¼���������κβ�����
	 * @author  DwArFeng
	 * @since 0.0.0-alpha
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

	/**
	 * �ڲ���������Ϣ��
	 * <p> ��������Ϣ���ڲ�ʵ�֡�
	 * @author  DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class InnerMutilangInfo implements MutilangInfo {
		
		private final String label;
		private final Map<String, String> mutilangMap;
		
		public InnerMutilangInfo(String label, Map<String, String> mutilangMap) {
			Objects.requireNonNull(label, "��ڲ��� label ����Ϊ null��");
			Objects.requireNonNull(mutilangMap, "��ڲ��� mutilangMap ����Ϊ null��");
			
			this.label = label;
			this.mutilangMap = mutilangMap;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.MutilangInfo#getLabel()
		 */
		@Override
		public String getLabel() {
			return this.label;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.MutilangAttribute#getMutilangMap()
		 */
		@Override
		public Map<String, String> getMutilangMap() {
			return Collections.unmodifiableMap(mutilangMap);
		}

	}
	
	/**
	 * �ڲ���¼����Ϣ��
	 * <p> ��¼����Ϣ���ڲ�ʵ�֡�
	 * @author DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class InnerLoggerInfo implements LoggerInfo{

		private final String name;
		
		public InnerLoggerInfo(String name) {
			Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
			this.name = name;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.LoggerInfo#getName()
		 */
		@Override
		public String getName() {
			return name;
		}
		
	}


	//��ֹ�ⲿʵ����
	private ToolPlatformUtil(){}

}
