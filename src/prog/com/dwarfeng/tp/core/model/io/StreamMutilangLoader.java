package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


/**
 * 流多语言模型读取器。
 * <p> 用流实现的多语言接模型取器。
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class StreamMutilangLoader implements MutilangLoader, Closeable {

	/**读取器中的输入流*/
	protected final InputStream in;
	
	/**
	 * 新实例。
	 * @param in 指定的输入流
	 */
	public StreamMutilangLoader(InputStream in) {
		Objects.requireNonNull(in, "入口参数 in 不能为 null。");
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
