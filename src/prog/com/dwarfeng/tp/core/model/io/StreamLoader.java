package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * ���Ƕ�ȡ����
 * <p> ����ʵ�ֵĶ�ȡ����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class StreamLoader implements Closeable{
	
	/**��������*/
	protected final InputStream in;

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public StreamLoader(InputStream in) {
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
