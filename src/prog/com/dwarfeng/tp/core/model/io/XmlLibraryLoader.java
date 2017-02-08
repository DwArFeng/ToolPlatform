package com.dwarfeng.tp.core.model.io;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.FileExtensionNameFiliter;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.dutil.basic.prog.Loader;
import com.dwarfeng.tp.core.model.cm.LibraryModel;
import com.dwarfeng.tp.core.model.struct.DefaultLibrary;

/**
 * xml库模型读取器。
 * <p> 使用xml读取库模型。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlLibraryLoader extends StreamLoader<LibraryModel> implements Loader<LibraryModel> {

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public XmlLibraryLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.LibraryLoader#load(com.dwarfeng.tp.core.model.cm.LibraryModel)
	 */
	@Override
	public void load(LibraryModel libraryModel) throws LoadFailedException {
		Objects.requireNonNull(libraryModel, "入口参数 libraryModel 不能为 null。");
		
		try{
			SAXReader reader = new SAXReader();
			
			Element root = null;
			try{
				root = reader.read(in).getRootElement();
			}finally {
				in.close();
			}
			
			String rootDirStr = root.attributeValue("dir");
			if(Objects.isNull(rootDirStr)){
				throw new LoadFailedException("根元素缺失dir属性");
			}
			
			File dir = new File(rootDirStr);
			
			//如果文件夹不存在，则不存在任何库
			if(! dir.exists()) return;
			
			File[] libs = dir.listFiles(new FileExtensionNameFiliter(".jar"));
			
			for(File lib : libs){
				String name = lib.getName().substring(0, lib.getName().length()-4);
				libraryModel.add(new DefaultLibrary(name));
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("无法向指定的库模型中读取流中的数据", e);
		}

	}
	

}
