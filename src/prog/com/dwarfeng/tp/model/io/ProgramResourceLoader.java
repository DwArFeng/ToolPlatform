package com.dwarfeng.tp.model.io;

import java.util.Map;

import com.dwarfeng.tp.model.struct.EmergencyException;

/**
 * 
 * @author DwArFeng
 * @since 1.8
 */
public interface ProgramResourceLoader {

	/**
	 * 读取程序资源。
	 * @return 资源键与对应资源组成的映射。
	 * @throws EmergencyException 相应配置无法解析造成的紧急异常。
	 */
	public Map<String, ProgramResource> loadResources() throws EmergencyException;
	
}
