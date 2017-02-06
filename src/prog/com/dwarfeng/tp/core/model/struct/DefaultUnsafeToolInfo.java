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
 * Ĭ�ϲ���ȫ������Ϣ��
 * <p> ����ȫ������Ϣ��Ĭ��ʵ�֡�
 *  ���У�stringFile Ӧ���������¸�ʽ��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultUnsafeToolInfo implements UnsafeToolInfo {
	
	private final String name;
	private final File stringFile;
	private final File imageFile;

	/**
	 * ��ʵ��
	 * @param name ����ȫ������Ϣ�����ơ�
	 * @param stringFile �ı��ļ���
	 * @param imageFile СͼƬ�ļ���
	 * @param imageFile_m ��ͼƬ�ļ���
	 * @param imageFile_l ��ͼƬ�ļ���
	 */
	public DefaultUnsafeToolInfo(String name, File stringFile, File imageFile) {
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
		Objects.requireNonNull(stringFile, "��ڲ��� stringFile ����Ϊ null��");
		Objects.requireNonNull(imageFile, "��ڲ��� imageFile_s ����Ϊ null��");

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
			throw new ProcessException("������Ϣ-��ȡͼƬʧ��", e);
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
			throw new ProcessException("������Ϣ-��ȡ�汾ʧ��", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//��̫���ܷ�����
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
			 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
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
			throw new ProcessException("������Ϣ-��ȡ�汾ʧ��", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//��̫���ܷ�����
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
			 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
			 */
			@SuppressWarnings("unchecked")
			List<Element> authors = (List<Element>) reader.read(in).getRootElement().elements("author");
			List<String> author_strs = new ArrayList<>();
			for(Element author : authors){
				author_strs.add(author.attributeValue("value"));
			}
			return author_strs.toArray(new String[0]);
		}catch (Exception e) {
			throw new ProcessException("������Ϣ-��ȡ�汾ʧ��", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//��̫���ܷ�����
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
			 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
			 */
			@SuppressWarnings("unchecked")
			List<Element> libs = (List<Element>) reader.read(in).getRootElement().elements("lib");
			List<String> lib_strs = new ArrayList<>();
			for(Element lib : libs){
				lib_strs.add(lib.attributeValue("value"));
			}
			return lib_strs.toArray(new String[0]);
		}catch (Exception e) {
			throw new ProcessException("������Ϣ-��ȡ�汾ʧ��", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//��̫���ܷ�����
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
			throw new ProcessException("������Ϣ-��ȡ�汾ʧ��", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//��̫���ܷ�����
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
			throw new ProcessException("������Ϣ-��ȡ�汾ʧ��", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//��̫���ܷ�����
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
			throw new ProcessException("������Ϣ-��ȡ�汾ʧ��", e);
		}finally{
			if(Objects.nonNull(in)){
				try{
					in.close();
				}catch (IOException e) {
					//��̫���ܷ�����
					e.printStackTrace();
				}
			}
		}
	}

}
