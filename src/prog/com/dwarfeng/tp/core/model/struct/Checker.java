package com.dwarfeng.tp.core.model.struct;

/**
 * 检查器。
 * <p> 检查指定的值是否合法。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface Checker<T> {

	/**
	 * 判断一个对象是否有效。
	 * @param obj 指定的对象。
	 * @return 该对象是否有效。
	 */
	public boolean isValid(T obj);
	
	/**
	 * 判断一个对象是否无效。
	 * @param obj 指定的对象。
	 * @return 该对象是否无效。
	 */
	public default boolean nonValid(T obj){
		return ! isValid(obj);
	}
	
}
