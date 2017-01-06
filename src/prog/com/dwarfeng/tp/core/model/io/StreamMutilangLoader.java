package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.dutil.basic.str.Name;


/**
 * ��������ģ�Ͷ�ȡ����
 * <p> ����ʵ�ֵĶ����Խ�ģ��ȡ����
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class StreamMutilangLoader implements MutilangLoader, Closeable {

	/**��ȡ���е�������*/
	protected final InputStream in;
	/**��Ҫ��ģ����д��Ķ����Լ�ֵĬ��ֵ*/
	protected final String defaultValue;
	/**��Ҫ��ģ����д�����֧�ֵļ�����*/
	protected final Set<Name> supportedKeys;
	/**��Ҫ��ģ����д���Ĭ�ϵĶ����Լ�ֵӳ��*/
	protected final Map<Name, String> defaultMutilangMap;
	
	/**
	 * ��ʵ����
	 * @param in ָ����������
	 * @param defaultValue ָ���Ķ����Լ�ֵĬ��ֵ��
	 * @param supportedKeys ָ������֧�ֵļ��ļ��ϡ�
	 * @param defaultMutilangMap ָ����Ĭ�϶����Լ�ֵӳ�䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public StreamMutilangLoader(
			InputStream in, 
			String defaultValue, 
			Set<Name> supportedKeys, 
			Map<Name, String> defaultMutilangMap
			) {
		Objects.requireNonNull(in, "��ڲ��� in ����Ϊ null��");
		Objects.requireNonNull(defaultValue, "��ڲ��� defaultValue ����Ϊ null��");
		Objects.requireNonNull(supportedKeys, "��ڲ��� supportedKeys ����Ϊ null��");
		Objects.requireNonNull(defaultMutilangMap, "��ڲ��� defaultMutilangMap ����Ϊ null��");
		
		this.in = in;
		this.defaultValue = defaultValue;
		this.supportedKeys = supportedKeys;
		this.defaultMutilangMap = defaultMutilangMap;
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
