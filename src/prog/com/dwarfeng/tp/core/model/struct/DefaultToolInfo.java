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
import com.dwarfeng.tp.core.model.cfg.ToolImageType;
import com.dwarfeng.tp.core.util.LocaleUtil;

/**
 * Ĭ�Ϲ�����Ϣ��
 * <p> ������Ϣ��Ĭ��ʵ�֡�
 *  ���У�stringFile Ӧ���������¸�ʽ��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultToolInfo implements ToolInfo {
	
	private final File stringFile;
	private final File imageFile_s;
	private final File imageFile_m;
	private final File imageFile_l;

	/**
	 * ��ʵ��
	 * @param stringFile �ı��ļ���
	 * @param imageFile_s СͼƬ�ļ���
	 * @param imageFile_m ��ͼƬ�ļ���
	 * @param imageFile_l ��ͼƬ�ļ���
	 */
	public DefaultToolInfo(File stringFile, File imageFile_s, File imageFile_m, File imageFile_l) {
		Objects.requireNonNull(stringFile, "��ڲ��� stringFile ����Ϊ null��");
		Objects.requireNonNull(imageFile_s, "��ڲ��� imageFile_s ����Ϊ null��");
		Objects.requireNonNull(imageFile_m, "��ڲ��� imageFile_m ����Ϊ null��");
		Objects.requireNonNull(imageFile_l, "��ڲ��� imageFile_l ����Ϊ null��");

		this.stringFile = stringFile;
		this.imageFile_s = imageFile_s;
		this.imageFile_m = imageFile_m;
		this.imageFile_l = imageFile_l;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getImage(com.dwarfeng.tp.core.model.cfg.ToolImageType)
	 */
	@Override
	public Image getImage(ToolImageType type) throws ProcessException {
		try{
			switch (type) {
			case ICON_LARGE:
				return ImageIO.read(imageFile_l).getScaledInstance(
						ToolImageType.ICON_LARGE.getWidth(),
						ToolImageType.ICON_LARGE.getHeight(), 
						Image.SCALE_DEFAULT);
			case ICON_MEDIUM:
				return ImageIO.read(imageFile_m).getScaledInstance(
						ToolImageType.ICON_MEDIUM.getWidth(),
						ToolImageType.ICON_MEDIUM.getHeight(), 
						Image.SCALE_DEFAULT);
			case ICON_SMALL:
				return ImageIO.read(imageFile_s).getScaledInstance(
						ToolImageType.ICON_SMALL.getWidth(),
						ToolImageType.ICON_SMALL.getHeight(), 
						Image.SCALE_DEFAULT);
			default:
				return null;
			}
		}catch (Exception e) {
			throw new ProcessException("������Ϣ-��ȡͼƬʧ��", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getVersion()
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

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getDescription(java.util.Locale)
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
	
	/**
	 * ��ȡȫ����������������ӳ�䡣
	 * @return ������������ӳ�䡣
	 * @throws ProcessException �����쳣
	 */
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

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getAuthors()
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

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getToolLibs()
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

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getToolClass()
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

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getInfoClass()
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

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getToolFile()
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
