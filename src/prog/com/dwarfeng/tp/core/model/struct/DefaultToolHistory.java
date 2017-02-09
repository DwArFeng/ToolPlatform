package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.util.Date;
import java.util.Objects;

/**
 * 默认工具历史。
 * <p> 工具历史的默认实现。
 * <p> 该类线程安全。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultToolHistory implements ToolHistory{
	
	private final String name;
	private final Image image;
	private final Date runnedTime;
	private final Date exitedDate;
	
	/**
	 * 新实例
	 * @param name 指定的名称。
	 * @param image 指定的图片。
	 * @param runnedTime 指定的运行时间。
	 * @param exitedDate 指定的退出时间。
	 */
	public DefaultToolHistory(String name, Image image, Date runnedTime, Date exitedDate) {
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
		Objects.requireNonNull(image, "入口参数 image 不能为 null。");
		Objects.requireNonNull(runnedTime, "入口参数 runnedTime 不能为 null。");
		Objects.requireNonNull(exitedDate, "入口参数 exitedDate 不能为 null。");

		this.name = name;
		this.image = image;
		this.runnedTime = runnedTime;
		this.exitedDate = exitedDate;
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
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getImage()
	 */
	@Override
	public Image getImage() {
		return image;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getRunedTime()
	 */
	@Override
	public Date getRunedTime() {
		return runnedTime;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getExitedTime()
	 */
	@Override
	public Date getExitedTime() {
		return exitedDate;
	}

}
