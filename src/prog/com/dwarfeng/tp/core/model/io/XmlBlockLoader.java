package com.dwarfeng.tp.core.model.io;

import java.io.InputStream;
import java.util.Objects;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.cm.BlockModel;

/**
 * xml阻挡模型读取器。
 * <p> 使用xml读取阻挡模型。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlBlockLoader extends StreamLoader implements Loader<BlockModel> {

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public XmlBlockLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(BlockModel blockModel) throws LoadFailedException {
		Objects.requireNonNull(blockModel, "入口参数 libraryModel 不能为 null。");
		
		try{
			SAXReader reader = new SAXReader();
			
			Element root = null;
			try{
				root = reader.read(in).getRootElement();
			}finally {
				in.close();
			}
			
			//TODO 实现根元素的解析算法。
			
		}catch (Exception e) {
			throw new LoadFailedException("无法向指定的库模型中读取流中的数据", e);
		}

	}

}
