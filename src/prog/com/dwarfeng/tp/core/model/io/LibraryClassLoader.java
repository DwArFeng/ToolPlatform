package com.dwarfeng.tp.core.model.io;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class LibraryClassLoader extends URLClassLoader {

	/**
	 * @param urls
	 * @param parent
	 * @param factory
	 */
	public LibraryClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
		super(urls, parent, factory);
		// TODO LibraryClassLoader 构造器方法。
	}

	/**
	 * @param urls
	 * @param parent
	 */
	public LibraryClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
		// TODO LibraryClassLoader 构造器方法。
	}

	/**
	 * @param urls
	 */
	public LibraryClassLoader(URL[] urls) {
		super(urls);
		// TODO LibraryClassLoader 构造器方法。
	}

}
