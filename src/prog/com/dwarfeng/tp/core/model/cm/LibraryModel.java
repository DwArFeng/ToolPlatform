package com.dwarfeng.tp.core.model.cm;

import java.util.Map;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.LibraryObverser;
import com.dwarfeng.tp.core.model.struct.Library;

/**
 * ��ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface LibraryModel extends Map<String, Library>, ExternalReadWriteThreadSafe, ObverserSet<LibraryObverser>{
	
	
}
