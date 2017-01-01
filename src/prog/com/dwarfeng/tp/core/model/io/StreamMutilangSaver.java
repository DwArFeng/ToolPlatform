package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 流多语言模型保存器。
 * <p> 用流实现的多语言模型保存器。
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class StreamMutilangSaver implements MutilangSaver, Closeable {

	/**保存器中的输出流*/
	protected final OutputStream out;
	
	/**
	 * 新实例。
	 * @param out 指定的输出流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public StreamMutilangSaver(OutputStream out) {
		this.out = out;
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		out.close();
	}

}
