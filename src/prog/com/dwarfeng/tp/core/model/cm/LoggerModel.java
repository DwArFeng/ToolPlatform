package com.dwarfeng.tp.core.model.cm;

import java.util.Set;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.obv.LoggerObverser;
import com.dwarfeng.tp.core.model.struct.ReadWriteThreadSafe;

/**
 * 有关记录器的配置模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author  DwArFeng
 * @since 1.8
 */
public interface LoggerModel extends Set<Logger>, ObverserSet<LoggerObverser>, ReadWriteThreadSafe{

	/**
	 * 获取记录器模型中在记录的记录器上下文。
	 * <p> 该集合包含模型中所有记录器在记录的记录器上下文，有可能一个记录器上下文对应着多个记录器，
	 * <p> 该集合是不可更改的，试图调用其中的编辑方法会抛出 {@link UnsupportedOperationException}。
	 * <p> 该集合中的记录器只可作为查看使用，不可对其中的记录器调用修改方法，否则会导致模型以不可预料的方式工作。
	 * @return 模型中所有记录器在记录的上下文。
	 */
	public Set<LoggerContext> getLoggerContexts();
	
	/**
	 * 停止记录器上下文，并移除模型中与该上下文有关的所有记录器。
	 * <p> 该方法会关闭指定的记录器，不论该记录器是否在模型中。
	 * 如果记录器不在模型中，则返回 <code>false</code>，否则返回 <code>true</code>。
	 * @param context 指定的记录器上下文。
	 * @return 该操作是否对模型造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean stopLoggerContext(LoggerContext context);
	
	/**
	 * 停止模型记录器中的全部记录器上下文，同时清空记录器模型中的所有记录器。
	 */
	public void stopAllLoggerContexts();
	
}
