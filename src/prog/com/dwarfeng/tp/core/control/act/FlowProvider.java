package com.dwarfeng.tp.core.control.act;

import com.dwarfeng.tp.core.model.struct.Flow;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * �����ṩ����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface FlowProvider {
	
	/**
	 * ��ȡһ���µĳ����ʼ��ʱʹ�õĹ��̡�
	 * @return �µĳ����ʼ��ʱʹ�õĺ�̨���̡�
	 */
	public Flow newInitializeFlow();
	
	/**
	 * ��ȡһ���µĶ�ȡ��Ĺ��̡�
	 * @return �µĶ�ȡ��Ĺ��̡�
	 */
	public Flow newLoadLibFlow();
	
	/**
	 * ��ȡһ���µļ���Ĺ��̡�
	 * @return �µļ���Ĺ��̡�
	 */
	public Flow newCheckLibFlow();
	
	/**
	 * ��ȡһ���µĶ�ȡ������Ϣ�Ĺ��̡�
	 * @return �µĶ�ȡ������Ϣ�Ĺ��̡�
	 */
	public Flow newLoadToolInfoFlow();
	
	/**
	 *  ��ȡһ���µ����й��ߵĹ��̡�
	 * @param toolInfo ָ���Ĺ��ߡ�
	 * @return �µ����й��ߵĹ��̡�
	 */
	public Flow newRunToolFlow(ToolInfo toolInfo);

	/**
	 * ��ȡһ���µĹرմ��ڵĹ��̡�
	 * @return �µĹرմ��ڵĹ��̡�
	 */
	public Flow newClosingFlow();
	
}
