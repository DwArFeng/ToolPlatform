package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.Process;

public interface BackgroundObverser extends Obverser {
	
	/**
	 * ָ֪ͨ���Ĺ��̶���Ľ��ȷ����ı䡣
	 * @param process �����ı�Ĺ��̶���
	 * @param oldValue ���ȵľ�ֵ��
	 * @param newValue ���ȵ���ֵ��
	 */
	public void fireProcessProgressChanged(Process process, int oldValue, int newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶�����ܽ��ȷ����ı䡣
	 * @param process �����ı�Ĺ��̶���
	 * @param oldValue �ܽ��ȵľ�ֵ��
	 * @param newValue �ܽ��ȵ���ֵ��
	 */
	public void fireProcessTotleProgressChanged(Process process, int oldValue, int newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶����ȷ���Ըı䡣
	 * @param process �����ı�Ĺ��̶���
	 * @param oldValue �ɵ�ȷ���ԡ�
	 * @param newValue �µ�ȷ���ԡ�
	 */
	public void fireProcessDeterminateChanged(Process process, boolean oldValue, boolean newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶������Ϣ�����˸ı䡣
	 * @param process �����ı�Ĺ��̶���
	 * @param oldValue �ɵ���Ϣ��
	 * @param newValue �µ���Ϣ��
	 */
	public void fireProcessMessageChanged(Process process, String oldValue, String newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶���Ŀ��׳��������˸ı䡣
	 * @param process �����˸ı�Ĺ��̶���
	 * @param oldValue �ɵĿ��׳�����
	 * @param newValue �µĿ��׳�����
	 */
	public void fireProcessThrowableChanged(Process process, Throwable oldValue, Throwable newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶���ȡ����
	 * @param process ָ���Ĺ��̶���
	 */
	public void fireProcessCanceled(Process process);

	/**
	 * ָ֪ͨ���Ĺ��̶�����ɡ�
	 * @param process ָ���Ĺ��̶���
	 */
	public void fireProcessDone(Process process);
	
	/**
	 * ָ֪ͨ���Ĺ��̶�����ӡ�
	 * @param process ָ���Ĺ��̶���
	 */
	public void fireProcessAdded(Process process);
	
	/**
	 * ָ֪ͨ���Ĺ��̶����Ƴ���
	 * @param process ָ���Ĺ��̶���
	 */
	public void fireProcessRemoved(Process process);
	
	
}
