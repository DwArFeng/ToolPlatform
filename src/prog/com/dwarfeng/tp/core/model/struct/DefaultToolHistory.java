package com.dwarfeng.tp.core.model.struct;

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
	private final Date ranTime;
	private final Date exitedDate;
	
	/**
	 * 新实例
	 * @param name 指定的名称。
	 * @param ranTime 指定的运行时间。
	 * @param exitedDate 指定的退出时间。
	 */
	public DefaultToolHistory(String name, Date ranTime, Date exitedDate) {
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
		Objects.requireNonNull(ranTime, "入口参数 ranTime 不能为 null。");
		Objects.requireNonNull(exitedDate, "入口参数 exitedDate 不能为 null。");

		this.name = name;
		this.ranTime = ranTime;
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
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getRanTime()
	 */
	@Override
	public Date getRanTime() {
		return ranTime;
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
