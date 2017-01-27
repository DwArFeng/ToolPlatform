package com.dwarfeng.tp.core.model.struct;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;

/**
 *  ��ֵ�������
 * <p> ʹ��xml����ģ�͡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class LibraryKeyChecker implements Checker<String>{
	
	private final File dir;
	
	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 * @throws ProcessException �����쳣��
	 */
	public LibraryKeyChecker(InputStream in) throws ProcessException{
		Objects.requireNonNull(in, "��ڲ��� in ����Ϊ null��");
		
		try{
			SAXReader reader = new SAXReader();
			Element root = reader.read(in).getRootElement();
			
			String rootDirStr = root.attributeValue("dir");
			if(Objects.isNull(rootDirStr)){
				throw new LoadFailedException("��Ԫ��ȱʧdir����");
			}
			
			File dir = new File(rootDirStr);
			this.dir = dir;
		}catch (DocumentException | LoadFailedException e) {
			throw new ProcessException("���������̳����쳣", e.getCause());
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
