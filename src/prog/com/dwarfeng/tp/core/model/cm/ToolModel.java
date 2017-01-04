package com.dwarfeng.tp.core.model.cm;

import java.util.Map;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.obv.ToolObverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * ����ģ�ͽӿڡ�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 1.8
 */
public interface ToolModel extends Map<String, ToolInfo>, ObverserSet<ToolObverser>{
	
}
