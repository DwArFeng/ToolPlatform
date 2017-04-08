package com.dwarfeng.tp.core.model.struct;

import java.util.Date;
import java.util.Objects;

/**
 * 默认不安全工具历史。
 * <p> 不安全工具历史的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultUnsafeToolHistory implements UnsafeToolHistory {
	
	private final String name;
	private final String ranDateStr;
	private final String exitedDateStr;
	private final String exitedCodeStr;
	
	/**
	 * 新实例。
	 * @param name 指定的名称。
	 * @param ranDateStr 指定的运行日期字符串。
	 * @param exitedDateStr 指定的退出日期字符串。
	 * @param exitedCodeStr 指定的退出代码字符串。
	 */
	public DefaultUnsafeToolHistory(String name, String ranDateStr, String exitedDateStr, String exitedCodeStr) {
		this.name = name;
		this.ranDateStr = ranDateStr;
		this.exitedDateStr = exitedDateStr;
		this.exitedCodeStr = exitedCodeStr;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolHistory#getName()
	 */
	@Override
	public String getName() throws ProcessException {
		try{
			if(Objects.isNull(name)) throw new NullPointerException();
			return name;
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取工具名称失败", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolHistory#getRanDate()
	 */
	@Override
	public Date getRanDate() throws ProcessException {
		try{
			if(Objects.isNull(ranDateStr)) throw new NullPointerException();
			long l = Long.parseLong(ranDateStr);
			return new Date(l);
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取开始工具名称失败", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolHistory#getExitedDate()
	 */
	@Override
	public Date getExitedDate() throws ProcessException {
		try{
			if(Objects.isNull(exitedDateStr)) throw new NullPointerException();
			long l = Long.parseLong(exitedDateStr);
			return new Date(l);
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取结束工具名称失败", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolHistory#getExitedCode()
	 */
	@Override
	public int getExitedCode() throws ProcessException {
		try{
			if(Objects.isNull(exitedCodeStr)) throw new NullPointerException();
			int exitCode = Integer.parseInt(exitedCodeStr);
			return exitCode;
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取结束工具名称失败", e);
		}
	}

}
