package com.dwarfeng.tp.core.model.struct;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;

/**
 *  库值检查器。
 * <p> 使用xml检查库模型。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class LibraryKeyChecker implements Checker<String>{
	
	private final File dir;
	
	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 * @throws ProcessException 过程异常。
	 */
	public LibraryKeyChecker(InputStream in) throws ProcessException{
		Objects.requireNonNull(in, "入口参数 in 不能为 null。");
		
		try{
			SAXReader reader = new SAXReader();
			Element root = reader.read(in).getRootElement();
			
			String rootDirStr = root.attributeValue("dir");
			if(Objects.isNull(rootDirStr)){
				throw new LoadFailedException("根元素缺失dir属性");
			}
			
			File dir = new File(rootDirStr);
			this.dir = dir;
		}catch (DocumentException | LoadFailedException e) {
			throw new ProcessException("解析流过程出现异常", e.getCause());
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.Checker#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(String obj) {
		File file = new File(dir, obj + ".jar");
		return file.exists();
	}

}
