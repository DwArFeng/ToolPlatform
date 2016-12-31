package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;


/**
 * 流多语言接口读取器。
 * <p> 用流实现的多语言接口读取器。
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class StreamMutilangLoader implements MutilangLoader, Closeable {

	/**读取器中的输入流*/
	protected final InputStream in;
	
	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public StreamMutilangLoader(InputStream in) {
		this.in = in;
	}

	/*
	 * (non-Javadoc)
	 * @see com.sun.xml.internal.ws.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		in.close();
	}
	
}
