package com.dwarfeng.tp.core.model.io;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.control.ToolPlatform;
import com.dwarfeng.tp.core.model.struct.DefaultResource;
import com.dwarfeng.tp.core.model.vim.ResourceModel;

/**
 * xml资源模型读取器。
 * <p> 使用xml读取资源模型。
 * @author  DwArFeng
 * @since 1.8
 */
public class XmlResourceLoader extends StreamResourceLoader {

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public XmlResourceLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.ResourceLoader#load(com.dwarfeng.tp.core.model.vim.ResourceModel)
	 */
	@Override
	public void load(ResourceModel resourceModel) throws LoadFailedException {
		Objects.requireNonNull(resourceModel, "入口参数 resourceModel 不能为 null。");

		try{
			SAXReader reader = new SAXReader();
			Element root = reader.read(in).getRootElement();
			
			/*
			 * 根据 dom4j 的相关说明，此处转换是安全的。
			 */
			@SuppressWarnings("unchecked")
			List<Element> infos = (List<Element>)root.elements("info");
			
			for(Element info : infos){
				String defString = info.attributeValue("default");
				String resString = info.attributeValue("path");
				String key = info.attributeValue("key");
				
				if(Objects.isNull(defString) || Objects.isNull(resString) || Objects.isNull(key)) {
					throw new LoadFailedException("配置文件缺失属性");
				}
				
				URL def = ToolPlatform.class.getResource(defString);
				
				if(Objects.isNull(def)){
					throw new LoadFailedException("配置中资源的路径不正确");
				}
				
				File res = new File(resString);
				
				resourceModel.put(key, new DefaultResource(def, res));
			}
			
		}catch (DocumentException e) {
			throw new LoadFailedException("无法向资源模型中读取流中的数据", e);
		}
		
	}

}
