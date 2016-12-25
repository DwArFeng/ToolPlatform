package com.dwarfeng.tp.model.io;

/**
 * 工具平台用记录器。
 * <p> 用来处理与记录有关的方法。
 * @author  DwArFeng
 * @since 1.8
 */
public interface ToolPlatformLogger {

	/**
	 * 调用记录站点的trace方法。
	 * @param message 指定的信息。
	 */
	public void trace(String message);
	
	/**
	 * 调用记录站点的debug方法。
	 * @param message 指定的信息。
	 */
	public void debug(String message);
	
	/**
	 * 调用记录站点的info方法。
	 * @param message 指定的信息。
	 */
	public void info(String message);
	
	/**
	 * 调用记录站点的warn方法。
	 * @param message 指定的信息。
	 */
	public void warn(String message);
	
	/**
	 * 调用记录站点的warn方法。
	 * @param message 指定的信息。
	 * @param t 指定的可抛出对象，一般是线程或异常的跟踪堆栈。
	 */
	public void warn(String message, Throwable t);
	
	/**
	 * 调用记录站点的error方法。
	 * @param message 指定的信息。
	 * @param t 指定的可抛出对象，一般是线程或异常的跟踪堆栈。
	 */
	public void error(String message, Throwable t);
	
	/**
	 * 调用记录站点的fatal方法。
	 * @param message 指定的信息。
	 * @param t 指定的可抛出对象，一般是线程或异常的跟踪堆栈。
	 */
	public void fatal(String message, Throwable t);
	
}
