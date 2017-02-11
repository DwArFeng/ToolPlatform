package com.dwarfeng.tp.core.model.cm;

import java.util.Objects;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.ToolInfoObverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * 工具信息模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolInfoModel extends ObverserSet<ToolInfoObverser>, ExternalReadWriteThreadSafe, Iterable<ToolInfo>{
	
	/**
	 * 将指定的工具信息添加到模型中。
	 * @param toolInfo 指定的工具信息。
	 * @return 是否添加成功。
	 */
	public boolean add(ToolInfo toolInfo);
	
	/**
	 * 移除指定的工具信息。
	 * @param toolInfo 指定的工具信息。
	 * @return 是否成功移除。
	 */
	public boolean remove(ToolInfo toolInfo);
	
	/**
	 * 清除指定的工具信息。
	 */
	public void clear();
	
	/**
	 * 返回该模型中的元素数量。
	 * @return 该模型中的元素数量。
	 */
	public int size();
	
	/**
	 * 返回该模型是否是空的。
	 * @return 该模型是否是空的。
	 */
	public boolean isEmpty();
	
	/**
	 * 返回该模型是否含有指定的名称的工具信息。
	 * @param name 指定的名称。
	 * @return 是否含有指定名称的工具信息。
	 */
	public boolean contains(String name);
	
	/**
	 * 返回该模型是否含有指定的名称的工具信息。
	 * @param name 指定的名称。
	 * @return 是否含有指定名称的工具信息。
	 */
	public default boolean contains(Name name){
		if(Objects.isNull(name)) return false;
		return contains(name.getName());
	}
	
	/**
	 * 获取模型中指定名称的工具信息。
	 * @param name 指定的名称。
	 * @return 模型中指定名称的工具信息。
	 */
	public ToolInfo get(String name);
	
	/**
	 * 获取模型中名称为指定名称接口的名称的工具信息。
	 * @param name 指定的名称接口。
	 * @return 模型中名称为指定名称接口的名称的工具信息。
	 */
	public default ToolInfo get(Name name){
		if(Objects.isNull(name)) return null;
		return get(name.getName());
	}
	
}
