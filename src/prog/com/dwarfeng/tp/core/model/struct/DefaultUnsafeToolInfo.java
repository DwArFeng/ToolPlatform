package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.tp.core.util.LocaleUtil;

/**
 * 默认不安全工具信息。
 * <p> 不安全工具信息的默认实现。
 *  其中，stringFile 应该满足如下格式：
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultUnsafeToolInfo implements UnsafeToolInfo {
	
	private final String name;
	private final File stringFile;
	private final File imageFile;

	/**
	 * 新实例
	 * @param name 不安全工具信息的名称。
	 * @param stringFile 文本文件。
	 * @param imageFile 小图片文件。
	 * @param imageFile_m 中图片文件。
	 * @param imageFile_l 大图片文件。
	 */
	public DefaultUnsafeToolInfo(String name, File stringFile, File imageFile) {
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
		Objects.requireNonNull(stringFile, "入口参数 stringFile 不能为 null。");
		Objects.requireNonNull(imageFile, "入口参数 imageFile_s 不能为 null。");

		this.name = name;
		this.stringFile = stringFile;
		this.imageFile = imageFile;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolInfo#getImage()
	 */
	@Override
	public Image getImage() throws ProcessException {
		try{
			return ImageIO.read(imageFile);
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取图片失败", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolInfo#getVersion()
	 */
	@Override
	public Version getVersion() throws ProcessException {
		InputStream in = null;
		try{
			in = new FileInputStream(stringFile);
			SAXReader reader = new SAXReader();
			Element version = reader.read(in).getRootElement().element("version");
			String typ = version.attributeValue("typ");
			String fst = version.attributeValue("fst");
			String snd = version.attributeValue("snd");
			String trd = version.attributeValue("trd");
			String bdate = version.attributeValue("bdate");
			String bchar = version.attributeValue("bchar");
			return new DefaultVersion.Builder()
					.type(VersionType.valueOf(typ))
					.firstVersion(Byte.parseByte(fst))
					.secondVersion(Byte.parseByte(snd))
					.thirdVersion(Byte.parseByte(trd))
					.buildVersion(bchar.charAt(0))
					.buildDate(bdate)
					.build();
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取版本失败", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//不太可能发生。
					e.printStackTrace();
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolInfo#getDescription(java.util.Locale)
	 */
	@Override
	public String getDescription(Locale locale) throws ProcessException {
		Map<Locale, String> descriptionMap = getDescriptionMap();
		if(! descriptionMap.containsKey(null)){
			return descriptionMap.getOrDefault(locale, "");
		}else{
			return descriptionMap.getOrDefault(locale, descriptionMap.get(null));
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolInfo#getDescriptionMap()
	 */
	@Override
	public Map<Locale, String> getDescriptionMap() throws ProcessException{
		InputStream in = null;
		try{
			in = new FileInputStream(stringFile);
			SAXReader reader = new SAXReader();
			/*
			 * 根据 dom4j 的相关说明，此处转换是安全的。
			 */
			@SuppressWarnings("unchecked")
			List<Element> descriptions = (List<Element>) reader.read(in).getRootElement().elements("description");
			Map<Locale, String> descriptionMap = new HashMap<>();
			for(Element description : descriptions){
				descriptionMap.put(
						LocaleUtil.parseLocale(description.attributeValue("locale")),
						description.getStringValue());
			}
			return descriptionMap;
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取版本失败", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//不太可能发生。
					e.printStackTrace();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolInfo#getAuthors()
	 */
	@Override
	public String[] getAuthors() throws ProcessException {
		InputStream in = null;
		try{
			in = new FileInputStream(stringFile);
			SAXReader reader = new SAXReader();
			/*
			 * 根据 dom4j 的相关说明，此处转换是安全的。
			 */
			@SuppressWarnings("unchecked")
			List<Element> authors = (List<Element>) reader.read(in).getRootElement().elements("author");
			List<String> author_strs = new ArrayList<>();
			for(Element author : authors){
				author_strs.add(author.attributeValue("value"));
			}
			return author_strs.toArray(new String[0]);
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取版本失败", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//不太可能发生。
					e.printStackTrace();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolInfo#getToolLibs()
	 */
	@Override
	public String[] getToolLibs() throws ProcessException {
		InputStream in = null;
		try{
			in = new FileInputStream(stringFile);
			SAXReader reader = new SAXReader();
			/*
			 * 根据 dom4j 的相关说明，此处转换是安全的。
			 */
			@SuppressWarnings("unchecked")
			List<Element> libs = (List<Element>) reader.read(in).getRootElement().elements("lib");
			List<String> lib_strs = new ArrayList<>();
			for(Element lib : libs){
				lib_strs.add(lib.attributeValue("value"));
			}
			return lib_strs.toArray(new String[0]);
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取版本失败", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//不太可能发生。
					e.printStackTrace();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolInfo#getToolClass()
	 */
	@Override
	public String getToolClass() throws ProcessException {
		InputStream in = null;
		try{
			in = new FileInputStream(stringFile);
			SAXReader reader = new SAXReader();
			Element toolClass = reader.read(in).getRootElement().element("tool-class");
			return toolClass.attributeValue("value");
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取版本失败", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//不太可能发生。
					e.printStackTrace();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolInfo#getInfoClass()
	 */
	@Override
	public String getInfoClass() throws ProcessException {
		InputStream in = null;
		try{
			in = new FileInputStream(stringFile);
			SAXReader reader = new SAXReader();
			Element infoClass = reader.read(in).getRootElement().element("info-class");
			return infoClass.attributeValue("value");
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取版本失败", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//不太可能发生。
					e.printStackTrace();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolInfo#getToolFile()
	 */
	@Override
	public String getToolFile() throws ProcessException {
		InputStream in = null;
		try{
			in = new FileInputStream(stringFile);
			SAXReader reader = new SAXReader();
			Element toolFile = reader.read(in).getRootElement().element("tool-file");
			return toolFile.attributeValue("value");
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取版本失败", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//不太可能发生。
					e.printStackTrace();
				}
			}
		}
	}

}
