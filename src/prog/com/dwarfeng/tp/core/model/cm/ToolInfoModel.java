package com.dwarfeng.tp.core.model.cm;

import java.util.Map;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.ToolObverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * 工具信息模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 1.8
 */
public interface ToolInfoModel extends Map<String, ToolInfo>, ObverserSet<ToolObverser>, ExternalReadWriteThreadSafe{
	
}
