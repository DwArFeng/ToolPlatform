package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * ����Դģ�Ͷ�ȡ����
 * <p> ����ʵ�ֵ���Դģ�Ͷ�ȡ����
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class StreamResourceLoader implements ResourceLoader, Closeable {
	
	/**������*/
	protected final InputStream in;

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public StreamResourceLoader(InputStream in) {
		Objects.requireNonNull(in, "��ڲ��� in ����Ϊ null��");
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
