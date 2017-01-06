package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.dutil.basic.str.Name;


/**
 * 流多语言模型读取器。
 * <p> 用流实现的多语言接模型取器。
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class StreamMutilangLoader implements MutilangLoader, Closeable {

	/**读取器中的输入流*/
	protected final InputStream in;
	/**需要向模型中写入的多语言键值默认值*/
	protected final String defaultValue;
	/**需要向模型中写入的受支持的键集合*/
	protected final Set<Name> supportedKeys;
	/**需要向模型中写入的默认的多语言键值映射*/
	protected final Map<Name, String> defaultMutilangMap;
	
	/**
	 * 新实例。
	 * @param in 指定的输入流
	 * @param defaultValue 指定的多语言键值默认值。
	 * @param supportedKeys 指定的受支持的键的集合。
	 * @param defaultMutilangMap 指定的默认多语言键值映射。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public StreamMutilangLoader(
			InputStream in, 
			String defaultValue, 
			Set<Name> supportedKeys, 
			Map<Name, String> defaultMutilangMap
			) {
		Objects.requireNonNull(in, "入口参数 in 不能为 null。");
		Objects.requireNonNull(defaultValue, "入口参数 defaultValue 不能为 null。");
		Objects.requireNonNull(supportedKeys, "入口参数 supportedKeys 不能为 null。");
		Objects.requireNonNull(defaultMutilangMap, "入口参数 defaultMutilangMap 不能为 null。");
		
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
