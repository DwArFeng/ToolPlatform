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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param urls
	 * @param parent
	 */
	public LibraryClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param urls
	 */
	public LibraryClassLoader(URL[] urls) {
		super(urls);
		// TODO Auto-generated constructor stub
	}

}
