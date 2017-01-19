package com.dwarfeng.tp.core.model.io;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.cm.LibraryModel;
import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * ��ж������
 * @author DwArFeng
 * @since 1.8
 */
public final class LibraryUninstaller implements Uninstaller<LibraryModel> {
	
	private final InputStream config;
	private final String name;
	
	/**
	 * ��ʵ����
	 * @param config �����ļ�����������
	 * @param name ������ơ�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public LibraryUninstaller(InputStream config, String name) {
		Objects.requireNonNull(config, "��ڲ��� config ����Ϊ null��");
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");

		this.config = config;
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.Uninstaller#uninstall(java.lang.Object)
	 */
	@Override
	public void uninstall(LibraryModel libraryModel) throws ProcessException {
		Objects.requireNonNull(libraryModel, "��ڲ��� libraryModel ����Ϊ null��");
		
		try{
			SAXReader reader = new SAXReader();
			
			Element root = null;
			try{
				root = reader.read(config).getRootElement();
			}finally {
				config.close();
			}
			
			String rootDirStr = root.attributeValue("dir");
			if(Objects.isNull(rootDirStr)){
				throw new LoadFailedException("��Ԫ��ȱʧdir����");
			}
			
			File dir = new File(rootDirStr);
			File libFile = new File(dir, name + ".jar" );

			libraryModel.remove(name);
			FileUtil.deleteFile(libFile);
		}catch(Exception e){
			throw new ProcessException("�޷�ж��ָ���Ŀ⣺" + name);
		}
	}

}
