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
		// TODO LibraryClassLoader ������������
	}

	/**
	 * @param urls
	 * @param parent
	 */
	public LibraryClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
		// TODO LibraryClassLoader ������������
	}

	/**
	 * @param urls
	 */
	public LibraryClassLoader(URL[] urls) {
		super(urls);
		// TODO LibraryClassLoader ������������
	}

}
