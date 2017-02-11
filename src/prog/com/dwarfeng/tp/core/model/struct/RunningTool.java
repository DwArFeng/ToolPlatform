package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.prog.RuntimeState;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.obv.RunningToolObverser;

/**
 * 运行中工具。
 * <p> 该类表示一个正在运行的工具，提供有关的方法。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface RunningTool extends Name, ObverserSet<RunningToolObverser>{
	
	/**
	 * 返回工具中的图片。
	 * @return 工具中的图片。
	 */
	public Image getImage();
	
	/**
	 * 获取用于转接工具输出流的输入流。
	 * @return 用于转接工具输出流的输入流。
	 */
	public InputStream getInputStream();
	
	/**
	 * 设置用于转接工具输出流的输入流。
	 * <p> 运行中工具在运行的时候，会不断的将这个流中的数据输出到工具的输出流中，该过程是并发的。
	 * <p> 该输出流必须是阻塞式的，否则会系统资源造成巨大的负担。
	 * <p> 入口参数允许为 <code>null</code>，代表该运行中工具将抛弃工具的输出流中的所有数据。
	 * @param in 指定的输出流。
	 * @return 是否设置成功
	 */
	public boolean setInputStream(InputStream in);
	
	/**
	 * 获取用于转接工具输入流的输出流。
	 * @return 用于转接工具输入流的输出流。
	 */
	public OutputStream getOutputStream();
	
	/**
	 * 设置用于转接工具输入流的输出流。
	 * <p> 运行中工具在运行的时候，会不断的将工具的输出流的数据发送到该输出流中，该过程是并发的。
	 * <p> 入口参数允许为 <code>null</code>，代表该运行中工具将抛弃工具的输入流中的所有数据。
	 * @param out 指定的输出流。
	 * @return 是否设置成功
	 */
	public boolean setOutputStream(PrintStream out);
	
	/**
	 * 锁定流。
	 * <p> 该方法将会锁定该运行中工具的输入流与输出流，调用该方法之后，再次调用
	 * {@link RunningTool#setInputStream(InputStream)} 或 {@link RunningTool#setOutputStream(OutputStream)} 将返回 <code>false</code>。
	 * <p> 只有当流被锁定之后，工具才会开始运行。
	 */
	public void lockStream();
	
	/**
	 * 启动该运行中工具。
	 * <p> 该方法在运行 {@link RunningTool#lockStream()} 之前，会造成调用线程的阻塞，
	 * 在阻塞的过程中，如果线程被中断，并不会启动工具。
	 * @throws InterruptedException 调用线程在阻塞时被中断。
	 * @throws IllegalStateException 在程序已经启动或结束后再次调用该方法。
	 */
	public void start() throws InterruptedException;
	
	/**
	 * 强制中止该运行中工具。
	 * <p> 调用该方法后，如果该工具还未启动，则工具永远不会启动，返回的退出值是 <code>-12450</code>。
	 * <br> 调用该方法后，如果该工具正在等待启动，则结束等待（不会抛出 {@link InterruptedException}）并且结束，工具永远不会启动，
	 * 返回的退出值是 <code>-12451</code>。
	 */
	public void destroy();
	
	/**
	 * 获取该工具的运行时状态。
	 * @return 该工具的运行时状态。
	 */
	public RuntimeState getRuntimeState();
	
	/**
	 * 获取该运行中工具的工作目录。
	 * @return 该运行中工具的工作目录。
	 */
	public File getDirectory();
	
	/**
	 * 返回该运行中工具的退出代码。
	 * <p> 对于某些实现，即使工具处于运行状态下，调用该方法仍然能够返回值——但是这样做毫无意义。
	 * @return 该工具的退出代码。
	 */
	public int getExitCode();
	
	/**
	 * 获取工具开始运行的日期。
	 * @return 工具开始运行的日期。
	 */
	public Date getRanDate();
	
	/**
	 * 获取工具结束运行的日期。
	 * @return 工具结束运行的日期。
	 */
	public Date getExitedDate();
	
}
