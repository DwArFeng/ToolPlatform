package com.dwarfeng.tp.core.model.struct;

import java.net.URL;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.io.LibraryClassLoader;

/**
 * 库。
 * <p> 该接口的读写均应该是线程安全的。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface Library extends ExternalReadWriteThreadSafe{
	
	/**
	 * 获取该库是否被持有引用。
	 * @return 该库是否被持有引用。
	 */
	public boolean hasReference();
	
//	/**
//	 * 获取引用该库的所有库类加载器。
//	 * @return 引用该库的所有库类加载器。
//	 */
//	public Set<LibraryClassLoader> getReferences();
	
	/**
	 * 增加一个库类加载器的引用。
	 * @param libraryClassLoader 指定的库类加载器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void reference(LibraryClassLoader libraryClassLoader);
	
	/**
	 * 解除一个库类加载器的引用。
	 * @param libraryClassLoader 指定的库类加载器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void removeReference(LibraryClassLoader libraryClassLoader);
	
	/**
	 * 获取该库指向的URL。
	 * @return 该库指向的URL。
	 * @throws ProcessException 过程异常。
	 */
	public URL getURL() throws ProcessException;
	
	
	
}
