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
 * 路径解析类。
 * <p> 该类负责在程序初始化的时候定义路径以及提供路径的一些常用方法，不应该在其它时候使用。
 * @author  DwArFeng
 * @since 1.8
 */
public final class PathResolve {
	
	private final URL defaultUrl;
	private final File releaseFile;
	private final boolean forceOverride;

	/**
	 * 使用指定的dom4j节点生成一个路径。
	 * @param element 指定的 dom4j 节点。
	 * @throws LoadFailedException  节点读取失败。
	 * @throws NullPointerException 入口参数为 null。
	 */
	public PathResolve(Element element, boolean forceOverride) throws LoadFailedException{
		Objects.requireNonNull(element, "入口参数 element 不能为 null。");
		
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
	 * 释放文件。
	 * <p> 如果文件已经存在，则不释放文件，返回 <code>false</code> ；否则尝试释放文件，并且在成功的时候返回 <code>true</code>。
	 * @return 是否释放了文件。
	 * @throws IOException 文件读取失败异常。
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
	 * 获取释放文件文件的输入流。
	 * @return 释放文件的输入流。
	 * @throws IOException 释放文件不存在。
	 */
	public InputStream getReleaseFileInputstream() throws IOException{
		return new FileInputStream(releaseFile);
	}
	
}
