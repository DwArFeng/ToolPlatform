package com.dwarfeng.tp.core.model.io;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.tp.core.model.struct.DefaultUnsafeToolHistory;
import com.dwarfeng.tp.core.model.struct.UnsafeToolHistory;

/**
 * xml 不安全工具历史读取器。
 * 使用 xml 读取不安全工具历史。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlUnsafeToolHistoryLoader extends StreamLoader<List<UnsafeToolHistory>>{

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public XmlUnsafeToolHistoryLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(List<UnsafeToolHistory> unsafeToolHistories) throws LoadFailedException {
		Objects.requireNonNull(unsafeToolHistories, "入口参数 unsafeToolHistories 不能为 null。");
		
		try{
			SAXReader reader = new SAXReader();
			
			Element root = null;
			try{
				root = reader.read(in).getRootElement();
			}finally {
				in.close();
			}
			
			/*
			 * 根据 dom4j 的相关说明，此处转换是安全的。
			 */
			@SuppressWarnings("unchecked")
			List<Element> histories = (List<Element>)root.elements("history");
			
			next:
			for(Element history : histories){
				try{
					String name = history.attributeValue("name");
					String ranDateStr = history.attributeValue("ran_date");
					String exitedDateStr = history.attributeValue("exited_date");
					String exitedCodeStr = history.attributeValue("exited_code");
					unsafeToolHistories.add(new DefaultUnsafeToolHistory(name, ranDateStr, exitedDateStr, exitedCodeStr));
				}catch (Exception e) {
					continue next;
				}
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("不安全工具历史读取器-无法向指定的不安全工具历史列表中读取流中的数据", e);
		}

	}

}
