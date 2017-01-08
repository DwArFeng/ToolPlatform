package com.dwarfeng.tp.core.model.cm;

import java.util.Set;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.obv.LoggerObverser;
import com.dwarfeng.tp.core.model.struct.ReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.struct.Updateable;

/**
 * 有关记录器的配置模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerModel extends ObverserSet<LoggerObverser>, ReadWriteThreadSafe, Updateable{
	
	/**
	 * 获取记录器上下文。
	 * @return 记录器上下文。
	 */
	public LoggerContext getLoggerContext();
	
	/**
	 * 设置记录器上下文为指定上下文。
	 * @param loggerContext 记录器上下文。
	 * @return 该操作是否对模型造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setLoggerContext(LoggerContext loggerContext);
	
	/**
	 * 获取模型中的记录器名称集合。
	 * <p> 获得的集合是只读的，尝试调用其中的编辑方法会抛出 {@link UnsupportedOperationException}。
	 * @return 记录器名称集合。
	 */
	public Set<String> getLoggerNames();
	
	/**
	 * 设置模型中的记录器的名称集合。
	 * @param loggerNames 指定的名称集合。
	 * @return 该操作是否对模型造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setLoggerNames(Set<String> loggerNames);
	
	/**
	 * 获取模型中的Logger集合。
	 * <p>该集合是只读的，尝试调用其编辑方法会抛出 {@link UnsupportedOperationException}。
	 * @return 该模型中的Logger集合。
	 */
	public Set<Logger> getLoggers();
		
}
