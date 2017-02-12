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
	private final Date ranDate;
	private final Date exitedDate;
	private final int exitedCode;
	
	/**
	 * 新实例
	 * @param name 指定的名称。
	 * @param ranDate 指定的运行日期。
	 * @param exitedDate 指定的退出日期。
	 * @param exitedCode 退出代码。
	 */
	public DefaultToolHistory(String name, Date ranDate, Date exitedDate, int exitedCode) {
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
		Objects.requireNonNull(ranDate, "入口参数 ranDate 不能为 null。");
		Objects.requireNonNull(exitedDate, "入口参数 exitedDate 不能为 null。");

		this.name = name;
		this.ranDate = ranDate;
		this.exitedDate = exitedDate;
		this.exitedCode = exitedCode;
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
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getRanDate()
	 */
	@Override
	public Date getRanDate() {
		return ranDate;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getExitedDate()
	 */
	@Override
	public Date getExitedDate() {
		return exitedDate;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getExitedCode()
	 */
	@Override
	public int getExitedCode() {
		return exitedCode;
	}

}
