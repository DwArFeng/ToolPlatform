package com.dwarfeng.tp.core.model.cm;

import java.util.Collection;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.tp.core.model.obv.BackgrObverser;
import com.dwarfeng.tp.core.model.struct.Process;
import com.dwarfeng.tp.core.model.struct.ReadWriteThreadSafe;

/**
 * 后台模型。
 * @author DwArFeng
 * @since 1.8
 */
public interface BackgroundModel extends ObverserSet<BackgrObverser>, ReadWriteThreadSafe, Iterable<Process>{
	
	public void submit(Process process);
	
	public void submitAll(Collection<? extends Process> c);
	
	public void contains(Object o);
	
	public void containsAll(Collection<? extends Process> c);
	
	public boolean isEmpty();
	
	

}
