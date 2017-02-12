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
	 * ��ȡ���ߵ��������ڡ�
	 * @return ���ߵ��������ڡ�
	 */
	public Date getRanDate();
	
	/**
	 * ��ȡ���ߵĽ������ڡ�
	 * @return ���ߵĽ������ڡ�
	 */
	public Date getExitedDate();
	
	/**
	 * ��ȡ���ߵ��˳����롣
	 * @return ���ߵ��˳����롣
	 */
	public int getExitedCode();
	
}
