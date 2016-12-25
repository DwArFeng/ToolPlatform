package com.dwarfeng.tp.model.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import org.dom4j.Element;

import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.basic.io.IoUtil;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.control.ToolPlatform;

/**
 * ·�������ࡣ
 * <p> ���ฺ���ڳ����ʼ����ʱ����·���Լ��ṩ·����һЩ���÷�������Ӧ��������ʱ��ʹ�á�
 * @author  DwArFeng
 * @since 1.8
 */
public final class PathResolve {
	
	private final URL defaultUrl;
	private final File releaseFile;
	private final boolean forceOverride;

	/**
	 * ʹ��ָ����dom4j�ڵ�����һ��·����
	 * @param element ָ���� dom4j �ڵ㡣
	 * @throws LoadFailedException  �ڵ��ȡʧ�ܡ�
	 * @throws NullPointerException ��ڲ���Ϊ null��
	 */
	public PathResolve(Element element, boolean forceOverride) throws LoadFailedException{
		Objects.requireNonNull(element, "��ڲ��� element ����Ϊ null��");
		
		Element defaultPath = element.element("default-path");
		Element releasePath = element.element("release-path");
		
		if(Objects.isNull(defaultPath) || Objects.isNull(releasePath)){
			throw new LoadFailedException();
		}
		
		String defaultValueString = element.element("default-path").attributeValue("value");
		String releaseValueString = element.element("release-path").attributeValue("value");
		String defaultTypeString = element.element("default-path").attributeValue("type");
		
		if(
				Objects.isNull(defaultValueString) ||
				Objects.isNull(releaseValueString) ||
				Objects.isNull(defaultTypeString)
				){
			throw new LoadFailedException();
		}
		
		PathResloveType defaultTypeEnum = null;
		
		try{
			defaultTypeEnum = PathResloveType.valueOf(defaultTypeString);
		}catch (IllegalArgumentException e) {
			throw new LoadFailedException();
		}
		
		switch (defaultTypeEnum) {
			case FILE:
				try {
					this.defaultUrl = new File(releaseValueString.replaceAll("/", File.separator)).toURI().toURL();
				} catch (MalformedURLException e) {
					throw new LoadFailedException();
				}
				break;
			case RESOURCE:
				this.defaultUrl = ToolPlatform.class.getResource(defaultValueString);
				if(this.defaultUrl == null) throw new LoadFailedException();
				break;
			case URL:
				try {
					this.defaultUrl = new URL(defaultValueString);
				} catch (MalformedURLException e) {
					throw new LoadFailedException();
				}
				break;
			default:
				throw new LoadFailedException();
		}
		
		this.releaseFile = new File(releaseValueString.replaceAll("//", File.separator));
		this.forceOverride = forceOverride;
	}
	
	
	/**
	 * �ͷ��ļ���
	 * <p> ����ļ��Ѿ����ڣ����ͷ��ļ������� <code>false</code> ���������ͷ��ļ��������ڳɹ���ʱ�򷵻� <code>true</code>��
	 * @return �Ƿ��ͷ����ļ���
	 * @throws IOException �ļ���ȡʧ���쳣��
	 */
	public boolean release() throws IOException{
		if(!forceOverride){
			if(releaseFile.exists()) return false;
		}
		
		FileUtil.createFileIfNotExists(releaseFile);
		
		OutputStream out = null;
		InputStream in = null;
		
		try{
			in = defaultUrl.openStream();
			out = new FileOutputStream(releaseFile);
			IoUtil.trans(in, out, 8192);
			return true;
		}finally {
			if(Objects.nonNull(out)){
				out.close();
			}
			if(Objects.nonNull(in)){
				in.close();
			}
		}
		
	}
	
	/**
	 * ��ȡ�ͷ��ļ��ļ�����������
	 * @return �ͷ��ļ�����������
	 * @throws IOException �ͷ��ļ������ڡ�
	 */
	public InputStream getReleaseFileInputstream() throws IOException{
		return new FileInputStream(releaseFile);
	}
	
}
