package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.tp.core.model.cfg.ToolImageType;

/**
 * 安全工具信息。
 * <p> 工具信息接口的安全实现，调用该接口的任意一个方法都不会抛出异常。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
final class SafeToolInfo implements ToolInfo {
	
	private final Map<ToolImageType, Image> imageMap;
	private final Version version;
	private final Map<Locale, String> description;
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
	 * @throws IllegalArgumentException 图片映射不全（没有全部包含 ToolImageType 中的所有项）。
	 */
	public SafeToolInfo(
			Map<ToolImageType, Image> imageMap, 
			Version version, 
			Map<Locale, String> description,
			String[] authors,
			String toolClass, 
			String infoClass,
			String toolFile, 
			String[] toolLibs) {
		Objects.requireNonNull(imageMap, "入口参数 imageMap 不能为 null。");
		Objects.requireNonNull(version, "入口参数 version 不能为 null。");
		Objects.requireNonNull(description, "入口参数 description 不能为 null。");
		ArrayUtil.requireNotContainsNull(authors, "入口参数 authors 不能为 null，也不能包含 null 元素。");
		Objects.requireNonNull(toolClass, "入口参数 toolClass 不能为 null。");
		Objects.requireNonNull(infoClass, "入口参数 infoClass 不能为 null。");
		Objects.requireNonNull(toolFile, "入口参数 toolFile 不能为 null。");
		ArrayUtil.requireNotContainsNull(toolLibs, "入口参数 toolLibs 不能为 null，也不能包含 null 元素。");
		for(ToolImageType type : imageMap.keySet()){
			if(!imageMap.containsKey(type)){
				throw new IllegalArgumentException("imageMap 中的键必须包含所有 ToolImageType。");
			}
		}
		
		this.imageMap = imageMap;
		this.version = version;
		this.description = description;
		this.authors = authors;
		this.toolClass = toolClass;
		this.infoClass = infoClass;
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
		return version;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolInfo#getDescription(java.util.Locale)
	 */
	@Override
	public String getDescription(Locale locale) {
		return description.getOrDefault(locale, description.get(null));
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
