package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ��������ģ�ͱ�������
 * <p> ����ʵ�ֵĶ�����ģ�ͱ�������
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class StreamMutilangSaver implements MutilangSaver, Closeable {

	/**�������е������*/
	protected final OutputStream out;
	
	/**
	 * ��ʵ����
	 * @param out ָ�����������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
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