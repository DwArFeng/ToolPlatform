package com.dwarfeng.tp.core.model.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.basic.io.IoUtil;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.cm.LibraryModel;
import com.dwarfeng.tp.core.model.struct.DefaultLibrary;
import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * �ⰲװ����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class LibraryInstaller implements Installer<LibraryModel>, Closeable {
	
	private final InputStream config;
	private final InputStream in;
	private final String name;
	
	/**
	 * ��ʵ����
	 * @param config �����ļ�����������
	 * @param in ���ļ�����������
	 * @param name ������ơ�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public LibraryInstaller(InputStream config, InputStream in, String name) {
		Objects.requireNonNull(config, "��ڲ��� config ����Ϊ null��");
		Objects.requireNonNull(in, "��ڲ��� in ����Ϊ null��");
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");

		this.config = config;
		this.in = in;
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.Installer#install(java.lang.Object)
	 */
	@Override
	public void install(LibraryModel libraryModel) throws ProcessException {
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
			FileUtil.createFileIfNotExists(libFile);
			
			OutputStream out = null;
			try{
				out = new FileOutputStream(libFile);
				IoUtil.trans(in, out, 4096);
			}finally {
				if(Objects.nonNull(out)){
					try{out.close();}catch (IOException e) {e.printStackTrace();}
				}
			}
			
			libraryModel.add(new DefaultLibrary(name));
		}catch (Exception e) {
			throw new ProcessException("�޷���װָ���Ŀ⣺" + name);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		config.close();
		in.close();
	}

}
