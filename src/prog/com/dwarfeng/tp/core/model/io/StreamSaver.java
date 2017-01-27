package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ����������
 * <p> ����ʵ�ֵı�������
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class StreamSaver<T> implements Closeable, Saver<T>{

	/**�������е������*/
	protected final OutputStream out;
	
	/**
	 * ��ʵ����
	 * @param out ָ�����������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public StreamSaver(OutputStream out) {
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
