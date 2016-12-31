package com.dwarfeng.tp.core.model.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.struct.DefaultMutilangInfo;
import com.dwarfeng.tp.core.model.vim.MutilangModel;

/**
 * xml多语言接口读取器。
 * <p> 使用xml读取多语言接口。
 * @author  DwArFeng
 * @since 1.8
 */
public final class XmlMutilangLoader extends StreamMutilangLoader {

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public XmlMutilangLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.MutilangLoader#load(com.dwarfeng.tp.core.model.vim.MutilangModel)
	 */
	@Override
	public void load(MutilangModel mutilangModel) throws LoadFailedException {
		Objects.requireNonNull(mutilangModel, "入口参数 mutilangModel 不能为 null。");
		
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
			
			File dirFile = new File(rootDirStr);
			mutilangModel.setDirFile(dirFile);
			
			/*
			 * 根据 dom4j 的相关说明，此处转换是安全的。
			 * 	<!--使用如下的格式：<info language="zh" country="CN" variant="" label="简体中文" file="zh_CN.properties"></info>-->
			 */
			@SuppressWarnings("unchecked")
			List<Element> mutilangInfos = (List<Element>)root.elements("info");
			for(Element mutilangInfo : mutilangInfos){
				String language = mutilangInfo.attributeValue("language");
				String country = mutilangInfo.attributeValue("country");
				String variant = mutilangInfo.attributeValue("variant");
				String label = mutilangInfo.attributeValue("label");
				String fileName = mutilangInfo.attributeValue("file");
				
				if(
						Objects.nonNull(language) &&
						Objects.nonNull(country) &&
						Objects.nonNull(variant) &&
						Objects.nonNull(label) &&
						Objects.nonNull(fileName)
						){
					Locale locale = new Locale(language, country, variant);
					File file = new File(fileName);
					mutilangModel.put(locale, new DefaultMutilangInfo(label, file));
				}
			}
			
		}catch (DocumentException | IOException e) {
			throw new LoadFailedException(e.getMessage(), e);
		}

	}

}
