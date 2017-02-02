package com.dwarfeng.tp.core.model.io;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.tp.core.model.cm.ToolInfoModel;
import com.dwarfeng.tp.core.model.struct.DefaultToolInfo;

public class XmlToolInfoLoader extends StreamLoader<ToolInfoModel> {

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public XmlToolInfoLoader(InputStream in) {
		super(in);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(ToolInfoModel toolInfoModel) throws LoadFailedException {
		Objects.requireNonNull(toolInfoModel, "入口参数 toolInfoModel 不能为 null。");
		
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
			 */
			@SuppressWarnings("unchecked")
			List<Element> toolInfos = (List<Element>)root.elements("tool");
			next:
			for(Element toolinfo : toolInfos){
				try{
					String name = toolinfo.attributeValue("name");
					File strs = new File(dir, toolinfo.element("strs").attributeValue("url"));
					File img = new File(dir, toolinfo.element("img").attributeValue("url"));
					toolInfoModel.put(name, new DefaultToolInfo(strs, img));
				}catch (Exception e) {
					continue next;
				}
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("工具信息模型读取器-无法向指定的工具信息模型中读取流中的数据", e);
		}

	}

}
