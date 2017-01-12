package com.dwarfeng.tp.core.model.cm;

import java.util.Collection;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.BackgrObverser;
import com.dwarfeng.tp.core.model.struct.Process;

/**
 * 后台模型。
 * @author DwArFeng
 * @since 1.8
 */
public interface BackgroundModel extends ObverserSet<BackgrObverser>, ExternalReadWriteThreadSafe, Iterable<Process>{
	
	/**
	 * 向后台模型中提交一个过程。
	 * <p> 当指定的进程为 <code>null</code>，或者模型中已经包含了指定的进程时，不进行任何操作。
	 * @param process 指定的进程。
	 * @return 该操作是否对模型造成了改变。
	 */
	public boolean submit(Process process);
	
	/**
	 * 向后台模型中提交指定集合中的所有过程。
	 * <p> 当指定的进程为 <code>null</code>，或者模型中已经包含了指定的进程时，不进行任何操作。
	 * @param c 所有过程组成的集合。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 * @return 该操作过程是否对模型造成了改变。
	 */
	public boolean submitAll(Collection<? extends Process> c);
	
	/**
	 * 返回该后台进程是否包含指定的对象。
	 * <p> 只有当对象为 {@link Process}类时，才有可能返回 <code>true</code>。
	 * @param o 指定的对象。
	 * @return 该后台模型中是否包含指定的对象。
	 */
	public boolean contains(Object o);
	
	/**
	 * 返回后台进程中
	 * @param c
	 */
	public void containsAll(Collection<?> c);
	
	public boolean isEmpty();
	
	

}
