package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.Process;

/**
 * ���̹۲�����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface ProcessObverser extends Obverser{
	
	/**
	 * ָ֪ͨ���Ĺ��̶���Ľ��ȷ����ı䡣
	 * @param process �����ı�Ĺ��̶���
	 * @param oldValue ���ȵľ�ֵ��
	 * @param newValue ���ȵ���ֵ��
	 */
	public void fireProgressChanged(Process process, int oldValue, int newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶�����ܽ��ȷ����ı䡣
	 * @param process �����ı�Ĺ��̶���
	 * @param oldValue �ܽ��ȵľ�ֵ��
	 * @param newValue �ܽ��ȵ���ֵ��
	 */
	public void fireTotleProgressChanged(Process process, int oldValue, int newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶����ȷ���Ըı䡣
	 * @param process �����ı�Ĺ��̶���
	 * @param oldValue �ɵ�ȷ���ԡ�
	 * @param newValue �µ�ȷ���ԡ�
	 */
	public void fireDeterminateChanged(Process process, boolean oldValue, boolean newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶������Ϣ�����˸ı䡣
	 * @param process �����ı�Ĺ��̶���
	 * @param oldValue �ɵ���Ϣ��
	 * @param newValue �µ���Ϣ��
	 */
	public void fireMessageChanged(Process process, String oldValue, String newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶���Ŀ��׳��������˸ı䡣
	 * @param process �����˸ı�Ĺ��̶���
	 * @param oldValue �ɵĿ��׳�����
	 * @param newValue �µĿ��׳�����
	 */
	public void fireThrowableChanged(Process process, Throwable oldValue, Throwable newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶����ȡ���Է����˸ı䡣
	 * @param process �����˸ı�Ĺ��̶���
	 * @param oldValue �ɵĿ�ȡ���ԡ�
	 * @param newValue �µĿ�ȡ���ԡ�
	 */
	public void fireCancelableChanged(Process process, boolean oldValue, boolean newValue);

	/**
	 * ָ֪ͨ���Ĺ��̶���ȡ����
	 * @param process ָ���Ĺ��̶���
	 */
	public void fireCanceled(Process process);

	/**
	 * ָ֪ͨ���Ĺ��̶�����ɡ�
	 * @param process ָ���Ĺ��̶���
	 */
	public void fireDone(Process process);
	
}
