package com.dwarfeng.tp.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.RunningToolObverser;

/**
 * �����й���ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface RunningToolModel extends ExternalReadWriteThreadSafe, ObverserSet<RunningToolObverser>{

}
