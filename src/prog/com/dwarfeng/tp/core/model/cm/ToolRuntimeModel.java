package com.dwarfeng.tp.core.model.cm;

import java.util.concurrent.ExecutorService;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.str.Name;
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
	 * ���ظú�̨ģ�������ڴ�����̵�ִ��������
	 * <p> ע�⣺���ص�ִ���������Ӧ�����ڲ�ѯ״̬�������������������׳� {@link UnsupportedOperationException}��
	 * @return ��̨ģ���е�ִ��������
	 */
	public ExecutorService getExecutorService();
	
	/**
	 * ��ģ�������ָ���������й��ߡ�
	 * <p> ��ģ������ӵ������й��߱����ǻ�û�����еĹ��ߡ�
	 * @param runningTool ָ���������й��ߡ�
	 * @return �ò����Ƿ��ģ�Ͳ����˱����
	 */
	public boolean add(RunningTool runningTool);
	
	/**
	 * ���ظ�ģ���Ƿ�ܾ��µ������й��߱���ӡ�
	 * @return ��ģ���Ƿ�ܾ��µ������й��߱���ӡ�
	 */
	public boolean isAddRejected();
	
	/**
	 * ���ø�ģ���Ƿ�ܾ��µ������й��߱���ӡ�
	 * @param aFlag �Ƿ�ܾ��µ������й��߱���ӡ�
	 * @return �ò����Ƿ��ģ������˸ı䡣
	 */
	public boolean setAddRejected(boolean aFlag);
	
	/**
	 * ����ģ�����Ƿ��������Ϊָ��ֵ�������й��ߡ�
	 * @param name ָ�������ơ�
	 * @return �Ƿ��������Ϊָ��ֵ�������й��ߡ�
	 */
	public boolean contains(Name name);
	
	/**
	 * ����ģ����ָ�����Ƶ������й��ߴ��ڵ�������
	 * @param name ָ�������ơ�
	 * @return ����Ϊָ��ֵ�������й��ߴ��ڵ�������
	 */
	public int numberOf(Name name);
	
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
	 * ���ظ�ģ�����Ƿ����û���˳���û���������������У��Ĺ���
	 * @return �Ƿ����û���˳��Ĺ��ߡ�
	 */
	public boolean hasNotExited();
	
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
	 * �Ƴ�ָ���������й��ߡ�
	 * <p> ֻ�е������Ѿ���������ʱ�����ܴ�ģ�����Ƴ���
	 * @param runningTool ָ���������й��ߡ�
	 * @return �ò����Ƿ�ı���ģ�ͱ���
	 */
	public boolean remove(RunningTool runningTool);

	/**
	 * ���ģ�������е��Ѿ��˳��������й��ߡ�
	 * @return �÷����Ƿ�ı���ģ�ͱ���
	 */
	public boolean clearExited();
	
	/**
	 * �رոù�������ʱģ�͡�
	 * <p> ��������ʱģ�ͱ��رպ󣬻�ܾ���������ʱ���ߵ���ӣ������Ѿ���ӹ��Ĺ��ߣ���ʲôҲ������
	 */
	public void shutdown();
}
