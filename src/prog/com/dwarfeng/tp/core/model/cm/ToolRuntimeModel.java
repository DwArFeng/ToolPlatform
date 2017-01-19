package com.dwarfeng.tp.core.model.cm;

import java.util.Iterator;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.ToolRuntimeObverser;
import com.dwarfeng.tp.core.model.struct.Tool;

/**
 * ��������ʱģ�͡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolRuntimeModel extends ExternalReadWriteThreadSafe, ObverserSet<ToolRuntimeObverser>, Iterator<Tool>{

	
	
}
