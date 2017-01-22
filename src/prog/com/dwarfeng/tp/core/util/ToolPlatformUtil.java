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
 * 关于工具平台的工厂类。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class ToolPlatformUtil {
	
	private final static Set<LoggerInfo> defaultLoggerInfos;
	private final static String mutilangLabel = "简体中文";
	private final static MutilangInfo defaultMutilangInfo = new InnerMutilangInfo(mutilangLabel, new HashMap<>());
	private final static String missingString = "!文本缺失";
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
	 * 获取一个新的阻挡字典指示的输入流。
	 * @return 新的阻挡字典指示的输入流。
	 */
	public final InputStream newBlockDictionary(){
		return ToolPlatform.class.getResourceAsStream("/com/dwarfeng/tp/resource/block_dictionary.xml");
	}
	
	/**
	 * 获取默认的记录器上下文。
	 * @return 默认的记录器上下文。
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
	 * 获取默认的记录器名称集合。
	 * @return 默认的记录器名称集合。
	 */
	public final static Set<LoggerInfo> getDefaultLoggerInfos(){
		return defaultLoggerInfos;
	}
	
	public final static MutilangInfo getDefaultMutilangInfo(){
		return defaultMutilangInfo;
	}
	
	/**
	 * 获取文本缺失字段。
	 * @return 文本缺失字段。
	 */
	public final static String getDefaultMissingString(){
		return missingString;
	}
	
	/**
	 * 获取默认的记录器多语言信息。
	 * @return 默认的记录器多语言信息。
	 */
	public final static MutilangInfo getDefaultLoggerMutilangInfo(){
		return defaultLoggerMutilangInfo;
	}
	
	/**
	 * 获取默认的标签多语言信息。
	 * @return 默认的标签多语言信息。
	 */
	public final static MutilangInfo getDefaultLabelMutilangInfo(){
		return defaultLoggerMutilangInfo;
	}
	
	/**
	 * 获取记录器多语言接口的支持键集合。
	 * @return 记录器多语言接口的支持键集合。
	 */
	public static Set<String> getLoggerMutilangSupportedKeys() {
		try {
			return Collections.unmodifiableSet(defaultLoggerMutilangInfo.getMutilangMap().keySet());
		} catch (ProcessException ignore) {
			//不会抛出异常
			return null;
		}
	}

	/**
	 * 获取标签多语言接口的支持键集合。
	 * @return 标签多语言接口的支持键集合。
	 */
	public static Set<String> getLabelMutilangSupportedKeys() {
		try {
			return Collections.unmodifiableSet(defaultLabelMutilangInfo.getMutilangMap().keySet());
		} catch (ProcessException ignore) {
			//不会抛出异常
			return null;
		}
	}
	
	/**
	 * 获取默认记录器接口。
	 * <p> 该记录器不进行任何操作。
	 * @return 新的初始化记录器接口。
	 */
	public final static Logger newDefaultLogger(){
		return new InitialLogger();
	}
	
	/**
	 * 获取默认的记录器多语言接口。
	 * <p> 使用程序中内置的简体中文。
	 * @return 新的默认记录器多语言接口。
	 */
	public final static Mutilang newDefaultLoggerMutilang(){
		return new DefaultLoggerMutilang();
	}
	
	/**
	 * 获取默认的标签多语言接口。
	 * <p> 使用程序中内置的简体中文。
	 * @return 新的默认标签多语言接口。
	 */
	public final static Mutilang newDefaultLabelMutilang(){
		return new DefaultLabelMutilang();
	}
	
	/**
	 * 向事件队列中添加一个指定的可运行对象。
	 * @param runnable 指定的可运行对象。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public final static void invokeInEventQueue(Runnable runnable){
		Objects.requireNonNull(runnable, "入口参数 runnable 不能为 null。");
		
		if(SwingUtilities.isEventDispatchThread()){
			runnable.run();
		}else{
			SwingUtilities.invokeLater(runnable);
		}
	}
	
	/**
	 * 向实践队列中添加一个指定的可运行对象。
	 * <p> 在指定的可运行对象运行结束之前，当前线程将处于阻塞状态。
	 * @param runnable 指定的可运行对象。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 * @throws InvocationTargetException <code>runnable</code>	运行时抛出异常。
	 * @throws InterruptedException 如果等待事件指派线程执完成执行 <code>runnable.run()</code>时被中断 
	 */
	public final static void invokeAndWaitInEventQueue(Runnable runnable) throws InvocationTargetException, InterruptedException{
		Objects.requireNonNull(runnable, "入口参数 runnable 不能为 null。");
		
		if(SwingUtilities.isEventDispatchThread()){
			runnable.run();
		}else{
			SwingUtilities.invokeAndWait(runnable);
		}
	}
	
	
	

	/**
	 * 默认记录器多语言接口。
	 * <p> 使用程序中内置的简体中文。
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
					throw new IllegalArgumentException("此多语言接口不支持该键");
				}
			} catch (ProcessException ignore) {
				//不会抛出异常
			}
			try{
				return loggerMutilangResourceBundle.getString(key);
			}catch (MissingResourceException e) {
				return missingString;
			}
		}
		
	}
	
	/**
	 * 默认记录器多语言接口。
	 * <p> 使用程序中内置的简体中文。
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
					throw new IllegalArgumentException("此多语言接口不支持该键");
				}
			} catch (ProcessException ignore) {
				//不会抛出异常
			}
			try{
				return labelMutilangResourceBundle.getString(key);
			}catch (MissingResourceException e) {
				return missingString;
			}
		}
		
	}
	
	

	/**
	 * 默认记录器接口。
	 * <p> 该记录器不进行任何操作。
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
	 * 内部多语言信息。
	 * <p> 多语言信息的内部实现。
	 * @author  DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class InnerMutilangInfo implements MutilangInfo {
		
		private final String label;
		private final Map<String, String> mutilangMap;
		
		public InnerMutilangInfo(String label, Map<String, String> mutilangMap) {
			Objects.requireNonNull(label, "入口参数 label 不能为 null。");
			Objects.requireNonNull(mutilangMap, "入口参数 mutilangMap 不能为 null。");
			
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
	 * 内部记录器信息。
	 * <p> 记录器信息的内部实现。
	 * @author DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class InnerLoggerInfo implements LoggerInfo{

		private final String name;
		
		public InnerLoggerInfo(String name) {
			Objects.requireNonNull(name, "入口参数 name 不能为 null。");
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


	//禁止外部实例化
	private ToolPlatformUtil(){}

}
