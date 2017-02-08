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
 * xml��ģ�Ͷ�ȡ����
 * <p> ʹ��xml��ȡ��ģ�͡�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlLibraryLoader extends StreamLoader<LibraryModel> implements Loader<LibraryModel> {

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
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
		Objects.requireNonNull(libraryModel, "��ڲ��� libraryModel ����Ϊ null��");
		
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
				throw new LoadFailedException("��Ԫ��ȱʧdir����");
			}
			
			File dir = new File(rootDirStr);
			
			//����ļ��в����ڣ��򲻴����κο�
			if(! dir.exists()) return;
			
			File[] libs = dir.listFiles(new FileExtensionNameFiliter(".jar"));
			
			for(File lib : libs){
				String name = lib.getName().substring(0, lib.getName().length()-4);
				libraryModel.add(new DefaultLibrary(name));
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("�޷���ָ���Ŀ�ģ���ж�ȡ���е�����", e);
		}

	}
	

}
