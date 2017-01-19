package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.basic.prog.Version;

/**
 * Ĭ�Ϲ�����Ϣ��
 * <p> ������Ϣ�ӿڵ�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultToolInfo implements ToolInfo {
	
	private final Map<ToolImageType, Image> imageMap;
	private final Version version;
	private final Map<Locale, String> description;
	private final String[] authors;
	private final String toolClass;
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
	 * @throws IllegalArgumentException ͼƬӳ�䲻ȫ��û��ȫ������ ToolImageType �е��������
	 */
	public DefaultToolInfo(
			Map<ToolImageType, Image> imageMap, 
			Version version, 
			Map<Locale, String> description,
			String[] authors,
			String toolClass, 
			String toolFile, 
			String[] toolLibs) {
		Objects.requireNonNull(imageMap, "��ڲ��� imageMap ����Ϊ null��");
		Objects.requireNonNull(version, "��ڲ��� version ����Ϊ null��");
		Objects.requireNonNull(description, "��ڲ��� description ����Ϊ null��");
		ArrayUtil.requireNotContainsNull(authors, "��ڲ��� authors ����Ϊ null��Ҳ���ܰ��� null Ԫ�ء�");
		Objects.requireNonNull(toolClass, "��ڲ��� toolClass ����Ϊ null��");
		Objects.requireNonNull(toolFile, "��ڲ��� toolFile ����Ϊ null��");
		ArrayUtil.requireNotContainsNull(toolLibs, "��ڲ��� toolLibs ����Ϊ null��Ҳ���ܰ��� null Ԫ�ء�");
		for(ToolImageType type : imageMap.keySet()){
			if(!imageMap.containsKey(type)){
				throw new IllegalArgumentException("imageMap �еļ������������ ToolImageType��");
			}
		}
		
		this.imageMap = imageMap;
		this.version = version;
		this.description = description;
		this.authors = authors;
		this.toolClass = toolClass;
		this.toolFile = toolFile;
		this.toolLibs = toolLibs;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getImage(com.dwarfeng.tp.core.model.struct.ToolImageType)
	 */
	@Override
	public Image getImage(ToolImageType type) {
		return imageMap.get(type);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getVersion()
	 */
	@Override
	public Version getVersion() {
		return this.version;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getDescription(java.util.Locale)
	 */
	@Override
	public String getDescription(Locale locale) {
		return this.description.get(locale);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getAuthors()
	 */
	@Override
	public String[] getAuthors() {
		return this.authors;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getToolClass()
	 */
	@Override
	public String getToolClass() {
		return this.toolClass;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getToolFile()
	 */
	@Override
	public String getToolFile() {
		return this.toolFile;
	}

	/*
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getToolLibs()
	 */
	@Override
	public String[] getToolLibs() {
		return this.toolLibs;
	}

}
