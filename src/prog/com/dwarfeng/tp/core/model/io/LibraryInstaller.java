package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.basic.io.IoUtil;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.cm.LibraryModel;
import com.dwarfeng.tp.core.model.struct.DefaultLibrary;
import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * 库安装器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class LibraryInstaller implements Installer<LibraryModel>, Closeable {
	
	private final InputStream config;
	private final InputStream in;
	private final String name;
	
	/**
	 * 新实例。
	 * @param config 配置文件的输入流。
	 * @param in 库文件的输入流。
	 * @param name 库的名称。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public LibraryInstaller(InputStream config, InputStream in, String name) {
		Objects.requireNonNull(config, "入口参数 config 不能为 null。");
		Objects.requireNonNull(in, "入口参数 in 不能为 null。");
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");

		this.config = config;
		this.in = in;
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.Installer#install(java.lang.Object)
	 */
	@Override
	public void install(LibraryModel libraryModel) throws ProcessException {
		Objects.requireNonNull(libraryModel, "入口参数 libraryModel 不能为 null。");
		
		try{
			SAXReader reader = new SAXReader();
			
			Element root = null;
			try{
				root = reader.read(config).getRootElement();
			}finally {
				config.close();
			}
			
			String rootDirStr = root.attributeValue("dir");
			if(Objects.isNull(rootDirStr)){
				throw new LoadFailedException("根元素缺失dir属性");
			}
			
			File dir = new File(rootDirStr);
			File libFile = new File(dir, name + ".jar" );
			FileUtil.createFileIfNotExists(libFile);
			
			OutputStream out = null;
			try{
				out = new FileOutputStream(libFile);
				IoUtil.trans(in, out, 4096);
			}finally {
				if(Objects.nonNull(out)){
					try{out.close();}catch (IOException e) {e.printStackTrace();}
				}
			}
			
			libraryModel.add(new DefaultLibrary(name));
		}catch (Exception e) {
			throw new ProcessException("无法安装指定的库：" + name);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		config.close();
		in.close();
	}

}
