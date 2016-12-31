package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

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
