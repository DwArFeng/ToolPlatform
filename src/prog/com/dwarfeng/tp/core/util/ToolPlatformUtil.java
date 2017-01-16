package com.dwarfeng.tp.core.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.control.ToolPlatform;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.struct.Logger;
import com.dwarfeng.tp.core.model.struct.Mutilang;

/**
 * 关于工具平台的工厂类。
 * @author  DwArFeng
 * @since 1.8
 */
public final class ToolPlatformUtil {
	
	private final static String missingString = "!文本缺失";
	private final static ResourceBundle loggerMutilangResourceBundle = ResourceBundle.getBundle(
			"com.dwarfeng.tp.resource.defaultres.mutilang.logger.default");
	private final static ResourceBundle labelMutilangResourceBundle = ResourceBundle.getBundle(
			"com.dwarfeng.tp.resource.defaultres.mutilang.label.default");
	
	/**
	 * 获取默认的记录器上下文。
	 * @return 默认的记录器上下文。
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
	 * 获取默认的记录器名称集合。
	 * @return 默认的记录器名称集合。
	 */
	public final static Set<String> getDefaultLoggerNames(){
		return new HashSet<>(Arrays.asList(new String[]{"std.all"}));
	}
	
	/**
	 * 获取文本缺失字段。
	 * @return 文本缺失字段。
	 */
	public final static String getMissingString(){
		return missingString;
	}
	
	/**
	 * 返回默认的记录器多语言接口的默认键值映射。
	 * @return 默认键值映射。
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
	 * 返回默认的标签多语言接口的默认键值映射。
	 * @return 默认键值映射。
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
	 * @since 1.8
	 */
	private static final class DefaultLoggerMutilang implements Mutilang {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
		 */
		@Override
		public String getString(Name key) {
			if(!(key instanceof LoggerStringKey)){
				throw new IllegalArgumentException("此多语言接口不支持该键");
			}
			try{
				return loggerMutilangResourceBundle.getString(key.getName());
			}catch (MissingResourceException e) {
				return missingString;
			}
		}
		
	}
	
	/**
	 * 默认记录器多语言接口。
	 * <p> 使用程序中内置的简体中文。
	 * @author DwArFeng
	 * @since 1.8
	 */
	private static final class DefaultLabelMutilang implements Mutilang{

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
		 */
		@Override
		public String getString(Name key) {
			if(!(key instanceof LabelStringKey)){
				throw new IllegalArgumentException("此多语言接口不支持该键");
			}
			try{
				return labelMutilangResourceBundle.getString(key.getName());
			}catch (MissingResourceException e) {
				return missingString;
			}
		}
		
	}
	
	

	/**
	 * 默认记录器接口。
	 * <p> 该记录器不进行任何操作。
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

	//禁止外部实例化
	private ToolPlatformUtil(){}

}
