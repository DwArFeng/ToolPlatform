package com.dwarfeng.tp.core.model.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.cm.MutilangModel;
import com.dwarfeng.tp.core.model.struct.DefaultMutilangInfo;

/**
 * xml多语言模型读取器。
 * <p> 使用xml读取多语言模型。
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
			
			File dir = new File(rootDirStr);
			
			/*
			 * 根据 dom4j 的相关说明，此处转换是安全的。
			 * 	<!--使用如下的格式：<info language="zh" country="CN" variant="" label="简体中文" file="zh_CN.properties"></info>-->
			 */
			@SuppressWarnings("unchecked")
			List<Element> mutilangInfos = (List<Element>)root.elements("info");
			next:
			for(Element mutilangInfo : mutilangInfos){
				String language = mutilangInfo.attributeValue("language");
				String country = mutilangInfo.attributeValue("country");
				String variant = mutilangInfo.attributeValue("variant");
				String label = mutilangInfo.attributeValue("label");
				String filePath = mutilangInfo.attributeValue("file");
				
				if(
						Objects.nonNull(language) &&
						Objects.nonNull(country) &&
						Objects.nonNull(variant) &&
						Objects.nonNull(label) &&
						Objects.nonNull(filePath)
						){
					Locale locale = new Locale(language, country, variant);
					
					File targetFile = new File(dir, filePath);
					FileInputStream in = null;
					try{
						in = new FileInputStream(targetFile);
						Properties properties = new Properties();
						properties.load(in);
						Map<String, String> map = new HashMap<>();
						for(String key : properties.stringPropertyNames()){
							map.put(key, properties.getProperty(key));
						}
						
						mutilangModel.put(locale, new DefaultMutilangInfo(label, map));
					}catch (Exception e) {
						continue next;
					}
				}
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("无法向指定的多语言模型中读取流中的数据", e);
		}

	}

}
