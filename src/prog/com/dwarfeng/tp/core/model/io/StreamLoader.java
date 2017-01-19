package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 流记读取器。
 * <p> 用流实现的读取器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class StreamLoader implements Closeable{
	
	/**输入流。*/
	protected final InputStream in;

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public StreamLoader(InputStream in) {
		Objects.requireNonNull(in, "入口参数 in 不能为 null。");
		this.in = in;
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		this.in.close();
	}

}
