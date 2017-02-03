package com.dwarfeng.tp.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.ToolRuntimeObverser;
import com.dwarfeng.tp.core.model.struct.RunningTool;

/**
 * ��������ʱģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolRuntimeModel extends ExternalReadWriteThreadSafe, ObverserSet<ToolRuntimeObverser>, Iterable<RunningTool>{

	/**
	 * ��ģ�������ָ���������й��ߡ�
	 * @param runningTool ָ���������й��ߡ�
	 * @return �ò����Ƿ��ģ�Ͳ����˱����
	 */
	public boolean add(RunningTool runningTool);
	
	/**
	 * ����ģ�����Ƿ��������Ϊָ��ֵ�������й��ߡ�
	 * @param name ָ�������ơ�
	 * @return �Ƿ��������Ϊָ��ֵ�������й��ߡ�
	 */
	public boolean contains(String name);
	
	/**
	 * ����ģ����ָ�����Ƶ������й��ߴ��ڵ�������
	 * @param name ָ�������ơ�
	 * @return ����Ϊָ��ֵ�������й��ߴ��ڵ�������
	 */
	public int numberOf(String name);
	
	/**
	 * ����ģ���е������й���������
	 * @return ģ���е������й��ߵ�������
	 */
	public int size();
	
	/**
	 * ���ظ�ģ���Ƿ�Ϊ�ա�
	 * @return ��ģ���Ƿ�Ϊ�ա�
	 */
	public boolean isEmpty();
	
	/**
	 * ���ظ�ģ�����Ƿ�����Ѿ��˳��������й��ߡ�
	 * @return �Ƿ�����Ѿ���ɵ������й��ߡ�
	 */
	public boolean hasExited();
	
	/**
	 * ȡ��������������Ѿ��˳��������й��ߣ����û�У���ȴ���
	 * @return ������Ѿ��˳��������й��ߡ�
	 * @throws InterruptedException �ȴ��������̱߳��жϡ�
	 */
	public RunningTool takeExited() throws InterruptedException;
	
	/**
	 * ���ģ�������е��Ѿ��˳��������й��ߡ�
	 * @return �÷����Ƿ�ı���ģ�ͱ���
	 */
	public boolean clearExited();
}
