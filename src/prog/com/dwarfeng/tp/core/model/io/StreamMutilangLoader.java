package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


/**
 * ��������ģ�Ͷ�ȡ����
 * <p> ����ʵ�ֵĶ����Խ�ģ��ȡ����
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class StreamMutilangLoader implements MutilangLoader, Closeable {

	/**��ȡ���е�������*/
	protected final InputStream in;
	
	/**
	 * ��ʵ����
	 * @param in ָ����������
	 */
	public StreamMutilangLoader(InputStream in) {
		Objects.requireNonNull(in, "��ڲ��� in ����Ϊ null��");
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
