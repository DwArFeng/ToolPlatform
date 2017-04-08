package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.basic.prog.Version;

/**
 * 默认工具信息。
 * <p> 工具信息的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultToolInfo implements ToolInfo {
	
	private final String name;
	private final Image image;
	private final Version version;
	private final Map<Locale, String> descriptionMap;
	private final String[] authors;
	private final String toolClass;
	private final String infoClass;
	private final String toolFile;
	private final String[] toolLibs;
	
	
	
	/**
	 * 新实例。
	 * @param imageMap 指定的图片映射。
	 * @param version 指定的工具版本。
	 * @param description 指定的描述。
	 * @param authors 指定的作者数组。
	 * @param toolClass 工具指定的类的名称。
	 * @param toolFile 工具指定的文件名称。
	 * @param toolLibs 工具的库文件列表。
	 * @throws NullPointerException 入口参数为 <code>null</code>，或其数组中包含 <code>null</code>元素。
	 */
	public DefaultToolInfo(
			String name,
			Image image, 
			Version version, 
			Map<Locale, String> description,
			String[] authors,
			String toolClass, 
			String infoClass,
			String toolFile, 
			String[] toolLibs) {
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
		Objects.requireNonNull(image, "入口参数 image 不能为 null。");
		Objects.requireNonNull(version, "入口参数 version 不能为 null。");
		Objects.requireNonNull(description, "入口参数 description 不能为 null。");
		ArrayUtil.requireNotContainsNull(authors, "入口参数 authors 不能为 null，也不能包含 null 元素。");
		Objects.requireNonNull(toolClass, "入口参数 toolClass 不能为 null。");
		Objects.requireNonNull(infoClass, "入口参数 infoClass 不能为 null。");
		Objects.requireNonNull(toolFile, "入口参数 toolFile 不能为 null。");
		ArrayUtil.requireNotContainsNull(toolLibs, "入口参数 toolLibs 不能为 null，也不能包含 null 元素。");
		
		this.name = name;
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
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getImage()
	 */
	@Override
	public Image getImage() {
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
	public Map<Locale, String> getDescriptionMap() {
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
	public String getInfoClass() {
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
