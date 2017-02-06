package com.dwarfeng.tp.core.model.io;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.cm.LibraryModel;
import com.dwarfeng.tp.core.model.struct.Library;
import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * ��ж������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class LibraryUninstaller implements Uninstaller<LibraryModel> {
	
	private final InputStream config;
	private final Library library;
	
	/**
	 * ��ʵ����
	 * @param config �����ļ�����������
	 * @param library �⡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public LibraryUninstaller(InputStream config, Library library) {
		Objects.requireNonNull(config, "��ڲ��� config ����Ϊ null��");
		Objects.requireNonNull(library, "��ڲ��� name ����Ϊ null��");

		this.config = config;
		this.library = library;
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
			File libFile = new File(dir, library.getName() + ".jar" );

			libraryModel.remove(library);
			FileUtil.deleteFile(libFile);
		}catch(Exception e){
			throw new ProcessException("�޷�ж��ָ���Ŀ⣺" + library);
		}
	}

}
