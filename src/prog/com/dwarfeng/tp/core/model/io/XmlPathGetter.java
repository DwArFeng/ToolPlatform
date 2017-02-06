package com.dwarfeng.tp.core.model.io;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * ͨ����ȡXML������ʵ�ֵ�·���������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlPathGetter implements PathGetter {
	
	private final File libraryDir;
	private final File dataDir;
	
	public XmlPathGetter(InputStream libCfg, InputStream dataCfg) throws LoadFailedException, DocumentException {
		Objects.requireNonNull(libCfg, "��ڲ��� libCfg ����Ϊ null��");
		Objects.requireNonNull(dataCfg, "��ڲ��� dataCfg ����Ϊ null��");
		
		SAXReader libraryReader = new SAXReader();
		Element libraryRoot = null;
		libraryRoot = libraryReader.read(libCfg).getRootElement();
		String libraryRootStr = libraryRoot.attributeValue("dir");
		if(Objects.isNull(libraryRootStr)){
			throw new LoadFailedException("��Ԫ��ȱʧdir����");
		}
		
		SAXReader dataReader = new SAXReader();
		Element dataRoot = null;
		dataRoot = dataReader.read(dataCfg).getRootElement();
		String dataRootStr = dataRoot.attributeValue("dir");
		if(Objects.isNull(dataRootStr)){
			throw new LoadFailedException("��Ԫ��ȱʧdir����");
		}
		
		libraryDir = new File(libraryRootStr);
		dataDir = new File(dataRootStr);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.PathGetter#getLibraryPath(java.lang.String)
	 */
	@Override
	public String getLibraryPath(String name) {
		return new File(libraryDir, name+".jar").getAbsolutePath();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.PathGetter#getToolFilePath(java.lang.String)
	 */
	@Override
	public String getToolFilePath(String name) {
		return new File(dataDir, name).getAbsolutePath();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.PathGetter#getToolDirectory(com.dwarfeng.tp.core.model.struct.ToolInfo)
	 */
	@Override
	public File getToolDirectory(ToolInfo toolInfo) {
		return new File(new File(dataDir, toolInfo.getName() + File.separator).getAbsolutePath());
	}

}
