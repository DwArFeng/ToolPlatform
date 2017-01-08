package com.dwarfeng.tp.core.model.struct;

/**
 * 过程结果。
 * <p> 过程结果是一个过程在执行完毕后返回的结果信息。
 * @author DwArFeng
 * @since 1.8
 */
public interface ProcessResult {
	
	/**
	 * 返回处理结果中的信息。
	 * @return 处理结果中的信息。
	 */
	public String getMessage();
	
	/**
	 * 返回处理结果中的可抛出对象。
	 * <p> 如果处理结果发生了异常，则该方法通常应该返回该异常；如果处理没有发生异常，则应该返回 <code>null</code>。
	 * @return 处理结果中的可抛出对象。
	 */
	public Throwable getThrowable();

}
