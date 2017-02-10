package com.dwarfeng.tp.core.model.struct;

import java.util.Date;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * ������ʷ��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolHistory extends Name{
	
	/**
	 * ��ȡ���ߵ�����ʱ�䡣
	 * @return ���ߵ�����ʱ�䡣
	 */
	public Date getRanTime();
	
	/**
	 * ��ȡ���ߵĽ���ʱ�䡣
	 * @return ���ߵĽ���ʱ�䡣
	 */
	public Date getExitedTime();
	
}
