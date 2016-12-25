package com.dwarfeng.tp.model.init;

import java.net.URL;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.model.io.ToolPlatformLogger;

/**
 * 平台用记录器生成器。
 * <p> 用于生成平台记录器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface ToolPlatformLoggerGenerator {
	
	/**
	 * 生成一个由指定配置确定的新的平台用记录器。
	 * @param path 指定的记录器路径配置所在的路径。
	 * @param forceOverride 是否强行覆盖现有的配置。
	 * @return 新的平台用记录器。
	 * @throws NullPointerException 入口参数为 null。
	 * @throws LoadFailedException 读取失败异常。
	 */
	public ToolPlatformLogger genToolPlatformLogger(URL path, boolean forceOverride) throws LoadFailedException;

}
