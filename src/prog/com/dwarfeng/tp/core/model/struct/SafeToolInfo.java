package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.basic.prog.Version;

/**
 * ��ȫ������Ϣ��
 * <p> ������Ϣ�ӿڵİ�ȫʵ�֣����øýӿڵ�����һ�������������׳��쳣��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class SafeToolInfo implements ToolInfo {
	
	private final Image image;
	private final Version version;
	private final Map<Locale, String> descriptionMap;
	private final String[] authors;
	private final String toolClass;
	private final String infoClass;
	private final String toolFile;
	private final String[] toolLibs;
	
	
	
	/**
	 * ��ʵ����
	 * @param imageMap ָ����ͼƬӳ�䡣
	 * @param version ָ���Ĺ��߰汾��
	 * @param description ָ����������
	 * @param authors ָ�����������顣
	 * @param toolClass ����ָ����������ơ�
	 * @param toolFile ����ָ�����ļ����ơ�
	 * @param toolLibs ���ߵĿ��ļ��б�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>�����������а��� <code>null</code>Ԫ�ء�
	 */
	public SafeToolInfo(
			Image image, 
			Version version, 
			Map<Locale, String> description,
			String[] authors,
			String toolClass, 
			String infoClass,
			String toolFile, 
			String[] toolLibs) {
		Objects.requireNonNull(image, "��ڲ��� image ����Ϊ null��");
		Objects.requireNonNull(version, "��ڲ��� version ����Ϊ null��");
		Objects.requireNonNull(description, "��ڲ��� description ����Ϊ null��");
		ArrayUtil.requireNotContainsNull(authors, "��ڲ��� authors ����Ϊ null��Ҳ���ܰ��� null Ԫ�ء�");
		Objects.requireNonNull(toolClass, "��ڲ��� toolClass ����Ϊ null��");
		Objects.requireNonNull(infoClass, "��ڲ��� infoClass ����Ϊ null��");
		Objects.requireNonNull(toolFile, "��ڲ��� toolFile ����Ϊ null��");
		ArrayUtil.requireNotContainsNull(toolLibs, "��ڲ��� toolLibs ����Ϊ null��Ҳ���ܰ��� null Ԫ�ء�");
		
		this.image = image;
		this.version = version;
		this.descriptionMap = description;
		this.authors = authors;
		this.toolClass = toolClass;
		this.infoClass = infoClass;
		this.toolFile = toolFile;
		this.toolLibs = toolLibs;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getImage()
	 */
	@Override
	public Image getImage() throws ProcessException {
		return image;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getVersion()
	 */
	@Override
	public Version getVersion() {
		return version;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getDescriptionMap()
	 */
	@Override
	public Map<Locale, String> getDescriptionMap() throws ProcessException {
		return descriptionMap;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getDescription(java.util.Locale)
	 */
	@Override
	public String getDescription(Locale locale) {
		if(! descriptionMap.containsKey(null)){
			return descriptionMap.getOrDefault(locale, "");
		}else{
			return descriptionMap.getOrDefault(locale, descriptionMap.get(null));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getAuthors()
	 */
	@Override
	public String[] getAuthors() {
		return authors;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getToolClass()
	 */
	@Override
	public String getToolClass() {
		return toolClass;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getInfoClass()
	 */
	@Override
	public String getInfoClass() throws ProcessException {
		return infoClass;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getToolFile()
	 */
	@Override
	public String getToolFile() {
		return toolFile;
	}

	/*
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getToolLibs()
	 */
	@Override
	public String[] getToolLibs() {
		return toolLibs;
	}

}