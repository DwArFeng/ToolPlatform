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
	private final String ranTimeStr;
	private final String exitedTimeStr;
	
	/**
	 * 新实例。
	 * @param name 指定的名称。
	 * @param ranTimeStr 指定的运行时间字符串。
	 * @param exitedTimeStr 指定的退出时间字符串。
	 */
	public DefaultUnsafeToolHistory(String name, String ranTimeStr, String exitedTimeStr) {
		this.name = name;
		this.ranTimeStr = ranTimeStr;
		this.exitedTimeStr = exitedTimeStr;
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
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolHistory#getRanTime()
	 */
	@Override
	public Date getRanTime() throws ProcessException {
		try{
			if(Objects.isNull(ranTimeStr)) throw new NullPointerException();
			long l = Long.parseLong(ranTimeStr);
			return new Date(l);
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取开始工具名称失败", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.UnsafeToolHistory#getExitedTime()
	 */
	@Override
	public Date getExitedTime() throws ProcessException {
		try{
			if(Objects.isNull(ranTimeStr)) throw new NullPointerException();
			long l = Long.parseLong(exitedTimeStr);
			return new Date(l);
		}catch (Exception e) {
			throw new ProcessException("工具信息-读取结束工具名称失败", e);
		}
	}

}
