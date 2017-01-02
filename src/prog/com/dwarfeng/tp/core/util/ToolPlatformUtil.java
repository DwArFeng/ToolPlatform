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
	 * 获取初始化用多语言接口。
	 * <p> 该多语言接口是程序在初始化阶段，尚未通过配置生成专用的多语言接口提供器之前，
	 * 用于代替的多语言接口，该方法生成的多语言接口只会在程序在初始化的前期保留一段时间。
	 * <p> 使用简体中文，并且不响应设置语言方法和将语言设置为默认值方法。
	 * @return 新的初始化用多语言接口。
	 */
	public final static Mutilang newInitialLoggerMutilang(){
		return new InitialLoggerMutilang();
	}
	
	/**
	 * 通过指定的多语言模型生成一个新的记录器多语言提供器。
	 * @param mutilangModel 指定的多语言模型。
	 * @return 通过指定的多语言模型生成的记录器多语言提供器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public final static MutilangProvider newLoggerMutilangProvider(MutilangModel mutilangModel){
		Objects.requireNonNull(mutilangModel, "入口参数 mutilangModel 不能为 null。");
		
		return new DefaultMutilangProvider(
				mutilangModel, 
				new HashSet<>(Arrays.asList(LoggerStringKey.values())), 
				ResourceBundleUtil.toMap(loggerMutilangResourceBundle),
				missingString);
	}
	
	/**
	 * 通过指定的多语言模型生成一个新的标签多语言提供器。
	 * @param mutilangModel mutilangModel 指定的多语言模型。
	 * @return 通过指定的多语言模型生成的比起阿多语言提供器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public final static MutilangProvider newLabelMutilangProvider(MutilangModel mutilangModel){
		Objects.requireNonNull(mutilangModel, "入口参数 mutilangModel 不能为 null。");

		return new DefaultMutilangProvider(
				mutilangModel, 
				new HashSet<>(Arrays.asList(LabelStringKey.values())), 
				ResourceBundleUtil.toMap(labelMutilangResourceBundle),
				missingString);
	}
	

	/**
	 * 初始化多语言接口。
	 * <p> 该多语言接口是程序在初始化阶段，尚未通过配置生成专用的多语言接口提供器之前，
	 * 用于代替的多语言接口，该方法生成的多语言接口只会在程序在初始化的前期保留一段时间。
	 * <p> 使用简体中文，并且不响应设置语言方法和将语言设置为默认值方法。
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
				throw new IllegalArgumentException("此多语言接口不支持该键");
			}
			return loggerMutilangResourceBundle.getString(key.getName());
		}
		
	}


	//禁止外部实例化
	private ToolPlatformUtil(){}

}
