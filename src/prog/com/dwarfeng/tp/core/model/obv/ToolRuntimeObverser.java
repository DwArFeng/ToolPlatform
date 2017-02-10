package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.RunningTool;

/**
 * ��������ʱģ�͹۲�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolRuntimeObverser extends Obverser {
	
	/**
	 * ָ֪ͨ���������й��߱����ӡ�
	 * @param runningTool ָ���������й��ߡ�
	 */
	public void fireRunningToolAdded(RunningTool runningTool);

	/**
	 * ָ֪ͨ���������й��߱��Ƴ���
	 * @param runningTool ָ���������й��ߡ�
	 */
	public void fireRunningToolRemoved(RunningTool runningTool);
	
	/**
	 * ָ֪ͨ���������й��߿�ʼ���С�
	 * @param runningTool ָ���������й��ߡ�
	 */
	public void fireRunningToolStarted(RunningTool runningTool);
	
	/**
	 * ָ֪ͨ���������й��߽�����
	 * @param runningTool ָ���������й��ߡ�
	 */
	public void fireRunningToolExited(RunningTool runningTool);
	
	/**
	 * ֪ͨģ���еľܾ����ӱ�Ǹı䡣
	 * @param newValue �ܾ����ӱ�ǵ���ֵ��
	 */
	public void fireAddRejectChanged(boolean newValue);
	
}