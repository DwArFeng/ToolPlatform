package com.dwarfeng.tp.model.io;

import java.util.Map;

import com.dwarfeng.tp.model.struct.EmergencyException;

/**
 * 程序记录器生成器。
 * @author  DwArFeng
 * @since 1.8
 */
public interface ProgramLoggerGenerator {
	
	/**
	 * 生成一个新的程序记录器生成器。
	 * @param resourceMap 指定的程序资源映射。
	 * @return 新的程序记录器实例。
	 */
	public ProgramLogger newInstance(Map<String, ProgramResource> resourceMap) throws EmergencyException;

}
