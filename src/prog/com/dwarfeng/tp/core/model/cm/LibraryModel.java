package com.dwarfeng.tp.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.LibraryObverser;
import com.dwarfeng.tp.core.model.struct.Library;

/**
 * 库模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface LibraryModel extends ExternalReadWriteThreadSafe, ObverserSet<LibraryObverser>, Iterable<Library>{
	
	/**
	 * 将指定的库添加到模型中。
	 * @param library 指定的库。
	 * @return 是否添加成功。
	 */
	public boolean add(Library library);
	
	/**
	 * 移除指定的库。
	 * @param library 指定的库。
	 * @return 是否成功移除。
	 */
	public boolean remove(Library library);
	
	/**
	 * 清除指定的库。
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
	 * 返回该模型是否含有指定的名称的库。
	 * @param name 指定的名称。
	 * @return 是否含有指定名称的库。
	 */
	public boolean contains(Name name);
	
}
